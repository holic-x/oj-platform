package com.noob.module.judge.strategy;

import com.noob.module.judge.codesandbox.model.JudgeInfo;
import com.noob.module.oj.model.question.dto.JudgeCase;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @ClassName JudgeContext
 * @Description 上下文（用于定义在策略中传递的参数）
 * @Author holic-x
 * @Date 2024/5/3 14:31
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}