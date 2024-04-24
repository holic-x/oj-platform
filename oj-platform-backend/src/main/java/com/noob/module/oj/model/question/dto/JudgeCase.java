package com.noob.module.oj.model.question.dto;

import lombok.Data;

/**
 * @ClassName JudgeCase
 * @Description T题目用例
 * @Author holic-x
 * @Date 2024 2024/4/24 22:19
 */
@Data
public class JudgeCase {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
