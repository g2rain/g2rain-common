package com.g2rain.common.exception;

import com.g2rain.common.model.Result;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("异常转换器测试")
class ExceptionConverterTest {

    private enum TestErrorCode implements ErrorCode {
        TEST_ERROR("test.1001", "Test error message");

        private final String code;
        private final String messageTemplate;

        TestErrorCode(String code, String messageTemplate) {
            this.code = code;
            this.messageTemplate = messageTemplate;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String messageTemplate() {
            return messageTemplate;
        }
    }

    @Test
    @DisplayName("测试私有构造函数")
    void testPrivateConstructor() throws Exception {
        // 确保ExceptionConverter类不能被实例化
        java.lang.reflect.Constructor<ExceptionConverter> constructor = ExceptionConverter.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        // 尝试创建实例应该成功（虽然不推荐）
        assertDoesNotThrow(() -> constructor.newInstance());
    }

    @Test
    @DisplayName("测试通过结果创建业务异常")
    void testOf() {
        Result<String> result = Result.error("ERROR_CODE", "error message");
        BusinessException exception = ExceptionConverter.of(result);

        assertNotNull(exception);
        assertEquals("ERROR_CODE", exception.getErrorCode());
        assertEquals("error message", exception.getMessage());
    }

    @Test
    @DisplayName("测试构建消息-键值参数")
    void testBuildMessageWithKeyArgs() {
        Map<String, Object> keyArgs = new HashMap<>();
        keyArgs.put("key", "value");

        String message = ExceptionConverter.buildMessage(TestErrorCode.TEST_ERROR, keyArgs, null);
        assertEquals("Test error message", message);
    }

    @Test
    @DisplayName("测试构建消息-索引参数")
    void testBuildMessageWithIndexArgs() {
        Object[] indexArgs = {"arg1", "arg2"};

        String message = ExceptionConverter.buildMessage(TestErrorCode.TEST_ERROR, null, indexArgs);
        assertEquals("Test error message", message);
    }

    @Test
    @DisplayName("测试构建消息-无参数")
    void testBuildMessageWithoutArgs() {
        String message = ExceptionConverter.buildMessage(TestErrorCode.TEST_ERROR, null, null);
        assertEquals("Test error message", message);
    }
}
