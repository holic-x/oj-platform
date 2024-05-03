package com.noob.module.oj.model.question.dto;

import com.noob.framework.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 查找请求
 *
 */
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}