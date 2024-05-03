package com.noob.module.judge.strategy;

import com.noob.module.judge.codesandbox.model.JudgeInfo;

/**
 * @ClassName JudgeStrategy
 * @Description 判题策略
 * @Author holic-x
 * @Date 2024/5/3 14:31
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
