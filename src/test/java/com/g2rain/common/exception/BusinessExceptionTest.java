package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("业务异常测试")
class BusinessExceptionTest {

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
    @DisplayName("测试通过错误码创建业务异常")
    void testBusinessExceptionCreationWithErrorCode() {
        BusinessException exception = new BusinessException(TestErrorCode.TEST_ERROR);

        assertEquals("test.1001", exception.getErrorCode());
        assertEquals("Test error message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("测试通过错误码和参数创建业务异常")
    void testBusinessExceptionCreationWithErrorCodeAndArgs() {
        BusinessException exception = new BusinessException(TestErrorCode.TEST_ERROR, "arg1", "arg2");

        assertEquals("test.1001", exception.getErrorCode());
        assertEquals("Test error message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("测试通过错误码和原因创建业务异常")
    void testBusinessExceptionCreationWithErrorCodeAndCause() {
        Throwable cause = new RuntimeException("Cause");
        BusinessException exception = new BusinessException(TestErrorCode.TEST_ERROR, cause);

        assertEquals("test.1001", exception.getErrorCode());
        assertEquals("Test error message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    @DisplayName("测试通过错误码、原因和参数创建业务异常")
    void testBusinessExceptionCreationWithErrorCodeArgsAndCause() {
        Throwable cause = new RuntimeException("Cause");
        BusinessException exception = new BusinessException(TestErrorCode.TEST_ERROR, java.util.Map.of("key1", "value1"), "arg1", "arg2");

        assertEquals("test.1001", exception.getErrorCode());
        assertEquals("Test error message", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("测试通过值和原因创建业务异常")
    void testBusinessExceptionCreationWithDirectValuesAndCause() {
        Throwable cause = new RuntimeException("Cause");
        BusinessException exception = new BusinessException(TestErrorCode.TEST_ERROR, cause);

        assertEquals("test.1001", exception.getErrorCode());
        assertEquals("Test error message", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
