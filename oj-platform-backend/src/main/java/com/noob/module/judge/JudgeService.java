package com.noob.module.judge;

import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import com.noob.module.oj.model.questionSubmit.vo.QuestionSubmitVO;

/**
 * @ClassName JudgeService
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 13:14
 */
public interface JudgeService {
    /**
     * 判题实现
     * @param questionSubmitId
     * @return
     */
    public QuestionSubmit doJudge(long questionSubmitId);
}
