package com.noob.oj.codesandbox.security;

import cn.hutool.core.io.FileUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 测试安全管理器
 */
@Deprecated
public class TestSecurityManager {

    public static void main(String[] args) {
        System.setSecurityManager(new MySecurityManager());
        // 限制读权限
//        List<String> strings = FileUtil.readUtf8Lines(FileUtil.file("E:\\workspace\\Git\\github\\PROJECT\\noob\\oj-platform\\oj-code-sandbox\\src\\main\\resources\\application.yml"));
//        System.out.println(strings);

        // 限制写权限
        FileUtil.writeString("aa", "aaa", Charset.defaultCharset());
    }
}
