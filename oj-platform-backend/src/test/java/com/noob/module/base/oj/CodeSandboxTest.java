package com.noob.module.base.oj;

import com.noob.module.judge.codesandbox.CodeSandbox;
import com.noob.module.judge.codesandbox.impl.RemoteCodeSandbox;
import com.noob.module.judge.codesandbox.model.ExecuteCodeRequest;
import com.noob.module.judge.codesandbox.model.ExecuteCodeResponse;
import com.noob.module.oj.model.questionSubmit.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName CodeAndBoxTest
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 9:32
 */
@SpringBootTest
public class CodeSandboxTest {

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
