package com.noob.module.oj.model.questionSubmit.dto;

import com.noob.framework.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 查找请求
 *
 */
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题状态（0 - 待判题，1 - 判题中，2 - 成功，3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;
}