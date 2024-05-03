package com.noob.module.judge.codesandbox;

import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * @ClassName CodeSandboxProxy
 * @Description 代码沙箱代理类
 * @Author holic-x
 * @Date 2024/5/3 12:35
 */
@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    @Resource
    private CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        // 代理增强，实现日志输出
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return null;
    }
}
