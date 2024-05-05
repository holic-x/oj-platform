package com.noob.oj.docker;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Container;

import java.util.List;

/**
 * Docker基本操作
 */
public class DockerDemo {
    public static void main(String[] args) throws InterruptedException {
        // 获取默认的 Docker Client
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//        PingCmd pingCmd = dockerClient.pingCmd();
//        pingCmd.exec();
        String image = "nginx:latest";

        // 1.拉取镜像
        /*
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
        PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
            @Override
            public void onNext(PullResponseItem item) {
                System.out.println("下载镜像：" + item.getStatus());
                super.onNext(item);
            }
        };
        pullImageCmd
                .exec(pullImageResultCallback)
                .awaitCompletion();
        System.out.println("下载完成");
         */


        // 2.创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);
        CreateContainerResponse createContainerResponse = containerCmd
                .withCmd("echo", "Hello Docker") // 在创建容器的时候可以附加一些给创建容器时自动执行的命令（例如此处指定命令输出Hello Docker）
                .exec();
        System.out.println(createContainerResponse);
        // 获取创建的容器ID
        String containerId = createContainerResponse.getId();


        // 3.查看容器状态
        /*
        ListContainersCmd listContainersCmd = dockerClient.listContainersCmd();
        List<Container> containerList = listContainersCmd.withShowAll(true).exec();
        for (Container container : containerList) {
            System.out.println(container);
        }
         */

        // 4.启动容器
         dockerClient.startContainerCmd(containerId).exec();
//         Thread.sleep(10000);

        // 5.查看日志
        LogContainerResultCallback logContainerResultCallback = new LogContainerResultCallback() {
            @Override
            public void onNext(Frame item) {
                System.out.println("日志：" + new String(item.getPayload()));
                super.onNext(item);
            }
        };

        // 阻塞等待日志输出
        dockerClient.logContainerCmd(containerId)
                .withStdErr(true)
                .withStdOut(true)
                .exec(logContainerResultCallback)
                .awaitCompletion();

        // 6.删除容器
        dockerClient.removeContainerCmd(containerId).exec();

        // 7.删除镜像
        dockerClient.removeImageCmd(image).exec();

    }


}
