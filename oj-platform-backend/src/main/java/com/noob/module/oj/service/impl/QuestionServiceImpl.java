package com.noob.module.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.exception.BusinessException;
import com.noob.framework.exception.ThrowUtils;
import com.noob.module.oj.mapper.QuestionMapper;
import com.noob.module.oj.model.question.dto.QuestionQueryRequest;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.question.vo.QuestionVO;
import com.noob.module.oj.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author HUAWEI
 * @description 针对表【question(题目)】的数据库操作Service实现
 * @createDate 2024-04-23 09:25:47
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {


    @Resource
    private QuestionMapper questionMapper;

    @Override
    public void validQuestion(Question question, boolean add) {
        if (question == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }

    @Override
    public QuestionVO getVOById(long id, HttpServletRequest request) {
        return questionMapper.getVOById(id);
    }

    @Override
    public Page<QuestionVO> getVOByPage(QuestionQueryRequest questionQueryRequest) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        Page<T> page = new Page<>(current, size);
        Page<QuestionVO> questionVOPage = questionMapper.getVOByPage(questionQueryRequest,page);
        return questionVOPage;

    }


}




