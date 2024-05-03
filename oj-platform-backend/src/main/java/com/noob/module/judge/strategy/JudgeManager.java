package com.noob.module.judge.strategy;

import com.noob.module.judge.codesandbox.model.JudgeInfo;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @ClassName JudgeManager
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 15:04
 */
@Service
public class JudgeManager {
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
