package com.noob.module.base.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noob.framework.common.ErrorCode;
import com.noob.framework.constant.CommonConstant;
import com.noob.framework.constant.UserConstant;
import com.noob.framework.exception.BusinessException;
import com.noob.framework.exception.ThrowUtils;
import com.noob.framework.realm.ShiroUtil;
import com.noob.framework.utils.SqlUtils;
import com.noob.module.base.user.mapper.UserMapper;
import com.noob.module.base.user.model.dto.user.UserQueryRequest;
import com.noob.module.base.user.model.entity.User;
import com.noob.module.base.user.model.vo.LoginUserVO;
import com.noob.module.base.user.model.vo.UserVO;
import com.noob.module.base.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "noob";

    @Override
    public long userRegister(String userName,String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userName,userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 3.调用subject方法进行登陆校验
        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, userPassword);
        Subject subject = SecurityUtils.getSubject();
        // 当调用Subject.login()方法时，在shiro框架内部会去调用realm的认证方法
        subject.login(token);
        // 设置session失效时间：永不超时
        subject.getSession().setTimeout(-1001);

        // 4.登陆鉴权通过，返回当前登陆用户信息
        LoginUserVO currentUser = (LoginUserVO)subject.getPrincipal();
        return currentUser;
    }


    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public User getLoginUser() {
        // 调整为shiro接管，获取并校验当前登录用户信息和状态
        LoginUserVO currentUser = ShiroUtil.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库获取用户最新信息
        User findUser = this.getById(currentUser.getId());
        if(findUser==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"当前登录用户信息不存在");
        }
        // 校验用户状态
        Integer currentUserStatus = findUser.getUserStatus();
        ThrowUtils.throwIf(UserConstant.USER_STATUS_FORBID.equals(currentUserStatus), ErrorCode.USER_STATUS_FORBID_ERROR);
        // 返回登录用户信息
        return findUser;

    }

    /**
     * 用户注销
     *
     */
    @Override
    public boolean userLogout() {
        // ShiroUtil.deleteCache();

        // 退出登陆
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // 销毁SESSION(清理权限缓存)
            subject.logout();
        }
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String userAccount = userQueryRequest.getUserAccount();
        Integer userStatus = userQueryRequest.getUserStatus();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.eq(StringUtils.isNotBlank(userAccount), "userAccount", userAccount);
        queryWrapper.eq(userStatus != null, "userStatus", userStatus);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}
