package com.noob.module.judge.codesandbox;

import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @ClassName CodeSandbox
 * @Description 代码沙箱
 * @Author holic-x
 * @Date 2024/5/3 9:52
 */
public interface CodeSandbox {

    /**
     * 执行代码
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);


    /**
     * 获取代码沙箱状态 todo 获取代码沙箱的状态信息
     * @return
     */
    // int getCodeSandbox();

}
