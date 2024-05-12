package com.noob.module.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.noob.framework.common.BaseResponse;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.exception.BusinessException;
import com.noob.module.judge.codesandbox.CodeSandbox;
import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
@Slf4j
@Component
public class RemoteCodeSandbox implements CodeSandbox {

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    // 远程访问链接
//    private String sandboxRemoteUrl = "http://192.168.68.129:8090/executeCode";  // ubuntu远程访问链接（访问远程linux沙箱（有Java原生版本和Docker版本））
//    private String sandboxRemoteUrl = "http://localhost:8090/executeCode"; // 本地访问链接

    private String sandboxRemoteUrl = "http://localhost:8090/onlineRun";


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        // 本地沙箱
        log.info("请求远程代码沙箱访问URL：{}",sandboxRemoteUrl);

        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(sandboxRemoteUrl)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }

        // 返回沙箱响应信息
//        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);

        BaseResponse response = JSONUtil.toBean(responseStr, BaseResponse.class);
        if(response.getCode()==0){
            // 返回响应数据
            return JSONUtil.toBean(String.valueOf(response.getData()), ExecuteCodeResponse.class);
        }else{
            // 调用沙箱异常，返回沙箱异常信息
            throw new BusinessException(response.getCode(),response.getMessage());
        }

//        System.out.println("远程代码沙箱");
//        return null;

    }
}
