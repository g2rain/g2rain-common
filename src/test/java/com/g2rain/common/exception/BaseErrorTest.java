package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("基础错误类测试")
class BaseErrorTest {

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
    @DisplayName("测试基础错误创建")
    void testBaseErrorCreation() {
        BaseError error = new BaseError(TestErrorCode.TEST_ERROR, "Test error message", null, null);

        assertEquals("test.1001", error.getErrorCode());
        assertEquals("Test error message", error.getErrorMessage());
        assertNull(error.getKeyArgs());
        assertNull(error.getIndexArgs());
    }

    @Test
    @DisplayName("测试带键值参数的基础错误创建")
    void testBaseErrorWithKeyArgs() {
        java.util.Map<String, Object> keyArgs = new java.util.HashMap<>();
        keyArgs.put("key1", "value1");
        keyArgs.put("key2", "value2");

        BaseError error = new BaseError(TestErrorCode.TEST_ERROR, "Test error message", keyArgs, null);

        assertNotNull(error.getKeyArgs());
        assertEquals(2, error.getKeyArgs().size());
        assertEquals("value1", error.getKeyArgs().get("key1"));
        assertEquals("value2", error.getKeyArgs().get("key2"));
    }

    @Test
    @DisplayName("测试带索引参数的基础错误创建")
    void testBaseErrorWithIndexArgs() {
        Object[] indexArgs = {"arg1", "arg2"};
        BaseError error = new BaseError(TestErrorCode.TEST_ERROR, "Test error message", null, indexArgs);

        assertNotNull(error.getIndexArgs());
        assertEquals(2, error.getIndexArgs().length);
        assertEquals("arg1", error.getIndexArgs()[0]);
        assertEquals("arg2", error.getIndexArgs()[1]);
    }

    @Test
    @DisplayName("测试直接使用值创建基础错误")
    void testBaseErrorConstructorWithDirectValues() {
        BaseError error = new BaseError("test.2001", "Direct error message", null, null);

        assertEquals("test.2001", error.getErrorCode());
        assertEquals("Direct error message", error.getErrorMessage());
    }
}
