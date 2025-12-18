package com.g2rain.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("集合工具类测试")
class CollectionsTest {

    @Test
    @DisplayName("测试集合是否为空")
    void testIsEmpty() {
        // 测试null集合
        assertTrue(Collections.isEmpty((Collection<?>) null));

        // 测试空集合
        assertTrue(Collections.isEmpty(java.util.Collections.emptyList()));

        // 测试非空集合
        assertFalse(Collections.isEmpty(java.util.Collections.singletonList("item")));
    }

    @Test
    @DisplayName("测试数组是否为空")
    void testIsEmptyArray() {
        // 测试null数组
        assertTrue(Collections.isEmpty((Object[]) null));

        // 测试空数组
        assertTrue(Collections.isEmpty(new Object[0]));

        // 测试非空数组
        assertFalse(Collections.isEmpty(new Object[]{"item"}));
    }

    @Test
    @DisplayName("测试字节数组是否为空")
    void testIsEmptyByteArray() {
        // 测试null字节数组
        assertTrue(Collections.isEmpty((byte[]) null));

        // 测试空字节数组
        assertTrue(Collections.isEmpty(new byte[0]));

        // 测试非空字节数组
        assertFalse(Collections.isEmpty(new byte[]{1}));
    }

    @Test
    @DisplayName("测试映射是否为空")
    void testIsEmptyMap() {
        // 测试null映射
        assertTrue(Collections.isEmpty((Map<?, ?>) null));

        // 测试空映射
        assertTrue(Collections.isEmpty(java.util.Collections.emptyMap()));

        // 测试非空映射
        assertFalse(Collections.isEmpty(java.util.Collections.singletonMap("key", "value")));
    }

    @Test
    @DisplayName("测试集合非空判断")
    void testIsNotEmpty() {
        // 测试null集合
        assertFalse(Collections.isNotEmpty((Collection<?>) null));

        // 测试空集合
        assertFalse(Collections.isNotEmpty(java.util.Collections.emptyList()));

        // 测试非空集合
        assertTrue(Collections.isNotEmpty(java.util.Collections.singletonList("item")));
    }
}
