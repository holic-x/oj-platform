package com.noob.module.oj.controller;

import com.noob.framework.common.BaseResponse;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.common.ResultUtils;
import com.noob.framework.exception.BusinessException;
import com.noob.module.base.user.model.entity.User;
import com.noob.module.base.user.service.UserService;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.judge.strategy.SandboxService;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitAddRequest;
import com.noob.module.oj.model.sandbox.dto.SandboxOnlineRunRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 沙箱接口调用
 */
@RestController
@RequestMapping("/sandbox")
@Slf4j
public class SandboxController {

    @Autowired
    private UserService userService;

    @Autowired
    private SandboxService sandboxService;


    /**
     * 在线运行
     */
    @PostMapping("/onlineRun")
    public BaseResponse<ExecuteCodeResponse> onlineRun(@RequestBody SandboxOnlineRunRequest sandboxOnlineRunRequest,
                                               HttpServletRequest request) {
        if (sandboxOnlineRunRequest == null||sandboxOnlineRunRequest.getQuestionId()==0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取登录用户
//        final User loginUser = userService.getLoginUser(request);

        ExecuteCodeResponse executeCodeResponse = sandboxService.onlineRun(sandboxOnlineRunRequest);
        return ResultUtils.success(executeCodeResponse);
    }
}
