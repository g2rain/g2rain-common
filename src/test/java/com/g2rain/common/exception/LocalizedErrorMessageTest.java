package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("本地化错误信息测试")
class LocalizedErrorMessageTest {

    @Test
    @DisplayName("测试无参构造函数")
    void testNoArgsConstructor() {
        LocalizedErrorMessage message = new LocalizedErrorMessage();

        assertNull(message.getErrorCode());
        assertNull(message.getLocale());
        assertNull(message.getMessageTemplate());
    }

    @Test
    @DisplayName("测试全参构造函数")
    void testAllArgsConstructor() {
        LocalizedErrorMessage message = new LocalizedErrorMessage("ERROR_CODE", "en_US", "Error message");

        assertEquals("ERROR_CODE", message.getErrorCode());
        assertEquals("en_US", message.getLocale());
        assertEquals("Error message", message.getMessageTemplate());
    }

    @Test
    @DisplayName("测试属性设置和获取")
    void testSetAndGetProperties() {
        LocalizedErrorMessage message = new LocalizedErrorMessage();

        message.setErrorCode("ERROR_CODE");
        message.setLocale("en_US");
        message.setMessageTemplate("Error message");

        assertEquals("ERROR_CODE", message.getErrorCode());
        assertEquals("en_US", message.getLocale());
        assertEquals("Error message", message.getMessageTemplate());
    }
}
