package com.g2rain.common.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("主体头部枚举测试")
class PrincipalHeadersTest {

    @Test
    @DisplayName("测试枚举值")
    void testEnumValues() {
        assertEquals(18, PrincipalHeaders.values().length);
    }

    @Test
    @DisplayName("测试客户端ID头部")
    void testClientId() {
        assertEquals("X-CLIENT-ID", PrincipalHeaders.CLIENT_ID.getUpper());
        assertEquals("x-client-id", PrincipalHeaders.CLIENT_ID.getLower());
    }

    @Test
    @DisplayName("测试跟踪ID头部")
    void testTraceId() {
        assertEquals("X-TRACE-ID", PrincipalHeaders.TRACE_ID.getUpper());
        assertEquals("x-trace-id", PrincipalHeaders.TRACE_ID.getLower());
    }

    @Test
    @DisplayName("测试请求ID头部")
    void testRequestId() {
        assertEquals("X-REQUEST-ID", PrincipalHeaders.REQUEST_ID.getUpper());
        assertEquals("x-request-id", PrincipalHeaders.REQUEST_ID.getLower());
    }

    @Test
    @DisplayName("测试请求时间头部")
    void testRequestTime() {
        assertEquals("X-REQUEST-TIME", PrincipalHeaders.REQUEST_TIME.getUpper());
        assertEquals("x-request-time", PrincipalHeaders.REQUEST_TIME.getLower());
    }

    @Test
    @DisplayName("测试会话类型头部")
    void testSessionType() {
        assertEquals("X-SESSION-TYPE", PrincipalHeaders.SESSION_TYPE.getUpper());
        assertEquals("x-session-type", PrincipalHeaders.SESSION_TYPE.getLower());
    }

    @Test
    @DisplayName("测试通行证ID头部")
    void testPassportId() {
        assertEquals("X-PASSPORT-ID", PrincipalHeaders.PASSPORT_ID.getUpper());
        assertEquals("x-passport-id", PrincipalHeaders.PASSPORT_ID.getLower());
    }

    @Test
    @DisplayName("测试用户ID头部")
    void testUserId() {
        assertEquals("X-USER-ID", PrincipalHeaders.USER_ID.getUpper());
        assertEquals("x-user-id", PrincipalHeaders.USER_ID.getLower());
    }

    @Test
    @DisplayName("测试名称头部")
    void testName() {
        assertEquals("X-NAME", PrincipalHeaders.NAME.getUpper());
        assertEquals("x-name", PrincipalHeaders.NAME.getLower());
    }

    @Test
    @DisplayName("测试管理员用户头部")
    void testAdminUser() {
        assertEquals("X-ADMIN-USER", PrincipalHeaders.ADMIN_USER.getUpper());
        assertEquals("x-admin-user", PrincipalHeaders.ADMIN_USER.getLower());
    }

    @Test
    @DisplayName("测试组织类型头部")
    void testOrganType() {
        assertEquals("X-ORGAN-TYPE", PrincipalHeaders.ORGAN_TYPE.getUpper());
        assertEquals("x-organ-type", PrincipalHeaders.ORGAN_TYPE.getLower());
    }

    @Test
    @DisplayName("测试组织ID头部")
    void testOrganId() {
        assertEquals("X-ORGAN-ID", PrincipalHeaders.ORGAN_ID.getUpper());
        assertEquals("x-organ-id", PrincipalHeaders.ORGAN_ID.getLower());
    }

    @Test
    @DisplayName("测试组织名称头部")
    void testOrganName() {
        assertEquals("X-ORGAN-NAME", PrincipalHeaders.ORGAN_NAME.getUpper());
        assertEquals("x-organ-name", PrincipalHeaders.ORGAN_NAME.getLower());
    }

    @Test
    @DisplayName("测试管理员公司头部")
    void testAdminCompany() {
        assertEquals("X-ADMIN-COMPANY", PrincipalHeaders.ADMIN_COMPANY.getUpper());
        assertEquals("x-admin-company", PrincipalHeaders.ADMIN_COMPANY.getLower());
    }

    @Test
    @DisplayName("测试应用ID头部")
    void testAppId() {
        assertEquals("X-APP-ID", PrincipalHeaders.APP_ID.getUpper());
        assertEquals("x-app-id", PrincipalHeaders.APP_ID.getLower());
    }

    @Test
    @DisplayName("测试应用组织ID头部")
    void testAppOrganId() {
        assertEquals("X-APP-ORGAN-ID", PrincipalHeaders.APP_ORGAN_ID.getUpper());
        assertEquals("x-app-organ-id", PrincipalHeaders.APP_ORGAN_ID.getLower());
    }

    @Test
    @DisplayName("测试调试头部")
    void testDebug() {
        assertEquals("X-DEBUG", PrincipalHeaders.DEBUG.getUpper());
        assertEquals("x-debug", PrincipalHeaders.DEBUG.getLower());
    }

    @Test
    @DisplayName("测试后端头部")
    void testBackEnd() {
        assertEquals("X-BACK-END", PrincipalHeaders.BACK_END.getUpper());
        assertEquals("x-back-end", PrincipalHeaders.BACK_END.getLower());
    }
}
