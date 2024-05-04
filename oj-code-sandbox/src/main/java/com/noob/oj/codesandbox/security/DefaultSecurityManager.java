package com.noob.oj.codesandbox.security;

import java.security.Permission;

/**
 * @ClassName DefaultSecurityManager
 * @Description 默认安全管理器
 * @Author holic-x
 * @Date 2024/5/3 23:52
 */
public class DefaultSecurityManager extends SecurityManager{

    /**
     * 检查所有的权限
     * @param perm   the requested permission.
     */
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("默认不做任何权限限制");
        System.out.println(perm);
        super.checkPermission(perm);
    }
}