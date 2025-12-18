package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("基础DTO测试")
class BaseDtoTest {

    @Test
    @DisplayName("测试基础DTO属性")
    void testBaseDtoProperties() {
        BaseDto dto = new BaseDto();

        // 测试默认值
        assertNull(dto.getId());
        assertNull(dto.getUpdateTime());
        assertNull(dto.getCreateTime());

        // 测试设置和获取属性
        dto.setId(1L);
        dto.setUpdateTime("2023-10-15 14:30:45");
        dto.setCreateTime("2023-10-15 14:30:45");

        assertEquals(1L, dto.getId());
        assertEquals("2023-10-15 14:30:45", dto.getUpdateTime());
        assertEquals("2023-10-15 14:30:45", dto.getCreateTime());
    }
}
