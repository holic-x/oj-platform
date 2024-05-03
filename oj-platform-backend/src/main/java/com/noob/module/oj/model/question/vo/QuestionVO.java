package com.noob.module.oj.model.question.vo;

import com.noob.module.base.template.model.entity.Template;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题视图
 *
 */
@Data
public class QuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

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

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题用例（json 数组）
     */
    private String judgeCase;

    /**
     * 判题配置（json 对象）
     */
    private String judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private String creater;

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
    public static Template voToObj(QuestionVO templateVO) {
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
    public static QuestionVO objToVo(Template template) {
        if (template == null) {
            return null;
        }
        QuestionVO templateVO = new QuestionVO();
        BeanUtils.copyProperties(template, templateVO);
        return templateVO;
    }
}
