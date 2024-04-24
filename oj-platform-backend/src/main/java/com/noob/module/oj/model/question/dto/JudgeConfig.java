package com.noob.module.oj.model.question.dto;

import lombok.Data;

/**
 * @ClassName JudgeConfig
 * @Description 题目配置
 * @Author holic-x
 * @Date 2024 2024/4/24 22:20
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
