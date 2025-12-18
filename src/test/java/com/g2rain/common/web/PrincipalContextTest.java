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
    @DisplayName("测试获取会话类型字符串")
    void testGetSessionTypeStr() {
        PrincipalContext context = PrincipalContext.of();
        context.setSessionType(SessionType.USER);
        assertEquals("USER", context.getSessionTypeStr());

        context.setSessionType(null);
        assertNull(context.getSessionTypeStr());
    }

    @Test
    @DisplayName("测试获取组织类型字符串")
    void testGetOrganTypeStr() {
        PrincipalContext context = PrincipalContext.of();
        context.setOrganType(OrganType.COMPANY);
        assertEquals("COMPANY", context.getOrganTypeStr());

        context.setOrganType(null);
        assertNull(context.getOrganTypeStr());
    }

    @Test
    @DisplayName("测试获取租户ID")
    void testGetTenantId() {
        PrincipalContext context = PrincipalContext.of();
        context.setOrganType(OrganType.TENANT);
        context.setOrganId("tenant123");
        assertEquals("tenant123", context.getTenantId());

        context.setOrganType(OrganType.COMPANY);
        assertNull(context.getTenantId());

        context.setOrganType(null);
        assertNull(context.getTenantId());
    }

    @Test
    @DisplayName("测试获取公司ID")
    void testGetCompanyId() {
        PrincipalContext context = PrincipalContext.of();
        context.setOrganType(OrganType.COMPANY);
        context.setOrganId("company123");
        assertEquals("company123", context.getCompanyId());

        context.setOrganType(OrganType.TENANT);
        assertNull(context.getCompanyId());

        context.setOrganType(null);
        assertNull(context.getCompanyId());
    }

    @Test
    @DisplayName("测试是否为开发者")
    void testIsDeveloper() {
        PrincipalContext context = PrincipalContext.of();
        context.setUserId("user123");
        context.setApplicationOrganId("appOrg123");
        assertTrue(context.isDeveloper());

        context.setUserId(null);
        assertFalse(context.isDeveloper());

        context.setUserId("user123");
        context.setApplicationOrganId(null);
        assertFalse(context.isDeveloper());
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
