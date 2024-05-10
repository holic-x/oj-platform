package com.noob.module.judge.strategy;

import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.oj.model.sandbox.dto.SandboxOnlineRunRequest;

/**
 * 沙箱服务接口定义
 */
public interface SandboxService {

    public ExecuteCodeResponse onlineRun(SandboxOnlineRunRequest sandboxOnlineRunRequest);


}
