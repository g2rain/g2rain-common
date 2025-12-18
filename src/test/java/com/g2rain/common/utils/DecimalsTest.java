package com.g2rain.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("数值工具类测试")
class DecimalsTest {

    @Test
    @DisplayName("测试创建BigDecimal对象")
    void testFrom() {
        // 测试字符串创建
        assertEquals(new BigDecimal("123.45"), Decimals.from("123.45"));
        assertEquals(BigDecimal.ZERO, Decimals.from(""));
        assertEquals(BigDecimal.ZERO, Decimals.from((String) null));

        // 测试double创建
        assertEquals(BigDecimal.valueOf(123.45), Decimals.from(123.45));
    }

    @Test
    @DisplayName("测试零值")
    void testZero() {
        assertEquals(BigDecimal.ZERO, Decimals.zero());
    }

    @Test
    @DisplayName("测试加法运算")
    void testAdd() {
        assertEquals(new BigDecimal("3"), Decimals.add(BigDecimal.ONE, BigDecimal.valueOf(2)));
        assertEquals(BigDecimal.ONE, Decimals.add(null, BigDecimal.ONE));
        assertEquals(BigDecimal.ONE, Decimals.add(BigDecimal.ONE, null));
        assertEquals(BigDecimal.ZERO, Decimals.add(null, null));
    }

