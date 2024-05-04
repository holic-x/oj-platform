package com.noob.oj.codesandbox.security;

import java.security.Permission;

/**
 * @ClassName DenySecurityManager
 * @Description 所有权限拒绝
 * @Author holic-x
 * @Date 2024/5/3 23:53
 */
public class DenySecurityManager extends SecurityManager{

    /**
     * 检查所有的权限
     * @param perm   the requested permission.
     */
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常：" + perm.toString());
    }
}
