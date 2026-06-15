package com.g2rain.common.web;

import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Token JWT载荷测试")
class TokenJWTPayloadTest {

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TokenJWTPayload payload = new TokenJWTPayload();

        // 测试基础主体属性
        assertNull(payload.getSessionType());
        assertNull(payload.getPassportId());
        assertNull(payload.getUserId());
        assertNull(payload.getName());
        assertFalse(payload.isAdminUser());
        assertNull(payload.getOrganType());
        assertNull(payload.getOrganId());
        assertNull(payload.getOrganName());
        assertFalse(payload.isAdminCompany());

        // 测试Token特有属性
        assertNull(payload.getIssuedAt());
        assertNull(payload.getExpireAt());
        assertNull(payload.getRefreshExpireAt());
        assertNull(payload.getClientId());
        assertNull(payload.getRoleIds());
    }

    @Test
    @DisplayName("测试设置和获取值")
    void testSetAndGetValues() {
        TokenJWTPayload payload = new TokenJWTPayload();

        // 设置基础主体属性
        payload.setSessionType(SessionType.USER);
        payload.setPassportId(123L);
        payload.setUserId(456L);
        payload.setName("John Doe");
        payload.setAdminUser(true);
        payload.setOrganType(OrganType.COMPANY);
        payload.setOrganId(789L);
        payload.setOrganName("Test Company");
        payload.setAdminCompany(true);

        // 设置Token特有属性
        payload.setIssuedAt(1234567890L);
        payload.setExpireAt(1234567890L + 3600L);
        payload.setRefreshExpireAt(1234567890L + 86400L);
        payload.setClientId("key123");
        payload.setRoleIds(List.of(1L, 2L));

        // 验证基础主体属性
        assertEquals(SessionType.USER, payload.getSessionType());
        assertEquals(123L, payload.getPassportId());
        assertEquals(456L, payload.getUserId());
        assertEquals("John Doe", payload.getName());
        assertTrue(payload.isAdminUser());
        assertEquals(OrganType.COMPANY, payload.getOrganType());
        assertEquals(789L, payload.getOrganId());
        assertEquals("Test Company", payload.getOrganName());
        assertTrue(payload.isAdminCompany());

        // 验证Token特有属性
        assertEquals(1234567890L, payload.getIssuedAt());
        assertEquals(1234567890L + 3600L, payload.getExpireAt());
        assertEquals(1234567890L + 86400L, payload.getRefreshExpireAt());
        assertEquals("key123", payload.getClientId());
        assertEquals(List.of(1L, 2L), payload.getRoleIds());
    }
}
