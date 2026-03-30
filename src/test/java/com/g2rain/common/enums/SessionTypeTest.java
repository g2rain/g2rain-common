package com.g2rain.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("会话类型枚举测试")
class SessionTypeTest {

    @Test
    @DisplayName("测试枚举值")
    void testEnumValues() {
        assertEquals(2, SessionType.values().length);
        assertNotNull(SessionType.USER);
        assertNotNull(SessionType.PASSPORT);
    }

    @Test
    @DisplayName("测试枚举名称查找")
    void testEnumValueOf() {
        assertEquals(SessionType.USER, SessionType.valueOf("USER"));
        assertEquals(SessionType.PASSPORT, SessionType.valueOf("PASSPORT"));
    }

    @Test
    @DisplayName("测试用户会话类型判断")
    void testIsUser() {
        assertTrue(SessionType.isUser(SessionType.USER));
        assertFalse(SessionType.isUser(null));
    }

    @Test
    @DisplayName("测试通行证会话类型判断")
    void testIsPassport() {
        assertTrue(SessionType.isPassport(SessionType.PASSPORT));
        assertFalse(SessionType.isPassport(SessionType.USER));
        assertFalse(SessionType.isPassport(null));
    }
}
