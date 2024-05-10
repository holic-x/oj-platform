package com.noob.module.oj.service.impl;

import cn.hutool.json.JSONUtil;
import com.noob.module.judge.JudgeService;
import com.noob.module.judge.codesandbox.CodeSandbox;
import com.noob.module.judge.codesandbox.CodeSandboxFactory;
import com.noob.module.judge.codesandbox.CodeSandboxProxy;
import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.judge.strategy.SandboxService;
import com.noob.module.oj.model.question.dto.JudgeCase;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.sandbox.dto.SandboxOnlineRunRequest;
import com.noob.module.oj.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SandboxServiceImpl implements SandboxService {


    @Autowired
    private QuestionService questionService;

    // 沙箱参数：沙箱类型（如果没有指定则默认为example）
    @Value("${codesandbox.type:example}")
    private String sandboxType;

    @Override
    public ExecuteCodeResponse onlineRun(SandboxOnlineRunRequest sandboxOnlineRunRequest) {
        // 根据指定沙箱类型，构建代理沙箱，调用沙箱服务
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(sandboxType);
        codeSandbox = new CodeSandboxProxy(codeSandbox);

        Question question = questionService.getById(sandboxOnlineRunRequest.getQuestionId());
        // 获取请求参数信息
        String language = sandboxOnlineRunRequest.getLanguage();
        String code = sandboxOnlineRunRequest.getCode();

        // 获取题目关联的输入用例（将String类型转化为列表）
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);

        // 借助Lambda表达式获取用例的输入并拼接为List
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        List<String> outputList = judgeCaseList.stream().map(JudgeCase::getOutput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);

        return executeCodeResponse;
    }
}
