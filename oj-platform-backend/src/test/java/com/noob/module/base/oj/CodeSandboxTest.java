package com.noob.module.base.oj;

import com.noob.module.judge.codesandbox.CodeSandbox;
import com.noob.module.judge.codesandbox.CodeSandboxFactory;
import com.noob.module.judge.codesandbox.CodeSandboxProxy;
import com.noob.module.judge.codesandbox.impl.RemoteCodeSandbox;
import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.oj.model.questionSubmit.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @ClassName CodeAndBoxTest
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 9:32
 */
@SpringBootTest
public class CodeSandboxTest {

    // 沙箱参数：沙箱类型（如果没有指定则默认为example）
    @Value("${codesandbox.type:example}")
    private String sandboxType;


    // 优化3：引入代理类：代理增强实现日志打印
    @Test
    void test3(){
        System.out.println("当前沙箱配置参数：" + sandboxType);
        // 根据type动态生成相应的实现
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(sandboxType);
        // 代理增强
        codeSandbox = new CodeSandboxProxy(codeSandbox);

        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 调用沙箱接口
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }



    // 优化2：参数配置化
    @Test
    void test2(){
        System.out.println("当前沙箱配置参数：" + sandboxType);
        // 根据type动态生成相应的实现
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(sandboxType);
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 调用沙箱接口
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
    }

    public static void main(String[] args) {

    }

    // 优化1：工厂模式方式执行代码
    static void test1() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String type = scanner.nextLine();
            // 根据type动态生成相应的实现
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            String code = "int main() { }";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            // 调用沙箱接口
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
            System.out.println(executeCodeResponse);
        }
    }


    @Test
    void executeCode() {
        CodeSandbox codeSandbox = new RemoteCodeSandbox();
        String code = "int main() { }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

}
