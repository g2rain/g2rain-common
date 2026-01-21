package com.g2rain.common.web;

import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("主体上下文测试")
class PrincipalContextTest {

    @Test
    @DisplayName("测试创建主体上下文")
    void testOf() {
        PrincipalContext context = PrincipalContext.of();
        assertNotNull(context);
    }

    @Test
    @DisplayName("测试获取和设置值")
    void testGetValueAndSetValue() {
        PrincipalContext context = PrincipalContext.of();
        context.setClientId("client123");
        assertEquals("client123", context.getValue(PrincipalHeaders.CLIENT_ID));

        context.setValue(PrincipalHeaders.CLIENT_ID, "newClient123");
        assertEquals("newClient123", context.getValue(PrincipalHeaders.CLIENT_ID));

        // 测试null键
        assertNull(context.getValue(null));
        assertDoesNotThrow(() -> context.setValue(null, "value"));
    }

    @Test
    @DisplayName("测试设置会话类型")
    void testSetSessionType() {
        PrincipalContext context = PrincipalContext.of();
        context.setValue(PrincipalHeaders.SESSION_TYPE, "USER");
        assertEquals(SessionType.USER, context.getSessionType());
    }

    @Test
    @DisplayName("测试设置组织类型")
    void testSetOrganType() {
        PrincipalContext context = PrincipalContext.of();
        context.setValue(PrincipalHeaders.ORGAN_TYPE, "COMPANY");
        assertEquals(OrganType.COMPANY, context.getOrganType());
    }

    @Test
    @DisplayName("测试布尔值设置")
    void testBooleanValues() {
        PrincipalContext context = PrincipalContext.of();

        // 测试adminUser
        context.setValue(PrincipalHeaders.ADMIN_USER, "true");
        assertTrue(context.isAdminUser());

        context.setValue(PrincipalHeaders.ADMIN_USER, "false");
        assertFalse(context.isAdminUser());

        context.setValue(PrincipalHeaders.ADMIN_USER, null);
        assertFalse(context.isAdminUser());

        // 测试adminCompany
        context.setValue(PrincipalHeaders.ADMIN_COMPANY, "true");
        assertTrue(context.isAdminCompany());

        context.setValue(PrincipalHeaders.ADMIN_COMPANY, "false");
        assertFalse(context.isAdminCompany());

        context.setValue(PrincipalHeaders.ADMIN_COMPANY, null);
        assertFalse(context.isAdminCompany());

        // 测试debug
        context.setValue(PrincipalHeaders.DEBUG, "true");
        assertTrue(context.isDebug());

        context.setValue(PrincipalHeaders.DEBUG, "false");
        assertFalse(context.isDebug());

        context.setValue(PrincipalHeaders.DEBUG, null);
        assertFalse(context.isDebug());

        // 测试backEnd
        context.setValue(PrincipalHeaders.BACK_END, "true");
        assertTrue(context.isBackEnd());

        context.setValue(PrincipalHeaders.BACK_END, "false");
        assertFalse(context.isBackEnd());

        context.setValue(PrincipalHeaders.BACK_END, null);
        assertFalse(context.isBackEnd());
    }
}
