package com.noob.module.oj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.noob.framework.annotation.AuthCheck;
import com.noob.framework.common.*;
import com.noob.framework.constant.UserConstant;
import com.noob.framework.exception.BusinessException;
import com.noob.framework.exception.ThrowUtils;
import com.noob.module.base.model.entity.User;
import com.noob.module.base.service.UserService;
import com.noob.module.oj.model.question.dto.*;
import com.noob.module.oj.model.question.entity.Question;
import com.noob.module.oj.model.question.vo.QuestionVO;
import com.noob.module.oj.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 问题接口
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查
    /**
     * 创建
     * @param questionAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);


        User loginUser = userService.getLoginUser(request);
        question.setCreater(loginUser.getId());
        question.setUpdater(loginUser.getId());
        question.setCreateTime(new Date());
        question.setUpdateTime(new Date());

        // 设置标签
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }

        // 设置测试用例
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }

        // 设置题目配置
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }

        question.setFavourNum(0);
        question.setThumbNum(0);


        // 校验问题信息
        questionService.validQuestion(question, true);

        // 新增
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getCreater().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        question.setUpdateTime(new Date());
        BeanUtils.copyProperties(questionUpdateRequest, question);

        // 参数校验
        questionService.validQuestion(question, false);
        long id = questionUpdateRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);


        // 更新问题信息
        List<String> tags = questionUpdateRequest.getTags();
        if (tags != null) {
            question.setTags(GSON.toJson(tags));
        }

        // 设置测试用例
        List<JudgeCase> judgeCase = questionUpdateRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(GSON.toJson(judgeCase));
        }

        // 设置题目配置
        JudgeConfig judgeConfig = questionUpdateRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(GSON.toJson(judgeConfig));
        }


        // 校验问题信息
        questionService.validQuestion(question, false);

        // 执行修改操作
        boolean result = questionService.updateById(question);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionVO questionVO = questionService.getVOById(id,request);
        return ResultUtils.success(questionVO);
    }


    /**
     * 分页获取列表（自定义SQL处理）
     *
     * @param questionQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/getVOByPage")
    public BaseResponse<Page<QuestionVO>> getVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                     HttpServletRequest request) {
        // 获取分页信息
        return ResultUtils.success(questionService.getVOByPage(questionQueryRequest));
    }

    // endregion

    // --------------- 批量操作定义 ---------------
    /**
     * 批量删除问题
     *
     * @param batchDeleteRequest
     * @param request
     * @return
     */
    @PostMapping("/batchDeleteQuestion")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> batchDeleteQuestion(@RequestBody BatchDeleteRequest batchDeleteRequest, HttpServletRequest request) {
        if (batchDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 执行批量操作
        List<Long> idList = batchDeleteRequest.getIdList();
        if(idList == null || idList.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"指定操作列表不能为空");
        }
        // 批量删除
        boolean b = questionService.removeBatchByIds(idList);
        return ResultUtils.success(b);
    }

}
