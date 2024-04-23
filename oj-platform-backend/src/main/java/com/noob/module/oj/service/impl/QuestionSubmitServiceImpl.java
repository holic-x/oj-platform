package com.noob.module.oj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.exception.BusinessException;
import com.noob.framework.exception.ThrowUtils;
import com.noob.module.oj.mapper.QuestionSubmitMapper;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitQueryRequest;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import com.noob.module.oj.model.questionSubmit.vo.QuestionSubmitVO;
import com.noob.module.oj.service.QuestionSubmitService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author HUAWEI
* @description 针对表【questionSubmit_submit(题目提交)】的数据库操作Service实现
* @createDate 2024-04-23 09:25:47
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {


    @Resource
    private QuestionSubmitMapper questionSubmitMapper;

    @Override
    public void validQuestionSubmit(QuestionSubmit questionSubmit, boolean add) {
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        long questionId = questionSubmit.getQuestionId();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(language, code,String.valueOf(questionId)), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(language) && language.length() > 80) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (StringUtils.isNotBlank(code) && code.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
    }

    @Override
    public QuestionSubmitVO getVOById(long id, HttpServletRequest request) {
        return questionSubmitMapper.getVOById(id);
    }

    @Override
    public Page<QuestionSubmitVO> getVOByPage(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<T> page = new Page<>(current, size);
        Page<QuestionSubmitVO> questionSubmitVOPage = questionSubmitMapper.getVOByPage(questionSubmitQueryRequest,page);
        return questionSubmitVOPage;

    }

}




