package com.noob.oj.codesandbox.controller;

import com.noob.framework.common.BaseResponse;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.common.ResultUtils;
import com.noob.framework.exception.BusinessException;
import com.noob.oj.codesandbox.JavaNativeCodeSandbox;
import com.noob.oj.codesandbox.model.ExecuteCodeRequest;
import com.noob.oj.codesandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 17:51
 */
@RestController("/")
public class MainController {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    /**
     * 执行代码（调用代码沙箱的开发接口）
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
        // 基本的认证
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            // 鉴权失败
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"鉴权失败，请确认后再次尝试");
//            return null;
        }

        // 校验参数信息
        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }

        // 返回响应
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }


    /**
     * 执行代码（调用代码沙箱的开发接口）
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/onlineRun")
    BaseResponse<ExecuteCodeResponse> onlineRun(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                           HttpServletResponse response) {
        // 基本的认证
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            // 鉴权失败
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR,"鉴权失败，请确认后再次尝试");
        }

        // 校验参数信息
        if (executeCodeRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数为空");
        }

        // 封装一层响应信息
        return ResultUtils.success(javaNativeCodeSandbox.executeCode(executeCodeRequest));
    }
}




