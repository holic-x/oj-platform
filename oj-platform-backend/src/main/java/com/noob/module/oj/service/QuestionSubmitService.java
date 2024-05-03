package com.noob.module.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.noob.module.base.user.model.entity.User;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitAddRequest;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitQueryRequest;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import com.noob.module.oj.model.questionSubmit.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @description 针对表【questionSubmit_submit(题目提交)】的数据库操作Service
* @createDate 2024-04-23 09:25:47
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 校验
     *
     * @param questionSubmit
     * @param add
     */
    void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add);

    /**
     * 获取封装
     *
     * @param id
     * @param request
     * @return
     */
    QuestionSubmitVO getVOById(long id, HttpServletRequest request);

    /**
     * 分页获取数据封装(SQL处理)
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    Page<QuestionSubmitVO> getVOByPage(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);




}
