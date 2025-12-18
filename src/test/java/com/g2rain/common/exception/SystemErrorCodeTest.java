package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("系统错误码测试")
class SystemErrorCodeTest {

    @Test
    @DisplayName("测试JSON序列化错误")
    void testJsonSerializerError() {
        assertEquals("system.50006", SystemErrorCode.JSON_SERIALIZER_ERROR.code());
        assertEquals("数据序列化失败", SystemErrorCode.JSON_SERIALIZER_ERROR.messageTemplate());
        assertEquals("数据序列化失败", SystemErrorCode.JSON_SERIALIZER_ERROR.getMessage());
    }

    @Test
    @DisplayName("测试JSON序列化错误带参数")
    void testJsonSerializerErrorWithArgs() {
        String message = SystemErrorCode.JSON_SERIALIZER_ERROR.getMessage("test args");
        assertEquals("数据序列化失败", message);
    }
}
