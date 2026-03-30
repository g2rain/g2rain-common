package com.g2rain.common.web;

import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("基础主体类测试")
class BasePrincipalTest {

    @Test
    @DisplayName("测试基础主体类创建和属性访问")
    void testBasePrincipal() {
        BasePrincipal principal = new BasePrincipal();

        // 测试默认值
        assertNull(principal.getSessionType());
        assertNull(principal.getPassportId());
        assertNull(principal.getUserId());
        assertNull(principal.getName());
        assertFalse(principal.isAdminUser());
        assertNull(principal.getOrganType());
        assertNull(principal.getOrganId());
        assertNull(principal.getOrganName());
        assertFalse(principal.isAdminCompany());

        // 测试设置和获取属性
        principal.setSessionType(SessionType.USER);
        principal.setPassportId(123L);
        principal.setUserId(456L);
        principal.setName("John Doe");
        principal.setAdminUser(true);
        principal.setOrganType(OrganType.COMPANY);
        principal.setOrganId(789L);
        principal.setOrganName("Test Company");
        principal.setAdminCompany(true);

        assertEquals(SessionType.USER, principal.getSessionType());
        assertEquals(123L, principal.getPassportId());
        assertEquals(456L, principal.getUserId());
        assertEquals("John Doe", principal.getName());
        assertTrue(principal.isAdminUser());
        assertEquals(OrganType.COMPANY, principal.getOrganType());
        assertEquals(789L, principal.getOrganId());
        assertEquals("Test Company", principal.getOrganName());
        assertTrue(principal.isAdminCompany());
    }
}
