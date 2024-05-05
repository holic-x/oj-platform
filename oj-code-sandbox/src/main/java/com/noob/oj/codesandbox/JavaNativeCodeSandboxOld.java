package com.noob.oj.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.WordTree;
import com.noob.oj.codesandbox.model.ExecuteCodeRequest;
import com.noob.oj.codesandbox.model.ExecuteCodeResponse;
import com.noob.oj.codesandbox.model.ExecuteMessage;
import com.noob.oj.codesandbox.model.JudgeInfo;
import com.noob.oj.codesandbox.utils.ProcessUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName JavaNativeCodeSandbox
 * @Description Java原生沙箱实现（优化版本）
 * @Author holic-x
 * @Date 2024/5/3 19:08
 */
@Deprecated
public class JavaNativeCodeSandboxOld implements CodeSandbox {

    // 全局文件存储文件夹定义
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    // 存储的文件名定义
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    // 设置超时控制时长
    private static final long TIME_OUT = 5000L;

    // 定义黑名单
    public static final List<String> blackList = Arrays.asList("Files", "exec");

    public static final WordTree WORD_TREE;

    static {
        //初始化字典树
        WORD_TREE = new WordTree();
        WORD_TREE.addWords(blackList);
    }

    // 自定义安全管理器存储路径
    private static final String SECURITY_MANAGER_PATH = "E:\\workspace\\Git\\github\\PROJECT\\noob\\oj-platform\\oj-code-sandbox\\src\\main\\resources\\security";

    // 自定义安全管理器类名
    private static final String SECURITY_MANAGER_CLASS_NAME = "MySecurityManager";

    public static void main(String[] args) {

        JavaNativeCodeSandboxOld javaNativeCodeSandbox = new JavaNativeCodeSandboxOld();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2", "1 3"));
//        String code = ResourceUtil.readStr("testCode/simpleComputeArgs/Main.java", StandardCharsets.UTF_8);
        // todo 设置访问的文件路径
        String code = ResourceUtil.readStr("testCode/unsafeCode/RunFileError.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);

    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 设定安全管理器（外层设定安全管理器会根据规则限制程序的所有操作，例如自身项目程序以及要执行的代码程序）
//        System.setSecurityManager(new DenySecurityManager());

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        // 优化3：校验代码中是否包含黑名单命令
        /*
        FoundWord foundWord = WORD_TREE.matchWord(code);
        if (foundWord != null) {
            System.out.println("包含禁止词：" + foundWord.getFoundWord());
            return null;
        }
         */

        // 1）把用户的代码保存为文件
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        // 判断全局代码目录是否存在，没有则新建
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }

        // 把用户的代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);


        // 2）编译代码，得到 class 文件
        String compileCmd = String.format("javac -encoding utf-8 %s", userCodeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            // 自定义ProcessUtils获取控制台输出（通过 exitValue 判断程序是否正常返回，通过 inputStream 和 errorStream 获取控制台输出）
            // 方式1：通过args方式输入参数（项目核心）
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
        } catch (Exception e) {
            return getErrorResponse(e);
        }


        // 3）执行代码，得到输出结果（命令中指定 -Dfile.encoding=UTF-8 参数，解决中文乱码）
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
//            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);
            // 优化2：限制资源分配
//            String runCmd = String.format("java -Xmx4096m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath, inputArgs);


            // 优化4：设定自定义安全管理器(测试校验执行权限是否放行)
            String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s;%s -Djava.security.manager=%s Main %s", userCodeParentPath,SECURITY_MANAGER_PATH,SECURITY_MANAGER_CLASS_NAME, inputArgs);


            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                // 优化1：超时控制
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        System.out.println("超时了，中断");
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                System.out.println(executeMessage);
                // 将执行结果加入列表
                executeMessageList.add(executeMessage);
            } catch (Exception e) {
                System.out.println("异常：" + getErrorResponse(e));
                return getErrorResponse(e);
            }
        }

        // 4）收集整理输出结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        // 取用时最大值，便于判断是否超时
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            String errorMessage = executeMessage.getErrorMessage();
            if (StrUtil.isNotBlank(errorMessage)) {
                executeCodeResponse.setMessage(errorMessage);
                // 用户提交的代码执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
            }
        }
        // 正常运行完成
        if (outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(maxTime);
        // todo 要借助第三方库来获取内存占用，非常麻烦，此处不做实现
        // judgeInfo.setMemory();
        executeCodeResponse.setJudgeInfo(judgeInfo);


        // 5）文件清理
        if (userCodeFile.getParentFile() != null) {
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("删除" + (del ? "成功" : "失败"));
        }

        // 6）错误处理，提升程序健壮性（通过自定义封装getErrorResponse错误处理方法，当程序抛出异常时，直接返回错误响应）参考getErrorResponse方法

        // 返回执行代码结果响应
        return executeCodeResponse;
    }


    /**
     * 获取错误响应
     *
     * @param e
     * @return
     */
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        // 表示代码沙箱错误
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
