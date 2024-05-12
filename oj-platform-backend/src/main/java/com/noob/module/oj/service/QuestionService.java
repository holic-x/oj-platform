package com.noob.module.oj.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.noob.module.oj.model.question.dto.QuestionQueryRequest;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.question.vo.QuestionVO;

/**
* @author HUAWEI
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-04-23 09:25:47
*/
public interface QuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取封装
     *
     * @param id
     * @return
     */
    QuestionVO getVOById(long id);

    /**
     * 分页获取数据封装(SQL处理)
     *
     * @param questionQueryRequest
     * @return
     */
    Page<QuestionVO> getVOByPage(QuestionQueryRequest questionQueryRequest);
}
