package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("字段错误类测试")
class FieldErrorTest {

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
    @DisplayName("测试字段错误正常创建")
    void testFieldErrorCreation() {
        FieldError fieldError = new FieldError("fieldName", TestErrorCode.TEST_ERROR);

        assertEquals("fieldName", fieldError.getField());
        assertEquals("test.1001", fieldError.getErrorCode());
        assertEquals("Test error message", fieldError.getErrorMessage());
    }

    @Test
    @DisplayName("测试字段错误创建时传入null值")
    void testFieldErrorCreationWithNullValues() {
        FieldError fieldError = new FieldError(null, TestErrorCode.TEST_ERROR);

        assertNull(fieldError.getField());
        assertEquals("test.1001", fieldError.getErrorCode());
        assertEquals("Test error message", fieldError.getErrorMessage());
    }

    @Test
    @DisplayName("测试字段错误创建时传入空字符串")
    void testFieldErrorCreationWithEmptyValues() {
        FieldError fieldError = new FieldError("", TestErrorCode.TEST_ERROR);

        assertEquals("", fieldError.getField());
        assertEquals("test.1001", fieldError.getErrorCode());
        assertEquals("Test error message", fieldError.getErrorMessage());
    }
}
