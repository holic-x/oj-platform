package com.noob.oj.codesandbox.model;



import lombok.Data;

/**
 * @ClassName JudgeInfo
 * @Description 判题信息
 * @Author holic-x
 * @Date 2024/5/3 10:00
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间（KB）
     */
    private Long time;
}

