package com.g2rain.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("字符串工具类测试")
class StringsTest {

    @Test
    @DisplayName("测试字符串是否为空")
    void testIsBlank() {
        // 测试null字符串
        assertTrue(Strings.isBlank(null));

        // 测试空字符串
        assertTrue(Strings.isBlank(""));

        // 测试只有空格的字符串
        assertTrue(Strings.isBlank(" "));
        assertTrue(Strings.isBlank("   "));

        // 测试非空字符串
        assertFalse(Strings.isBlank("a"));
        assertFalse(Strings.isBlank(" a "));
    }

    @Test
    @DisplayName("测试字符串非空判断")
    void testIsNotBlank() {
        // 测试null字符串
        assertFalse(Strings.isNotBlank(null));

        // 测试空字符串
        assertFalse(Strings.isNotBlank(""));

        // 测试只有空格的字符串
        assertFalse(Strings.isNotBlank(" "));
        assertFalse(Strings.isNotBlank("   "));

        // 测试非空字符串
        assertTrue(Strings.isNotBlank("a"));
        assertTrue(Strings.isNotBlank(" a "));
    }

    @Test
    @DisplayName("测试字符串是否为空对象")
    void testIsEmpty() {
        // 测试null字符串
        assertTrue(Strings.isEmpty(null));

        // 测试空字符串
        assertTrue(Strings.isEmpty(""));

        // 测试只有空格的字符串
        assertFalse(Strings.isEmpty(" "));
        assertFalse(Strings.isEmpty("   "));

        // 测试非空字符串
        assertFalse(Strings.isEmpty("a"));
        assertFalse(Strings.isEmpty(" a "));
    }
}
