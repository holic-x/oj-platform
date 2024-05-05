package com.noob.oj.codesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.noob.oj.codesandbox.model.ExecuteCodeRequest;
import com.noob.oj.codesandbox.model.ExecuteCodeResponse;
import com.noob.oj.codesandbox.model.ExecuteMessage;
import com.noob.oj.codesandbox.model.JudgeInfo;
import com.noob.oj.codesandbox.utils.ProcessUtils;

import org.springframework.util.StopWatch;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName JavaDockerCodeSandbox
 * @Description JavaDocker沙箱实现
 * @Author holic-x
 * @Date 2024/5/3 19:08
 */
public class JavaDockerCodeSandbox implements CodeSandbox {

    // 全局文件存储文件夹定义
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    // 存储的文件名定义
    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    // 设置超时控制时长
    private static final long TIME_OUT = 5000L;

    // 自定义安全管理器存储路径
    private static final String SECURITY_MANAGER_PATH = "E:\\workspace\\Git\\github\\PROJECT\\noob\\oj-platform\\oj-code-sandbox\\src\\main\\resources\\security";

    // 自定义安全管理器类名
    private static final String SECURITY_MANAGER_CLASS_NAME = "MySecurityManager";

    // 定义初始化
    private static final Boolean FIRST_INIT = true;

    public static void main(String[] args) {

        JavaDockerCodeSandbox javaNativeCodeSandbox = new JavaDockerCodeSandbox();
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setInputList(Arrays.asList("1 2", "1 3"));
        String code = ResourceUtil.readStr("testCode/simpleComputeArgs/Main.java", StandardCharsets.UTF_8);
        executeCodeRequest.setCode(code);
        executeCodeRequest.setLanguage("java");
        ExecuteCodeResponse executeCodeResponse = javaNativeCodeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);

    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

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

        // 3）创建容器，把编译好的文件上传到容器环境内
        // 获取默认的 Docker Client
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        String image = "openjdk:8-alpine";
        if(FIRST_INIT){
            // 初始化镜像（不用每次都去拉取镜像，只需要初始化一次）
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载镜像：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd
                        .exec(pullImageResultCallback)
                        .awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
            System.out.println("下载完成");
        }

        // 创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        HostConfig hostConfig = new HostConfig();
        hostConfig.withMemory(100 * 1000 * 1000L); // 限制内存
        hostConfig.withMemorySwap(0L);
        hostConfig.withCpuCount(1L); // 限制CPU
//        hostConfig.withSecurityOpts(Arrays.asList("seccomp=安全管理配置字符串"));
        hostConfig.setBinds(new Bind(userCodeParentPath, new Volume("/app"))); // 容器映射，一般不要放在根目录
        CreateContainerResponse createContainerResponse = containerCmd
                .withHostConfig(hostConfig)
                .withNetworkDisabled(true)
                .withReadonlyRootfs(true)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withTty(true)
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();

        // 4）启动容器，执行代码
        dockerClient.startContainerCmd(containerId).exec();

        // 定义执行结果消息列表
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        // 创建命令：docker exec [容器名称] java -cp /app Main 1 3
        for (String inputArgs : inputList) {
            // 定义计时器
            StopWatch stopWatch = new StopWatch();
            long time = 0L;

            String[] inputArgsArray = inputArgs.split(" ");
            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .exec();
            System.out.println("创建执行命令：" + execCreateCmdResponse);

            ExecuteMessage executeMessage = new ExecuteMessage();
            final String[] message = {null};
            final String[] errorMessage = {null};

            String execId = execCreateCmdResponse.getId();
            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {
                @Override
                public void onNext(Frame frame) {
                    //
                    StreamType streamType = frame.getStreamType();
                    if (StreamType.STDERR.equals(streamType)) {
                        errorMessage[0] = new String(frame.getPayload());
                        System.out.println("输出错误结果：" + errorMessage[0]);
                    } else {
                        message[0] = new String(frame.getPayload());
                        System.out.println("输出结果：" + message[0]);
                    }
                    super.onNext(frame);
                }
            };
            try {
                stopWatch.start();
                // 将回调结果传递过来，并且阻塞等待日志输出
                dockerClient.execStartCmd(execId)
                        .exec(execStartResultCallback)
                        .awaitCompletion();
                stopWatch.stop();
                time = stopWatch.getLastTaskTimeMillis();
            } catch (InterruptedException e) {
                System.out.println("程序执行异常");
                throw new RuntimeException(e);
            }

            // 设置执行信息
            executeMessage.setTime(time);
            executeMessage.setMessage(message[0]);
            executeMessage.setErrorMessage(errorMessage[0]);
            executeMessageList.add(executeMessage);
        }


        // todo 封装响应结果
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
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
