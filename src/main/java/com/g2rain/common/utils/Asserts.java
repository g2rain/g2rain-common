package com.g2rain.common.utils;

import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.exception.ErrorCode;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * <p>{@code Asserts} 是参数条件判断工具类，用于对参数进行条件检查，如果不满足判断条件则抛出 {@link BusinessException}。</p>
 * <p>
 * 该类提供了一系列静态方法，用于进行常见的参数验证，包括：
 * <ul>
 *     <li>对象非空检查（{@code notNull}）</li>
 *     <li>条件真值检查（{@code isTrue}）</li>
 *     <li>字符串非空/非空白检查（{@code notEmpty}, {@code notBlank}）</li>
 *     <li>集合/Map/数组非空检查（{@code notEmpty}）</li>
 *     <li>数值比较检查（{@code greaterThan}, {@code lessThan}, {@code equals} 等）</li>
 *     <li>数值范围检查（{@code inRange}）</li>
 *     <li>正负数检查（{@code positive}, {@code negative}, {@code nonNegative} 等）</li>
 * </ul>
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * // 检查对象不为null
 * Asserts.notNull(user, MyErrorCode.USER_NOT_FOUND, "userId", userId);
 *
 * // 检查数值大于0
 * Asserts.greaterThan(amount, 0, MyErrorCode.INVALID_AMOUNT, "amount", amount);
 *
 * // 检查数值在范围内
 * Asserts.inRange(age, 18, 100, MyErrorCode.INVALID_AGE_RANGE, "age", age);
 * }</pre>
 *
 * @author jagger
 * @since 2025/10/5
 */
public final class Asserts {

    /**
     * 私有构造函数，防止实例化。
     */
    private Asserts() {
    }

    // ==================== 对象检查 ====================

