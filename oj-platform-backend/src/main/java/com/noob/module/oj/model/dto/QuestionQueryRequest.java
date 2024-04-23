package com.noob.module.oj.model.dto;

import com.noob.framework.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

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
     * 标签列表（json 数组）
     */
    private String tags;

    private static final long serialVersionUID = 1L;
}