    @Test
    @DisplayName("测试减法运算")
    void testSubtract() {
        assertEquals(BigDecimal.ONE, Decimals.subtract(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
        assertEquals(new BigDecimal("-1"), Decimals.subtract(null, BigDecimal.ONE));
        assertEquals(BigDecimal.ONE, Decimals.subtract(BigDecimal.ONE, null));
        assertEquals(BigDecimal.ZERO, Decimals.subtract(null, null));
    }

    @Test
    @DisplayName("测试乘法运算")
    void testMultiply() {
        assertEquals(new BigDecimal("6"), Decimals.multiply(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
        assertEquals(BigDecimal.ZERO, Decimals.multiply(null, BigDecimal.ONE));
        assertEquals(BigDecimal.ZERO, Decimals.multiply(BigDecimal.ONE, null));
        assertEquals(BigDecimal.ZERO, Decimals.multiply(null, null));
    }

    @Test
    @DisplayName("测试除法运算")
    void testDivide() {
        // 测试带参数的除法
        assertEquals(new BigDecimal("2"), Decimals.divide(BigDecimal.valueOf(6), BigDecimal.valueOf(3), 0, RoundingMode.DOWN));
        assertEquals(BigDecimal.ZERO, Decimals.divide(null, BigDecimal.ONE, 0, RoundingMode.DOWN));
        assertEquals(BigDecimal.ZERO, Decimals.divide(BigDecimal.ONE, null, 0, RoundingMode.DOWN));
        assertEquals(BigDecimal.ZERO, Decimals.divide(BigDecimal.ONE, BigDecimal.ZERO, 0, RoundingMode.DOWN));

        // 测试默认除法
        assertEquals(new BigDecimal("2.00"), Decimals.divide(BigDecimal.valueOf(6), BigDecimal.valueOf(3)));
        assertEquals(BigDecimal.ZERO, Decimals.divide(null, BigDecimal.ONE));
        assertEquals(BigDecimal.ZERO, Decimals.divide(BigDecimal.ONE, null));
        assertEquals(BigDecimal.ZERO, Decimals.divide(BigDecimal.ONE, BigDecimal.ZERO));
    }

    @Test
    @DisplayName("测试四舍五入")
    void testRound() {
        // 测试带模式的四舍五入
        assertEquals(new BigDecimal("2.13"), Decimals.round(BigDecimal.valueOf(2.125), 2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("0.00"), Decimals.round(null, 2, RoundingMode.HALF_UP));

        // 测试默认四舍五入
        assertEquals("2.13", Decimals.round(BigDecimal.valueOf(2.125), 2).toString());
        assertEquals(new BigDecimal("0.00"), Decimals.round(null, 2));
    }

    @Test
    @DisplayName("测试去除末尾零")
    void testStripTrailingZeros() {
        assertEquals(new BigDecimal("2.1"), Decimals.stripTrailingZeros(new BigDecimal("2.100")));
        assertEquals(BigDecimal.ZERO, Decimals.stripTrailingZeros(null));
    }

    @Test
    @DisplayName("测试转换为普通字符串")
    void testToPlainString() {
        assertEquals("2.100", Decimals.toPlainString(new BigDecimal("2.100")));
        assertEquals("0", Decimals.toPlainString(null));
    }

    @Test
    @DisplayName("测试安全转换为字符串")
    void testToStringSafe() {
        assertEquals("2.1", Decimals.toStringSafe(new BigDecimal("2.100")));
        assertEquals("0", Decimals.toStringSafe(null));
    }

    @Test
    @DisplayName("测试是否为零")
    void testIsZero() {
        assertTrue(Decimals.isZero(BigDecimal.ZERO));
        assertFalse(Decimals.isZero(BigDecimal.ONE));
        assertTrue(Decimals.isZero(null));
    }

    @Test
    @DisplayName("测试是否为正数")
    void testIsPositive() {
        assertTrue(Decimals.isPositive(BigDecimal.ONE));
        assertFalse(Decimals.isPositive(BigDecimal.ZERO));
        assertFalse(Decimals.isPositive(BigDecimal.valueOf(-1)));
        assertFalse(Decimals.isPositive(null));
    }

    @Test
    @DisplayName("测试是否为负数")
    void testIsNegative() {
        assertFalse(Decimals.isNegative(BigDecimal.ONE));
        assertFalse(Decimals.isNegative(BigDecimal.ZERO));
        assertTrue(Decimals.isNegative(BigDecimal.valueOf(-1)));
        assertFalse(Decimals.isNegative(null));
    }

    @Test
    @DisplayName("测试设置精度")
    void testSetScale() {
        assertEquals(new BigDecimal("2.13"), Decimals.setScale(new BigDecimal("2.125"), 2));
        assertEquals(BigDecimal.ZERO, Decimals.setScale(null, 2));
    }

    @Test
    @DisplayName("测试比较运算")
    void testComparison() {
        // 测试大于
        assertTrue(Decimals.greaterThan(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
        assertFalse(Decimals.greaterThan(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
        assertFalse(Decimals.greaterThan(null, BigDecimal.valueOf(3)));
        assertTrue(Decimals.greaterThan(BigDecimal.valueOf(3), null));
        assertFalse(Decimals.greaterThan(null, null));

        // 测试小于
        assertFalse(Decimals.lessThan(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
        assertTrue(Decimals.lessThan(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
        assertTrue(Decimals.lessThan(null, BigDecimal.valueOf(3)));
        assertFalse(Decimals.lessThan(BigDecimal.valueOf(3), null));
        assertFalse(Decimals.lessThan(null, null));

        // 测试等于
        assertTrue(Decimals.equals(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
        assertFalse(Decimals.equals(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
        assertFalse(Decimals.equals(null, BigDecimal.valueOf(3)));
        assertFalse(Decimals.equals(BigDecimal.valueOf(3), null));
        assertTrue(Decimals.equals(null, null));

        // 测试比较
        assertEquals(1, Decimals.compare(BigDecimal.valueOf(3), BigDecimal.valueOf(2)));
        assertEquals(-1, Decimals.compare(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
        assertEquals(0, Decimals.compare(BigDecimal.valueOf(2), BigDecimal.valueOf(2)));
        assertEquals(-1, Decimals.compare(null, BigDecimal.valueOf(3)));
        assertEquals(1, Decimals.compare(BigDecimal.valueOf(3), null));
        assertEquals(0, Decimals.compare(null, null));
    }
}