    /**
     * <p>断言对象不为 {@code null}，否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @throws BusinessException 如果对象为 {@code null}
     */
    public static void notNull(Object obj, ErrorCode errorCode) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言对象不为 {@code null}，否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果对象为 {@code null}
     */
    public static void notNull(Object obj, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言对象不为 {@code null}，否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果对象为 {@code null}
     */
    public static void notNull(Object obj, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 条件检查 ====================

    /**
     * <p>断言条件为 {@code true}，否则抛出 {@link BusinessException}。</p>
     *
     * @param condition 待检查条件
     * @param errorCode 错误码
     * @throws BusinessException 如果条件为 {@code false}
     */
    public static void isTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言条件为 {@code true}，否则抛出 {@link BusinessException}。</p>
     *
     * @param condition 待检查条件
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果条件为 {@code false}
     */
    public static void isTrue(boolean condition, ErrorCode errorCode, Object... args) {
        if (!condition) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言条件为 {@code true}，否则抛出 {@link BusinessException}。</p>
     *
     * @param condition 待检查条件
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果条件为 {@code false}
     */
    public static void isTrue(boolean condition, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!condition) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 字符串检查 ====================

    /**
     * <p>断言字符串不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @throws BusinessException 如果字符串为空
     */
    public static void notEmpty(String str, ErrorCode errorCode) {
        if (Strings.isEmpty(str)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言字符串不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果字符串为空
     */
    public static void notEmpty(String str, ErrorCode errorCode, Object... args) {
        if (Strings.isEmpty(str)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言字符串不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果字符串为空
     */
    public static void notEmpty(String str, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Strings.isEmpty(str)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言字符串不为空白（不为 {@code null} 且包含非空白字符），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @throws BusinessException 如果字符串为空白
     */
    public static void notBlank(String str, ErrorCode errorCode) {
        if (Strings.isBlank(str)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言字符串不为空白（不为 {@code null} 且包含非空白字符），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果字符串为空白
     */
    public static void notBlank(String str, ErrorCode errorCode, Object... args) {
        if (Strings.isBlank(str)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言字符串不为空白（不为 {@code null} 且包含非空白字符），否则抛出 {@link BusinessException}。</p>
     *
     * @param str       待检查字符串
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果字符串为空白
     */
    public static void notBlank(String str, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Strings.isBlank(str)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 集合检查 ====================

    /**
     * <p>断言集合不为空（不为 {@code null} 且包含至少一个元素），否则抛出 {@link BusinessException}。</p>
     *
     * @param collection 待检查集合
     * @param errorCode  错误码
     * @throws BusinessException 如果集合为空
     */
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        if (Collections.isEmpty(collection)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言集合不为空（不为 {@code null} 且包含至少一个元素），否则抛出 {@link BusinessException}。</p>
     *
     * @param collection 待检查集合
     * @param errorCode  错误码
     * @param args       索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果集合为空
     */
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, Object... args) {
        if (Collections.isEmpty(collection)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言集合不为空（不为 {@code null} 且包含至少一个元素），否则抛出 {@link BusinessException}。</p>
     *
     * @param collection 待检查集合
     * @param errorCode  错误码
     * @param keyArgs    键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果集合为空
     */
    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Collections.isEmpty(collection)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言 Map 不为空（不为 {@code null} 且包含至少一个键值对），否则抛出 {@link BusinessException}。</p>
     *
     * @param map       待检查 Map
     * @param errorCode 错误码
     * @throws BusinessException 如果 Map 为空
     */
    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        if (Collections.isEmpty(map)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言 Map 不为空（不为 {@code null} 且包含至少一个键值对），否则抛出 {@link BusinessException}。</p>
     *
     * @param map       待检查 Map
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 Map 为空
     */
    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode, Object... args) {
        if (Collections.isEmpty(map)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言 Map 不为空（不为 {@code null} 且包含至少一个键值对），否则抛出 {@link BusinessException}。</p>
     *
     * @param map       待检查 Map
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 Map 为空
     */
    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Collections.isEmpty(map)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数组不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param array     待检查数组
     * @param errorCode 错误码
     * @throws BusinessException 如果数组为空
     */
    public static void notEmpty(Object[] array, ErrorCode errorCode) {
        if (Collections.isEmpty(array)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数组不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param array     待检查数组
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数组为空
     */
    public static void notEmpty(Object[] array, ErrorCode errorCode, Object... args) {
        if (Collections.isEmpty(array)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数组不为空（不为 {@code null} 且长度大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param array     待检查数组
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数组为空
     */
    public static void notEmpty(Object[] array, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Collections.isEmpty(array)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言对象不为空（支持 {@code Collection}、{@code Map}、数组以及普通对象的判空），否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @throws BusinessException 如果对象为空
     */
    public static void notEmpty(Object obj, ErrorCode errorCode) {
        if (Collections.isEmpty(obj)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言对象不为空（支持 {@code Collection}、{@code Map}、数组以及普通对象的判空），否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果对象为空
     */
    public static void notEmpty(Object obj, ErrorCode errorCode, Object... args) {
        if (Collections.isEmpty(obj)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言对象不为空（支持 {@code Collection}、{@code Map}、数组以及普通对象的判空），否则抛出 {@link BusinessException}。</p>
     *
     * @param obj       待检查对象
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果对象为空
     */
    public static void notEmpty(Object obj, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Collections.isEmpty(obj)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 数值比较检查 ====================

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(int actual, int expected, ErrorCode errorCode) {
        if (actual <= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(int actual, int expected, ErrorCode errorCode, Object... args) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(int actual, int expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(long actual, long expected, ErrorCode errorCode) {
        if (actual <= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(long actual, long expected, ErrorCode errorCode, Object... args) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(long actual, long expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(double actual, double expected, ErrorCode errorCode) {
        if (actual <= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(double actual, double expected, ErrorCode errorCode, Object... args) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(double actual, double expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual <= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode) {
        if (!Decimals.greaterThan(actual, expected)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Object... args) {
        if (!Decimals.greaterThan(actual, expected)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual <= expected}
     */
    public static void greaterThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!Decimals.greaterThan(actual, expected)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(int actual, int expected, ErrorCode errorCode) {
        if (actual < expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(int actual, int expected, ErrorCode errorCode, Object... args) {
        if (actual < expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(int actual, int expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual < expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(long actual, long expected, ErrorCode errorCode) {
        if (actual < expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(long actual, long expected, ErrorCode errorCode, Object... args) {
        if (actual < expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(long actual, long expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual < expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(double actual, double expected, ErrorCode errorCode) {
        if (actual < expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(double actual, double expected, ErrorCode errorCode, Object... args) {
        if (actual < expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(double actual, double expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual < expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode) {
        if (Decimals.compare(actual, expected) < 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Object... args) {
        if (Decimals.compare(actual, expected) < 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值大于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 {@code actual < expected}
     */
    public static void greaterThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Decimals.compare(actual, expected) < 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(int actual, int expected, ErrorCode errorCode) {
        if (actual >= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(int actual, int expected, ErrorCode errorCode, Object... args) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(int actual, int expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(long actual, long expected, ErrorCode errorCode) {
        if (actual >= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(long actual, long expected, ErrorCode errorCode, Object... args) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(long actual, long expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(double actual, double expected, ErrorCode errorCode) {
        if (actual >= expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(double actual, double expected, ErrorCode errorCode, Object... args) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(double actual, double expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual >= expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode) {
        if (!Decimals.lessThan(actual, expected)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Object... args) {
        if (!Decimals.lessThan(actual, expected)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual >= expected
     */
    public static void lessThan(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!Decimals.lessThan(actual, expected)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(int actual, int expected, ErrorCode errorCode) {
        if (actual > expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(int actual, int expected, ErrorCode errorCode, Object... args) {
        if (actual > expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(int actual, int expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual > expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(long actual, long expected, ErrorCode errorCode) {
        if (actual > expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(long actual, long expected, ErrorCode errorCode, Object... args) {
        if (actual > expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(long actual, long expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual > expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(double actual, double expected, ErrorCode errorCode) {
        if (actual > expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(double actual, double expected, ErrorCode errorCode, Object... args) {
        if (actual > expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(double actual, double expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual > expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode) {
        if (Decimals.compare(actual, expected) > 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Object... args) {
        if (Decimals.compare(actual, expected) > 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言第一个数值小于等于第二个数值，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual > expected
     */
    public static void lessThanOrEqual(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Decimals.compare(actual, expected) > 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(int actual, int expected, ErrorCode errorCode) {
        if (actual != expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(int actual, int expected, ErrorCode errorCode, Object... args) {
        if (actual != expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(int actual, int expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual != expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(long actual, long expected, ErrorCode errorCode) {
        if (actual != expected) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(long actual, long expected, ErrorCode errorCode, Object... args) {
        if (actual != expected) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(long actual, long expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (actual != expected) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(double actual, double expected, ErrorCode errorCode) {
        if (Double.compare(actual, expected) != 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(double actual, double expected, ErrorCode errorCode, Object... args) {
        if (Double.compare(actual, expected) != 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(double actual, double expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Double.compare(actual, expected) != 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(BigDecimal actual, BigDecimal expected, ErrorCode errorCode) {
        if (!Decimals.equals(actual, expected)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Object... args) {
        if (!Decimals.equals(actual, expected)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言两个数值相等，否则抛出 {@link BusinessException}。</p>
     *
     * @param actual    待检查的数值
     * @param expected  期望的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果 actual != expected
     */
    public static void equals(BigDecimal actual, BigDecimal expected, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!Decimals.equals(actual, expected)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 数值范围检查 ====================

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(int value, int min, int max, ErrorCode errorCode) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(int value, int min, int max, ErrorCode errorCode, Object... args) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(int value, int min, int max, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(long value, long min, long max, ErrorCode errorCode) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(long value, long min, long max, ErrorCode errorCode, Object... args) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(long value, long min, long max, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(double value, double min, double max, ErrorCode errorCode) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(double value, double min, double max, ErrorCode errorCode, Object... args) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(double value, double min, double max, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < min || value > max) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(BigDecimal value, BigDecimal min, BigDecimal max, ErrorCode errorCode) {
        if (Decimals.compare(value, min) < 0 || Decimals.compare(value, max) > 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(BigDecimal value, BigDecimal min, BigDecimal max, ErrorCode errorCode, Object... args) {
        if (Decimals.compare(value, min) < 0 || Decimals.compare(value, max) > 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值在指定范围内（包含边界），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param min       最小值（包含）
     * @param max       最大值（包含）
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不在范围内
     */
    public static void inRange(BigDecimal value, BigDecimal min, BigDecimal max, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Decimals.compare(value, min) < 0 || Decimals.compare(value, max) > 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    // ==================== 正负数检查 ====================

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(int value, ErrorCode errorCode) {
        if (value <= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(int value, ErrorCode errorCode, Object... args) {
        if (value <= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(int value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value <= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(long value, ErrorCode errorCode) {
        if (value <= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(long value, ErrorCode errorCode, Object... args) {
        if (value <= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(long value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value <= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(double value, ErrorCode errorCode) {
        if (value <= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(double value, ErrorCode errorCode, Object... args) {
        if (value <= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(double value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value <= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(BigDecimal value, ErrorCode errorCode) {
        if (!Decimals.isPositive(value)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(BigDecimal value, ErrorCode errorCode, Object... args) {
        if (!Decimals.isPositive(value)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为正数（大于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为正数
     */
    public static void positive(BigDecimal value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!Decimals.isPositive(value)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(int value, ErrorCode errorCode) {
        if (value >= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(int value, ErrorCode errorCode, Object... args) {
        if (value >= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(int value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value >= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(long value, ErrorCode errorCode) {
        if (value >= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(long value, ErrorCode errorCode, Object... args) {
        if (value >= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(long value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value >= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(double value, ErrorCode errorCode) {
        if (value >= 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(double value, ErrorCode errorCode, Object... args) {
        if (value >= 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(double value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value >= 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(BigDecimal value, ErrorCode errorCode) {
        if (!Decimals.isNegative(value)) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(BigDecimal value, ErrorCode errorCode, Object... args) {
        if (!Decimals.isNegative(value)) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为负数（小于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值不为负数
     */
    public static void negative(BigDecimal value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (!Decimals.isNegative(value)) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(int value, ErrorCode errorCode) {
        if (value < 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(int value, ErrorCode errorCode, Object... args) {
        if (value < 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(int value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(long value, ErrorCode errorCode) {
        if (value < 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(long value, ErrorCode errorCode, Object... args) {
        if (value < 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(long value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(double value, ErrorCode errorCode) {
        if (value < 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(double value, ErrorCode errorCode, Object... args) {
        if (value < 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(double value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (value < 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(BigDecimal value, ErrorCode errorCode) {
        if (Decimals.compare(value, BigDecimal.ZERO) < 0) {
            throw new BusinessException(errorCode);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param args      索引参数数组，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(BigDecimal value, ErrorCode errorCode, Object... args) {
        if (Decimals.compare(value, BigDecimal.ZERO) < 0) {
            throw new BusinessException(errorCode, args);
        }
    }

    /**
     * <p>断言数值为非负数（大于等于 0），否则抛出 {@link BusinessException}。</p>
     *
     * @param value     待检查的数值
     * @param errorCode 错误码
     * @param keyArgs   键值对参数映射，用于填充错误消息模板
     * @throws BusinessException 如果数值为负数
     */
    public static void nonNegative(BigDecimal value, ErrorCode errorCode, Map<String, Object> keyArgs) {
        if (Decimals.compare(value, BigDecimal.ZERO) < 0) {
            throw new BusinessException(errorCode, keyArgs);
        }
    }
}
