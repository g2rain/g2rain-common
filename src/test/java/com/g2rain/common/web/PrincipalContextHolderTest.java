package com.g2rain.common.web;

import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("主体上下文持有者测试")
class PrincipalContextHolderTest {

    @AfterEach
    void tearDown() {
        // 清理线程本地变量
        PrincipalContextHolder.remove();
    }

    @Test
    @DisplayName("测试获取主体上下文")
    void testGet() {
        PrincipalContext context = PrincipalContextHolder.get();
        assertNotNull(context);

        // 再次获取应该是同一个实例
        PrincipalContext context2 = PrincipalContextHolder.get();
        assertSame(context, context2);
    }

    @Test
    @DisplayName("测试移除主体上下文")
    void testRemove() {
        PrincipalContext context1 = PrincipalContextHolder.get();
        PrincipalContextHolder.remove();
        PrincipalContext context2 = PrincipalContextHolder.get();

        // 移除后应该创建新的实例
        assertNotSame(context1, context2);
    }

    @Test
    @DisplayName("测试设置和获取客户端ID")
    void testSetAndGetClientId() {
        PrincipalContextHolder.setClientId("client123");
        assertEquals("client123", PrincipalContextHolder.getClientId());
    }

    @Test
    @DisplayName("测试设置和获取跟踪ID")
    void testSetAndGetTraceId() {
        PrincipalContextHolder.setTraceId("trace123");
        assertEquals("trace123", PrincipalContextHolder.getTraceId());
    }

    @Test
    @DisplayName("测试设置和获取请求ID")
    void testSetAndGetRequestId() {
        PrincipalContextHolder.setRequestId("request123");
        assertEquals("request123", PrincipalContextHolder.getRequestId());
    }

    @Test
    @DisplayName("测试设置和获取请求时间")
    void testSetAndGetRequestTime() {
        PrincipalContextHolder.setRequestTime("2023-10-15 14:30:45");
        assertEquals("2023-10-15 14:30:45", PrincipalContextHolder.getRequestTime());
    }

    @Test
    @DisplayName("测试设置和获取会话类型")
    void testSetAndGetSessionType() {
        PrincipalContextHolder.setSessionType(SessionType.USER);
        assertEquals(SessionType.USER, PrincipalContextHolder.getSessionType());
    }

    @Test
    @DisplayName("测试设置和获取通行证ID")
    void testSetAndGetPassportId() {
        PrincipalContextHolder.setPassportId("passport123");
        assertEquals("passport123", PrincipalContextHolder.getPassportId());
    }

    @Test
    @DisplayName("测试设置和获取用户ID")
    void testSetAndGetUserId() {
        PrincipalContextHolder.setUserId("user123");
        assertEquals("user123", PrincipalContextHolder.getUserId());
    }

    @Test
    @DisplayName("测试设置和获取名称")
    void testSetAndGetName() {
        PrincipalContextHolder.setName("John Doe");
        assertEquals("John Doe", PrincipalContextHolder.getName());
    }

    @Test
    @DisplayName("测试设置和获取管理员用户标记")
    void testSetAndGetAdminUser() {
        PrincipalContextHolder.setAdminUser(true);
        assertTrue(PrincipalContextHolder.isAdminUser());

        PrincipalContextHolder.setAdminUser(false);
        assertFalse(PrincipalContextHolder.isAdminUser());
    }

    @Test
    @DisplayName("测试设置和获取组织类型")
    void testSetAndGetOrganType() {
        PrincipalContextHolder.setOrganType(OrganType.COMPANY);
        assertEquals(OrganType.COMPANY, PrincipalContextHolder.getOrganType());
    }

    @Test
    @DisplayName("测试设置和获取组织ID")
    void testSetAndGetOrganId() {
        PrincipalContextHolder.setOrganId("organ123");
        assertEquals("organ123", PrincipalContextHolder.getOrganId());
    }

    @Test
    @DisplayName("测试设置和获取组织名称")
    void testSetAndGetOrganName() {
        PrincipalContextHolder.setOrganName("Test Org");
        assertEquals("Test Org", PrincipalContextHolder.getOrganName());
    }

    @Test
    @DisplayName("测试设置和获取管理员公司标记")
    void testSetAndGetAdminCompany() {
        PrincipalContextHolder.setAdminCompany(true);
        assertTrue(PrincipalContextHolder.isAdminCompany());

        PrincipalContextHolder.setAdminCompany(false);
        assertFalse(PrincipalContextHolder.isAdminCompany());
    }

    @Test
    @DisplayName("测试设置和获取应用ID")
    void testSetAndGetApplicationId() {
        PrincipalContextHolder.setApplicationId("app123");
        assertEquals("app123", PrincipalContextHolder.getApplicationId());
    }

    @Test
    @DisplayName("测试设置和获取应用组织ID")
    void testSetAndGetApplicationOrganId() {
        PrincipalContextHolder.setApplicationOrganId("appOrgan123");
        assertEquals("appOrgan123", PrincipalContextHolder.getApplicationOrganId());
    }

    @Test
    @DisplayName("测试设置和获取调试标记")
    void testSetAndGetDebug() {
        PrincipalContextHolder.setDebug(true);
        assertTrue(PrincipalContextHolder.isDebug());

        PrincipalContextHolder.setDebug(false);
        assertFalse(PrincipalContextHolder.isDebug());
    }

    @Test
    @DisplayName("测试设置和获取后端标记")
    void testSetAndGetBackEnd() {
        PrincipalContextHolder.setBackEnd(true);
        assertTrue(PrincipalContextHolder.isBackEnd());

        PrincipalContextHolder.setBackEnd(false);
        assertFalse(PrincipalContextHolder.isBackEnd());
    }
}
