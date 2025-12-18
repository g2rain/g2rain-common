package com.g2rain.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("组织类型枚举测试")
class OrganTypeTest {

    @Test
    @DisplayName("测试枚举值")
    void testEnumValues() {
        assertEquals(4, OrganType.values().length);
        assertNotNull(OrganType.COMPANY);
        assertNotNull(OrganType.TENANT);
    }

    @Test
    @DisplayName("测试枚举名称查找")
    void testEnumValueOf() {
        assertEquals(OrganType.COMPANY, OrganType.valueOf("COMPANY"));
        assertEquals(OrganType.TENANT, OrganType.valueOf("TENANT"));
    }

    @Test
    @DisplayName("测试获取类型值")
    void testGetType() {
        assertEquals("公司", OrganType.COMPANY.getType());
        assertEquals("租户", OrganType.TENANT.getType());
    }

    @Test
    @DisplayName("测试根据类型字符串查找枚举")
    void testTypeOf() {
        assertEquals(OrganType.COMPANY, OrganType.typeOf("公司"));
        assertEquals(OrganType.TENANT, OrganType.typeOf("租户"));
        assertNull(OrganType.typeOf("unknown"));
    }

    @Test
    @DisplayName("测试是否为公司类型")
    void testIsCompany() {
        assertTrue(OrganType.isCompany(OrganType.COMPANY));
        assertFalse(OrganType.isCompany(OrganType.TENANT));
        assertFalse(OrganType.isCompany((OrganType) null));
        assertTrue(OrganType.isCompany("COMPANY"));
        assertFalse(OrganType.isCompany("TENANT"));
        assertFalse(OrganType.isCompany((String) null));
    }

    @Test
    @DisplayName("测试是否为租户类型")
    void testIsTenant() {
        assertFalse(OrganType.isTenant(OrganType.COMPANY));
        assertTrue(OrganType.isTenant(OrganType.TENANT));
        assertFalse(OrganType.isTenant((OrganType) null));
        assertFalse(OrganType.isTenant("COMPANY"));
        assertTrue(OrganType.isTenant("TENANT"));
        assertFalse(OrganType.isTenant((String) null));
    }
}
