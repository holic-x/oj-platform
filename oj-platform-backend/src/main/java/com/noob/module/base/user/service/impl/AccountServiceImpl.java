package com.noob.module.base.user.service.impl;

import com.noob.framework.common.ErrorCode;
import com.noob.framework.exception.BusinessException;
import com.noob.module.base.user.model.vo.LoginUserVO;
import com.noob.module.base.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author Huh-x
 * @Date 2024 2024/4/30 14:19
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "noob";

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

    @Override
    public void userLogout() {
        // ShiroUtil.deleteCache();

        // 退出登陆
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            // 销毁SESSION(清理权限缓存)
            subject.logout();
        }
    }
}
