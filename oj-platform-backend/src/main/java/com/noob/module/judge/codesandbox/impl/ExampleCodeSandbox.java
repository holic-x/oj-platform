package com.noob.module.judge.codesandbox.impl;

import com.noob.module.judge.codesandbox.CodeSandbox;
import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.judge.codesandbox.model.JudgeInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName ExampleCodeSandbox
 * @Description 示例代码沙箱
 * @Author holic-x
 * @Date 2024/5/3 10:07
 */
@Slf4j
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
//        List<String> inputList = executeCodeRequest.getInputList();
//        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
//        executeCodeResponse.setOutputList(inputList);
//        executeCodeResponse.setMessage("测试执行成功");
////        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
//        JudgeInfo judgeInfo = new JudgeInfo();
////        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
//        judgeInfo.setMemory(100L);
//        judgeInfo.setTime(100L);
//        executeCodeResponse.setJudgeInfo(judgeInfo);
//        return executeCodeResponse;

        System.out.println("示例代码沙箱");
        return null;

    }

}

