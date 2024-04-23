package com.noob.module.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitQueryRequest;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import com.noob.module.oj.model.questionSubmit.vo.QuestionSubmitVO;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

/**
* @author HUAWEI
* @description 针对表【questionSubmit_submit(题目提交)】的数据库操作Mapper
* @createDate 2024-04-23 09:25:47
* @Entity com.noob.module.oj.model.entity.QuestionSubmitSubmit
*/
public interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    // 根据ID查找数据
    QuestionSubmitVO getVOById(@Param("id") Long id);

    // 分页查找数据
    Page<QuestionSubmitVO> getVOByPage(@Param("params") QuestionSubmitQueryRequest questionSubmitQueryRequest, Page<T> page);

}




