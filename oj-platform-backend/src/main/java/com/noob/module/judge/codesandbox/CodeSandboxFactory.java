package com.noob.module.judge.codesandbox;


import com.noob.module.judge.codesandbox.impl.ExampleCodeSandbox;
import com.noob.module.judge.codesandbox.impl.RemoteCodeSandbox;
import com.noob.module.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                // 如果用户设定type不在预期，则返回一个默认值或者报错提示
                return new ExampleCodeSandbox();
        }
    }
}
