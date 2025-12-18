package com.g2rain.common.utils;

import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("断言工具类测试")
class AssertsTest {

    private enum TestErrorCode implements ErrorCode {
        NULL_VALUE("test.1001", "Value cannot be null"),
        EMPTY_VALUE("test.1002", "Value cannot be empty"),
        INVALID_CONDITION("test.1003", "Condition is invalid"),
        INVALID_RANGE("test.1004", "Value out of range: {0:min} - {1:max}"),
        INVALID_COMPARISON("test.1005", "Invalid comparison: actual={0:actual}, expected={1:expected}"),
        POSITIVE_REQUIRED("test.1006", "Value must be positive"),
        NEGATIVE_REQUIRED("test.1007", "Value must be negative"),
        NON_NEGATIVE_REQUIRED("test.1008", "Value must be non-negative");

        private final String code;
        private final String messageTemplate;

        TestErrorCode(String code, String messageTemplate) {
            this.code = code;
            this.messageTemplate = messageTemplate;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String messageTemplate() {
            return messageTemplate;
        }
    }

    // ==================== 对象检查测试 ====================

    @Test
    @DisplayName("测试notNull-正常情况")
    void testNotNullSuccess() {
        assertDoesNotThrow(() -> Asserts.notNull("test", TestErrorCode.NULL_VALUE));
        assertDoesNotThrow(() -> Asserts.notNull(new Object(), TestErrorCode.NULL_VALUE));
    }

    @Test
    @DisplayName("测试notNull-抛出异常")
    void testNotNullThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notNull(null, TestErrorCode.NULL_VALUE));
        assertEquals("test.1001", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试notNull-带索引参数")
    void testNotNullWithIndexArgs() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notNull(null, TestErrorCode.NULL_VALUE, "arg1", "arg2"));
        assertEquals("test.1001", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试notNull-带键值对参数")
    void testNotNullWithKeyArgs() {
        Map<String, Object> keyArgs = Map.of("field", "username");
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notNull(null, TestErrorCode.NULL_VALUE, keyArgs));
        assertEquals("test.1001", exception.getErrorCode());
    }

    // ==================== 条件检查测试 ====================

    @Test
    @DisplayName("测试isTrue-正常情况")
    void testIsTrueSuccess() {
        assertDoesNotThrow(() -> Asserts.isTrue(true, TestErrorCode.INVALID_CONDITION));
    }

