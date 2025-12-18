package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("错误码接口测试")
class ErrorCodeTest {

    private enum TestErrorCode implements ErrorCode {
        TEST_ERROR("test.1001", "Test error message"),
        TEST_ERROR_WITH_ARGS("test.1002", "Test error with args: {0:param1}, {1:param2}"),
        TEST_ERROR_WITH_KEY_ARGS("test.1003", "Test error with key args: {0:name}, {1:age}");

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
    @DisplayName("测试错误码和消息模板")
    void testErrorCode() {
        assertEquals("test.1001", TestErrorCode.TEST_ERROR.code());
        assertEquals("Test error message", TestErrorCode.TEST_ERROR.messageTemplate());
    }

    @Test
    @DisplayName("测试使用索引参数获取消息")
    void testGetMessageWithIndexArgs() {
        String message = TestErrorCode.TEST_ERROR_WITH_ARGS.getMessage("arg1", "arg2");
        assertEquals("Test error with args: arg1, arg2", message);
    }

    @Test
    @DisplayName("测试使用键值参数获取消息")
    void testGetMessageWithKeyArgs() {
        String message = TestErrorCode.TEST_ERROR_WITH_KEY_ARGS.getMessage(
            java.util.Map.of("name", "John", "age", 25));
        assertEquals("Test error with key args: John, 25", message);
    }
}
