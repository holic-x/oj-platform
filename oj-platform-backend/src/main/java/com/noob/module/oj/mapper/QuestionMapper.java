package com.noob.module.oj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noob.module.oj.model.question.dto.QuestionQueryRequest;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.question.vo.QuestionVO;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

/**
* @author HUAWEI
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-04-23 09:25:47
* @Entity com.noob.module.oj.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    // 根据ID查找数据
    QuestionVO getVOById(@Param("id") Long id);

    // 分页查找数据
    Page<QuestionVO> getVOByPage(@Param("params") QuestionQueryRequest questionQueryRequest, Page<T> page);

}




