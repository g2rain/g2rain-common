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
        assertEquals(5, SessionType.values().length);
        assertNotNull(SessionType.ANONYMOUS);
        assertNotNull(SessionType.USER);
        assertNotNull(SessionType.PASSPORT);
        assertNotNull(SessionType.APP_TENANT);
        assertNotNull(SessionType.APP);
    }

    @Test
    @DisplayName("测试枚举名称查找")
    void testEnumValueOf() {
        assertEquals(SessionType.ANONYMOUS, SessionType.valueOf("ANONYMOUS"));
        assertEquals(SessionType.USER, SessionType.valueOf("USER"));
        assertEquals(SessionType.PASSPORT, SessionType.valueOf("PASSPORT"));
        assertEquals(SessionType.APP_TENANT, SessionType.valueOf("APP_TENANT"));
        assertEquals(SessionType.APP, SessionType.valueOf("APP"));
    }

    @Test
    @DisplayName("测试匿名会话类型判断")
    void testIsAnonymous() {
        assertTrue(SessionType.isAnonymous(SessionType.ANONYMOUS));
        assertFalse(SessionType.isAnonymous(SessionType.USER));
        assertFalse(SessionType.isAnonymous(null));
    }

    @Test
    @DisplayName("测试用户会话类型判断")
    void testIsUser() {
        assertTrue(SessionType.isUser(SessionType.USER));
        assertFalse(SessionType.isUser(SessionType.ANONYMOUS));
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
    @DisplayName("测试应用会话类型判断")
    void testIsApp() {
        assertTrue(SessionType.isApp(SessionType.APP));
        assertFalse(SessionType.isApp(SessionType.USER));
        assertFalse(SessionType.isApp(null));
    }

    @Test
    @DisplayName("测试应用租户会话类型判断")
    void testIsAppTenant() {
        assertTrue(SessionType.isAppTenant(SessionType.APP_TENANT));
        assertFalse(SessionType.isAppTenant(SessionType.APP));
        assertFalse(SessionType.isAppTenant(null));
    }
}
