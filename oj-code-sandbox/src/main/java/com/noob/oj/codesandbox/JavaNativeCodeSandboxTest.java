package com.noob.oj.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
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
 * @Description Java原生沙箱实现（测试：交互式实现）
 * @Author holic-x
 * @Date 2024/5/3 19:08
 */
@Deprecated
public class JavaNativeCodeSandboxTest implements CodeSandbox {

    // 全局文件存储文件夹定义
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    // 存储的文件名定义
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    public static void main(String[] args) {
        JavaNativeCodeSandboxTest javaNativeCodeSandbox = new JavaNativeCodeSandboxTest();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2", "1 3"));
        String code = ResourceUtil.readStr("testCode/simpleCompute/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String code = executeCodeRequest.getCode();

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
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
        } catch (Exception e) {
            return getErrorResponse(e);
        }

        // 3）执行代码，得到输出结果（命令中指定 -Dfile.encoding=UTF-8 参数，解决中文乱码）
        String runCmd = String.format("java -Dfile.encoding=UTF-8 -cp %s Main", userCodeParentPath);
        try {
            Process runProcess = Runtime.getRuntime().exec(runCmd);
            ExecuteMessage executeMessage = ProcessUtils.runInteractProcessAndGetMessage(runProcess, "1 3");
            System.out.println(executeMessage);
        } catch (Exception e) {
            System.out.println("异常：" + getErrorResponse(e));
            return getErrorResponse(e);
        }
        return null;
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
