package com.noob.module.oj.model.questionSubmit.vo;

import com.noob.module.template.model.entity.Template;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题提交视图
 *
 */
@Data
public class QuestionSubmitVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息（json对象）
     */
    private String judgeInfo;

    /**
     * 判题状态（0 - 待判题，1 - 判题中，2 - 成功，3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long creater;

    /**
     * 创建用户名称
     */
    private String createrName;

    /**
     * 修改用户 id
     */
    private String updater;

    /**
     * 修改用户名称
     */
    private String updaterName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 包装类转对象
     *
     * @param templateVO
     * @return
     */
    public static Template voToObj(QuestionSubmitVO templateVO) {
        if (templateVO == null) {
            return null;
        }
        Template template = new Template();
        BeanUtils.copyProperties(templateVO, template);
        return template;
    }

    /**
     * 对象转包装类
     *
     * @param template
     * @return
     */
    public static QuestionSubmitVO objToVo(Template template) {
        if (template == null) {
            return null;
        }
        QuestionSubmitVO templateVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(template, templateVO);
        return templateVO;
    }
}