    @Test
    @DisplayName("测试isTrue-抛出异常")
    void testIsTrueThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.isTrue(false, TestErrorCode.INVALID_CONDITION));
        assertEquals("test.1003", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试isTrue-带索引参数")
    void testIsTrueWithIndexArgs() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.isTrue(false, TestErrorCode.INVALID_CONDITION, "arg1"));
        assertEquals("test.1003", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试isTrue-带键值对参数")
    void testIsTrueWithKeyArgs() {
        Map<String, Object> keyArgs = Map.of("condition", "age >= 18");
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.isTrue(false, TestErrorCode.INVALID_CONDITION, keyArgs));
        assertEquals("test.1003", exception.getErrorCode());
    }

    // ==================== 字符串检查测试 ====================

    @Test
    @DisplayName("测试notEmpty-正常情况")
    void testNotEmptyStringSuccess() {
        assertDoesNotThrow(() -> Asserts.notEmpty("test", TestErrorCode.EMPTY_VALUE));
        assertDoesNotThrow(() -> Asserts.notEmpty("  ", TestErrorCode.EMPTY_VALUE));
    }

    @Test
    @DisplayName("测试notEmpty-抛出异常")
    void testNotEmptyStringThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty("", TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty((String) null, TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试notBlank-正常情况")
    void testNotBlankSuccess() {
        assertDoesNotThrow(() -> Asserts.notBlank("test", TestErrorCode.EMPTY_VALUE));
        assertDoesNotThrow(() -> Asserts.notBlank("  test  ", TestErrorCode.EMPTY_VALUE));
    }

    @Test
    @DisplayName("测试notBlank-抛出异常")
    void testNotBlankThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notBlank("", TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notBlank("   ", TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notBlank(null, TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());
    }

    // ==================== 集合检查测试 ====================

    @Test
    @DisplayName("测试notEmpty集合-正常情况")
    void testNotEmptyCollectionSuccess() {
        assertDoesNotThrow(() -> Asserts.notEmpty(java.util.Collections.singletonList("item"), TestErrorCode.EMPTY_VALUE));
    }

    @Test
    @DisplayName("测试notEmpty集合-抛出异常")
    void testNotEmptyCollectionThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty((Collection<?>) null, TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty(java.util.Collections.emptyList(), TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试notEmpty Map-正常情况")
    void testNotEmptyMapSuccess() {
        assertDoesNotThrow(() -> Asserts.notEmpty(java.util.Collections.singletonMap("key", "value"), TestErrorCode.EMPTY_VALUE));
    }

    @Test
    @DisplayName("测试notEmpty Map-抛出异常")
    void testNotEmptyMapThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty((Map<?, ?>) null, TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty(java.util.Collections.emptyMap(), TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试notEmpty数组-正常情况")
    void testNotEmptyArraySuccess() {
        assertDoesNotThrow(() -> Asserts.notEmpty(new String[]{"item"}, TestErrorCode.EMPTY_VALUE));
    }

    @Test
    @DisplayName("测试notEmpty数组-抛出异常")
    void testNotEmptyArrayThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty((Object[]) null, TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.notEmpty(new Object[0], TestErrorCode.EMPTY_VALUE));
        assertEquals("test.1002", exception.getErrorCode());
    }

    // ==================== 数值比较测试 ====================

    @Test
    @DisplayName("测试greaterThan-int-正常情况")
    void testGreaterThanIntSuccess() {
        assertDoesNotThrow(() -> Asserts.greaterThan(10, 5, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试greaterThan-int-抛出异常")
    void testGreaterThanIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.greaterThan(5, 10, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.greaterThan(5, 5, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试greaterThan-long-正常情况")
    void testGreaterThanLongSuccess() {
        assertDoesNotThrow(() -> Asserts.greaterThan(10L, 5L, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试greaterThan-double-正常情况")
    void testGreaterThanDoubleSuccess() {
        assertDoesNotThrow(() -> Asserts.greaterThan(10.5, 5.2, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试greaterThan-BigDecimal-正常情况")
    void testGreaterThanBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.greaterThan(BigDecimal.valueOf(10.5), BigDecimal.valueOf(5.2), TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试greaterThanOrEqual-int-正常情况")
    void testGreaterThanOrEqualIntSuccess() {
        assertDoesNotThrow(() -> Asserts.greaterThanOrEqual(10, 5, TestErrorCode.INVALID_COMPARISON));
        assertDoesNotThrow(() -> Asserts.greaterThanOrEqual(5, 5, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试greaterThanOrEqual-int-抛出异常")
    void testGreaterThanOrEqualIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.greaterThanOrEqual(5, 10, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试lessThan-int-正常情况")
    void testLessThanIntSuccess() {
        assertDoesNotThrow(() -> Asserts.lessThan(5, 10, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试lessThan-int-抛出异常")
    void testLessThanIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.lessThan(10, 5, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.lessThan(5, 5, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试lessThanOrEqual-int-正常情况")
    void testLessThanOrEqualIntSuccess() {
        assertDoesNotThrow(() -> Asserts.lessThanOrEqual(5, 10, TestErrorCode.INVALID_COMPARISON));
        assertDoesNotThrow(() -> Asserts.lessThanOrEqual(5, 5, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试lessThanOrEqual-int-抛出异常")
    void testLessThanOrEqualIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.lessThanOrEqual(10, 5, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试equals-int-正常情况")
    void testEqualsIntSuccess() {
        assertDoesNotThrow(() -> Asserts.equals(5, 5, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试equals-int-抛出异常")
    void testEqualsIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.equals(5, 10, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试equals-double-正常情况")
    void testEqualsDoubleSuccess() {
        assertDoesNotThrow(() -> Asserts.equals(5.5, 5.5, TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试equals-double-抛出异常")
    void testEqualsDoubleThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.equals(5.5, 5.6, TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试equals-BigDecimal-正常情况")
    void testEqualsBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.equals(BigDecimal.valueOf(5.5), BigDecimal.valueOf(5.5), TestErrorCode.INVALID_COMPARISON));
    }

    @Test
    @DisplayName("测试equals-BigDecimal-抛出异常")
    void testEqualsBigDecimalThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.equals(BigDecimal.valueOf(5.5), BigDecimal.valueOf(5.6), TestErrorCode.INVALID_COMPARISON));
        assertEquals("test.1005", exception.getErrorCode());
    }

    // ==================== 数值范围测试 ====================

    @Test
    @DisplayName("测试inRange-int-正常情况")
    void testInRangeIntSuccess() {
        assertDoesNotThrow(() -> Asserts.inRange(5, 1, 10, TestErrorCode.INVALID_RANGE));
        assertDoesNotThrow(() -> Asserts.inRange(1, 1, 10, TestErrorCode.INVALID_RANGE));
        assertDoesNotThrow(() -> Asserts.inRange(10, 1, 10, TestErrorCode.INVALID_RANGE));
    }

    @Test
    @DisplayName("测试inRange-int-抛出异常")
    void testInRangeIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.inRange(0, 1, 10, TestErrorCode.INVALID_RANGE));
        assertEquals("test.1004", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.inRange(11, 1, 10, TestErrorCode.INVALID_RANGE));
        assertEquals("test.1004", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试inRange-long-正常情况")
    void testInRangeLongSuccess() {
        assertDoesNotThrow(() -> Asserts.inRange(5L, 1L, 10L, TestErrorCode.INVALID_RANGE));
    }

    @Test
    @DisplayName("测试inRange-double-正常情况")
    void testInRangeDoubleSuccess() {
        assertDoesNotThrow(() -> Asserts.inRange(5.5, 1.0, 10.0, TestErrorCode.INVALID_RANGE));
    }

    @Test
    @DisplayName("测试inRange-BigDecimal-正常情况")
    void testInRangeBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.inRange(BigDecimal.valueOf(5.5), BigDecimal.valueOf(1.0), BigDecimal.valueOf(10.0), TestErrorCode.INVALID_RANGE));
    }

    @Test
    @DisplayName("测试inRange-带索引参数")
    void testInRangeWithIndexArgs() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.inRange(0, 1, 10, TestErrorCode.INVALID_RANGE, "1", "10"));
        assertEquals("test.1004", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试inRange-带键值对参数")
    void testInRangeWithKeyArgs() {
        Map<String, Object> keyArgs = Map.of("min", 1, "max", 10);
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.inRange(0, 1, 10, TestErrorCode.INVALID_RANGE, keyArgs));
        assertEquals("test.1004", exception.getErrorCode());
    }

    // ==================== 正负数检查测试 ====================

    @Test
    @DisplayName("测试positive-int-正常情况")
    void testPositiveIntSuccess() {
        assertDoesNotThrow(() -> Asserts.positive(10, TestErrorCode.POSITIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.positive(1, TestErrorCode.POSITIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试positive-int-抛出异常")
    void testPositiveIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.positive(0, TestErrorCode.POSITIVE_REQUIRED));
        assertEquals("test.1006", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.positive(-10, TestErrorCode.POSITIVE_REQUIRED));
        assertEquals("test.1006", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试positive-long-正常情况")
    void testPositiveLongSuccess() {
        assertDoesNotThrow(() -> Asserts.positive(10L, TestErrorCode.POSITIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试positive-double-正常情况")
    void testPositiveDoubleSuccess() {
        assertDoesNotThrow(() -> Asserts.positive(10.5, TestErrorCode.POSITIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试positive-BigDecimal-正常情况")
    void testPositiveBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.positive(BigDecimal.valueOf(10.5), TestErrorCode.POSITIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试negative-int-正常情况")
    void testNegativeIntSuccess() {
        assertDoesNotThrow(() -> Asserts.negative(-10, TestErrorCode.NEGATIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.negative(-1, TestErrorCode.NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试negative-int-抛出异常")
    void testNegativeIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.negative(0, TestErrorCode.NEGATIVE_REQUIRED));
        assertEquals("test.1007", exception.getErrorCode());

        exception = assertThrows(BusinessException.class,
            () -> Asserts.negative(10, TestErrorCode.NEGATIVE_REQUIRED));
        assertEquals("test.1007", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试negative-BigDecimal-正常情况")
    void testNegativeBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.negative(BigDecimal.valueOf(-10.5), TestErrorCode.NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试nonNegative-int-正常情况")
    void testNonNegativeIntSuccess() {
        assertDoesNotThrow(() -> Asserts.nonNegative(0, TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.nonNegative(10, TestErrorCode.NON_NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试nonNegative-int-抛出异常")
    void testNonNegativeIntThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.nonNegative(-10, TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertEquals("test.1008", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试nonNegative-long-正常情况")
    void testNonNegativeLongSuccess() {
        assertDoesNotThrow(() -> Asserts.nonNegative(0L, TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.nonNegative(10L, TestErrorCode.NON_NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试nonNegative-double-正常情况")
    void testNonNegativeDoubleSuccess() {
        assertDoesNotThrow(() -> Asserts.nonNegative(0.0, TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.nonNegative(10.5, TestErrorCode.NON_NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试nonNegative-BigDecimal-正常情况")
    void testNonNegativeBigDecimalSuccess() {
        assertDoesNotThrow(() -> Asserts.nonNegative(BigDecimal.ZERO, TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertDoesNotThrow(() -> Asserts.nonNegative(BigDecimal.valueOf(10.5), TestErrorCode.NON_NEGATIVE_REQUIRED));
    }

    @Test
    @DisplayName("测试nonNegative-BigDecimal-抛出异常")
    void testNonNegativeBigDecimalThrows() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.nonNegative(BigDecimal.valueOf(-10.5), TestErrorCode.NON_NEGATIVE_REQUIRED));
        assertEquals("test.1008", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试nonNegative-带索引参数")
    void testNonNegativeWithIndexArgs() {
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.nonNegative(-10, TestErrorCode.NON_NEGATIVE_REQUIRED, "value", -10));
        assertEquals("test.1008", exception.getErrorCode());
    }

    @Test
    @DisplayName("测试nonNegative-带键值对参数")
    void testNonNegativeWithKeyArgs() {
        Map<String, Object> keyArgs = Map.of("field", "amount", "value", -10);
        BusinessException exception = assertThrows(BusinessException.class,
            () -> Asserts.nonNegative(-10, TestErrorCode.NON_NEGATIVE_REQUIRED, keyArgs));
        assertEquals("test.1008", exception.getErrorCode());
    }

    // ==================== 综合测试 ====================

    @Test
    @DisplayName("测试多个断言链式调用")
    void testMultipleAsserts() {
        String username = "testuser";
        int age = 25;
        BigDecimal amount = BigDecimal.valueOf(100.50);

        assertDoesNotThrow(() -> {
            Asserts.notNull(username, TestErrorCode.NULL_VALUE);
            Asserts.notBlank(username, TestErrorCode.EMPTY_VALUE);
            Asserts.positive(age, TestErrorCode.POSITIVE_REQUIRED);
            Asserts.inRange(age, 18, 100, TestErrorCode.INVALID_RANGE);
            Asserts.positive(amount, TestErrorCode.POSITIVE_REQUIRED);
            Asserts.greaterThan(amount, BigDecimal.ZERO, TestErrorCode.INVALID_COMPARISON);
        });
    }

    @Test
    @DisplayName("测试边界值情况")
    void testBoundaryValues() {
        // 测试边界值
        assertDoesNotThrow(() -> Asserts.inRange(0, 0, 10, TestErrorCode.INVALID_RANGE));
        assertDoesNotThrow(() -> Asserts.inRange(10, 0, 10, TestErrorCode.INVALID_RANGE));

        // 测试边界外值
        BusinessException exception1 = assertThrows(BusinessException.class,
            () -> Asserts.inRange(-1, 0, 10, TestErrorCode.INVALID_RANGE));
        assertEquals("test.1004", exception1.getErrorCode());

        BusinessException exception2 = assertThrows(BusinessException.class,
            () -> Asserts.inRange(11, 0, 10, TestErrorCode.INVALID_RANGE));
        assertEquals("test.1004", exception2.getErrorCode());
    }
}
