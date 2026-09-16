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
        assertEquals(4, SessionType.values().length);
        assertNotNull(SessionType.USER);
        assertNotNull(SessionType.PASSPORT);
        assertNotNull(SessionType.ANONYMOUS);
        assertNotNull(SessionType.MEMBER);
    }

    @Test
    @DisplayName("测试枚举名称查找")
    void testEnumValueOf() {
        assertEquals(SessionType.USER, SessionType.valueOf("USER"));
        assertEquals(SessionType.PASSPORT, SessionType.valueOf("PASSPORT"));
        assertEquals(SessionType.ANONYMOUS, SessionType.valueOf("ANONYMOUS"));
        assertEquals(SessionType.MEMBER, SessionType.valueOf("MEMBER"));
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

    @Test
    @DisplayName("测试匿名会话类型判断")
    void testIsAnonymous() {
        assertTrue(SessionType.isAnonymous(SessionType.ANONYMOUS));
        assertFalse(SessionType.isAnonymous(SessionType.USER));
        assertFalse(SessionType.isAnonymous(null));
    }

    @Test
    @DisplayName("测试会员会话类型判断")
    void testIsMember() {
        assertTrue(SessionType.isMember(SessionType.MEMBER));
        assertFalse(SessionType.isMember(SessionType.USER));
        assertFalse(SessionType.isMember(null));
    }
}
