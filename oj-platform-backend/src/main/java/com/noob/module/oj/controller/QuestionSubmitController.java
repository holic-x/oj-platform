package com.noob.module.oj.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.noob.framework.annotation.AuthCheck;
import com.noob.framework.common.*;
import com.noob.framework.constant.UserConstant;
import com.noob.framework.exception.BusinessException;
import com.noob.framework.exception.ThrowUtils;
import com.noob.module.base.model.entity.User;
import com.noob.module.base.service.UserService;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitAddRequest;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitQueryRequest;
import com.noob.module.oj.model.questionSubmit.dto.QuestionSubmitUpdateRequest;
import com.noob.module.oj.model.questionSubmit.entity.QuestionSubmit;
import com.noob.module.oj.model.questionSubmit.vo.QuestionSubmitVO;
import com.noob.module.oj.service.QuestionSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 问题提交接口
 */
@RestController
@RequestMapping("/questionSubmit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    // region 增删改查
    /**
     * 创建
     * @param questionSubmitAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest, HttpServletRequest request) {
        if (questionSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitAddRequest, questionSubmit);

        // 设置状态
        questionSubmitService.validQuestionSubmit(questionSubmit, true);

        User loginUser = userService.getLoginUser(request);
        questionSubmit.setCreater(loginUser.getId());
        questionSubmit.setUpdater(loginUser.getId());
        questionSubmit.setCreateTime(new Date());
        questionSubmit.setUpdateTime(new Date());

        // 新增
        boolean result = questionSubmitService.save(questionSubmit);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionSubmitId = questionSubmit.getId();
        return ResultUtils.success(newQuestionSubmitId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestionSubmit(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionSubmit oldQuestionSubmit = questionSubmitService.getById(id);
        ThrowUtils.throwIf(oldQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestionSubmit.getCreater().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionSubmitService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionSubmitUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestionSubmit(@RequestBody QuestionSubmitUpdateRequest questionSubmitUpdateRequest) {
        if (questionSubmitUpdateRequest == null || questionSubmitUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUpdateTime(new Date());
        BeanUtils.copyProperties(questionSubmitUpdateRequest, questionSubmit);

        // 参数校验
        questionSubmitService.validQuestionSubmit(questionSubmit, false);
        long id = questionSubmitUpdateRequest.getId();
        // 判断是否存在
        QuestionSubmit oldQuestionSubmit = questionSubmitService.getById(id);
        ThrowUtils.throwIf(oldQuestionSubmit == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionSubmitService.updateById(questionSubmit);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionSubmitVO> getQuestionSubmitVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionSubmitVO questionSubmitVO = questionSubmitService.getVOById(id,request);
        return ResultUtils.success(questionSubmitVO);
    }


    /**
     * 分页获取列表（自定义SQL处理）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/getVOByPage")
    public BaseResponse<Page<QuestionSubmitVO>> getVOByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                     HttpServletRequest request) {
        // 获取分页信息
        return ResultUtils.success(questionSubmitService.getVOByPage(questionSubmitQueryRequest));
    }

    // endregion

    // --------------- 批量操作定义 ---------------
    /**
     * 批量删除问题提交
     *
     * @param batchDeleteRequest
     * @param request
     * @return
     */
    @PostMapping("/batchDeleteQuestionSubmit")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> batchDeleteQuestionSubmit(@RequestBody BatchDeleteRequest batchDeleteRequest, HttpServletRequest request) {
        if (batchDeleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 执行批量操作
        List<Long> idList = batchDeleteRequest.getIdList();
        if(idList == null || idList.isEmpty()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"指定操作列表不能为空");
        }
        // 批量删除
        boolean b = questionSubmitService.removeBatchByIds(idList);
        return ResultUtils.success(b);
    }

}
