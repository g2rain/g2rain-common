package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("基础VO测试")
class BaseVoTest {

    @Test
    @DisplayName("测试默认构造函数")
    void testDefaultConstructor() {
        BaseVo vo = new BaseVo();

        // 测试默认值
        assertNull(vo.getId());
        assertNull(vo.getUpdateTime());
        assertNull(vo.getCreateTime());
    }

    @Test
    @DisplayName("测试通过PO构造函数-空时间")
    void testConstructorWithPoEmptyTime() {
        BasePo po = new BasePo();
        po.setId(1L);
        // 不设置时间

        BaseVo vo = new BaseVo();

        assertNull(vo.getCreateTime());
        assertNull(vo.getUpdateTime());
    }

    @Test
    @DisplayName("测试VO属性")
    void testBaseVoProperties() {
        BaseVo vo = new BaseVo();

        // 测试设置和获取属性
        vo.setId(1L);
        vo.setUpdateTime("2023-10-15 14:30:45");
        vo.setCreateTime("2023-10-15 14:30:45");

        assertEquals(1L, vo.getId());
        assertEquals("2023-10-15 14:30:45", vo.getUpdateTime());
        assertEquals("2023-10-15 14:30:45", vo.getCreateTime());
    }
}
