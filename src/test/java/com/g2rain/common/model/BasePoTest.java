package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("基础PO测试")
class BasePoTest {

    @Test
    @DisplayName("测试基础PO属性")
    void testBasePoProperties() {
        BasePo po = new BasePo();

        // 测试默认值
        assertNull(po.getId());
        assertNull(po.getUpdateTime());
        assertNull(po.getCreateTime());
        assertNull(po.getVersion());

        // 测试设置和获取属性
        po.setId(1L);
        LocalDateTime now = LocalDateTime.now();
        po.setUpdateTime(now);
        po.setCreateTime(now);
        po.setVersion(1);

        assertEquals(1L, po.getId());
        assertEquals(now, po.getUpdateTime());
        assertEquals(now, po.getCreateTime());
        assertEquals(1, po.getVersion());
    }
}
