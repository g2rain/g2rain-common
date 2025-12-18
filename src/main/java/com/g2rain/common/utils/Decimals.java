package com.g2rain.common.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * <p>工具类 {@code Decimals} 提供 {@link BigDecimal} 的安全构造、运算和比较方法，避免 {@code null} 值引发的异常。</p>
 * <p>支持数值构造、加减乘除、舍入、比较等操作，并提供常用数值判断方法。</p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * BigDecimal a = Decimals.from("10.5");
 * BigDecimal b = Decimals.from(2.0);
 * BigDecimal sum = Decimals.add(a, b);
 * BigDecimal rounded = Decimals.round(sum, 2);
 * boolean positive = Decimals.isPositive(sum);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class Decimals {

    /**
     * 私有构造，防止实例化。
     */
    private Decimals() {

    }

    /**
     * 从字符串构造 {@link BigDecimal}。
     *
     * @param val 数值字符串
     * @return {@link BigDecimal} 对象，输入为空返回 {@link BigDecimal#ZERO}
     */
    public static BigDecimal from(String val) {
        return Strings.isNotBlank(val) ? new BigDecimal(val) : zero();
    }

    /**
     * 从 double 构造 {@link BigDecimal}。
     *
     * @param val double 数值
     * @return {@link BigDecimal} 对象
     */
    public static BigDecimal from(double val) {
        return BigDecimal.valueOf(val);
    }

    /**
     * 获取 {@link BigDecimal} 零值。
     *
     * @return {@link BigDecimal#ZERO}
     */
    public static BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    /**
     * 加法运算，处理 {@code null} 值。
     *
     * @param augend 被加数
     * @param addend 加数
     * @return {@code augend + addend}，{@code null} 当作零处理
     */
    public static BigDecimal add(BigDecimal augend, BigDecimal addend) {
        return nvl(augend).add(nvl(addend));
    }

    /**
     * 减法运算，处理 {@code null} 值。
     *
     * @param minuend    被减数
     * @param subtrahend 减数
     * @return {@code minuend - subtrahend}，{@code null} 当作零处理
     */
    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
        return nvl(minuend).subtract(nvl(subtrahend));
    }

    /**
     * 乘法运算，处理 {@code null} 值。
     *
     * @param multiplicand 乘数
     * @param multiplier   被乘数
     * @return {@code multiplicand * multiplier}，{@code null} 当作零处理
     */
    public static BigDecimal multiply(BigDecimal multiplicand, BigDecimal multiplier) {
        return nvl(multiplicand).multiply(nvl(multiplier));
    }

    /**
     * 除法运算，处理 {@code null} 值或除数为零的情况。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    小数位精度
     * @param mode     舍入模式
     * @return {@code dividend / divisor}，{@code null} 或除数为零时返回零
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor, int scale, RoundingMode mode) {
        if (Objects.isNull(dividend) || Objects.isNull(divisor) || divisor.compareTo(BigDecimal.ZERO) == 0) {
            return zero();
        }
        return nvl(dividend).divide(nvl(divisor), scale, mode);
    }

    /**
     * 除法运算，默认精度为 2，小数舍入模式为 {@link RoundingMode#HALF_UP}。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return {@code dividend / divisor}，{@code null} 或除数为零时返回零
     */
    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return divide(dividend, divisor, 2, RoundingMode.HALF_UP);
    }

    /**
     * 舍入数值，默认舍入模式 {@link RoundingMode#HALF_UP}。
     *
     * @param val   数值
     * @param scale 小数位
     * @return 舍入后的 {@link BigDecimal}
     */
    public static BigDecimal round(BigDecimal val, int scale) {
        return nvl(val).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 舍入数值。
     *
     * @param val   数值
     * @param scale 小数位
     * @param mode  舍入模式
     * @return 舍入后的 {@link BigDecimal}
     */
    public static BigDecimal round(BigDecimal val, int scale, RoundingMode mode) {
        return nvl(val).setScale(scale, mode);
    }

    /**
     * 去掉数值末尾多余零。
     *
     * @param val 数值
     * @return 去掉末尾零的 {@link BigDecimal}
     */
    public static BigDecimal stripTrailingZeros(BigDecimal val) {
        return nvl(val).stripTrailingZeros();
    }

    /**
     * 转换为非科学计数法字符串。
     *
     * @param val 数值
     * @return 非科学计数法字符串
     */
    public static String toPlainString(BigDecimal val) {
        return nvl(val).toPlainString();
    }

    /**
     * 安全转换为字符串，去掉多余零。
     *
     * @param val 数值
     * @return 数值字符串，{@code null} 返回 {@code "0"}
     */
    public static String toStringSafe(BigDecimal val) {
        return Objects.nonNull(val) ? val.stripTrailingZeros().toPlainString() : "0";
    }

    /**
     * 判断数值是否为零。
     *
     * @param val 数值
     * @return {@code true} 表示数值为零
     */
    public static boolean isZero(BigDecimal val) {
        return nvl(val).compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断数值是否为正数。
     *
     * @param val 数值
     * @return {@code true} 表示数值大于零
     */
    public static boolean isPositive(BigDecimal val) {
        return nvl(val).compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断数值是否为负数。
     *
     * @param val 数值
     * @return {@code true} 表示数值小于零
     */
    public static boolean isNegative(BigDecimal val) {
        return nvl(val).compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 设置数值的小数位精度，默认舍入模式 {@link RoundingMode#HALF_UP}。
     *
     * @param val   数值
     * @param scale 小数位
     * @return 设置精度后的 {@link BigDecimal}，输入为 {@code null} 返回零
     */
    public static BigDecimal setScale(BigDecimal val, int scale) {
        if (Objects.isNull(val)) {
            return zero();
        }
        return val.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 判断 {@code a} 是否大于 {@code b}。
     *
     * @param a 数值 a
     * @param b 数值 b
     * @return {@code true} 表示 a > b
     */
    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        return compare(a, b) > 0;
    }

    /**
     * 判断 {@code a} 是否小于 {@code b}。
     *
     * @param a 数值 a
     * @param b 数值 b
     * @return {@code true} 表示 a &lt; b
     */
    public static boolean lessThan(BigDecimal a, BigDecimal b) {
        return compare(a, b) < 0;
    }

    /**
     * 判断 {@code a} 是否等于 {@code b}。
     *
     * @param a 数值 a
     * @param b 数值 b
     * @return {@code true} 表示相等
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 0;
    }

    /**
     * 比较两个 {@link BigDecimal}。
     *
     * @param a 数值 a
     * @param b 数值 b
     * @return {@code a.compareTo(b)}，若有 {@code null} 值按规则处理
     */
    public static int compare(BigDecimal a, BigDecimal b) {
        if (Objects.isNull(a) && Objects.isNull(b)) {
            return 0;
        }
        if (Objects.isNull(a)) {
            return -1;
        }
        if (Objects.isNull(b)) {
            return 1;
        }
        return a.compareTo(b);
    }

    /**
     * 处理 {@code null} 数值，返回零。
     *
     * @param val 数值
     * @return 原值或零
     */
    private static BigDecimal nvl(BigDecimal val) {
        return Objects.nonNull(val) ? val : zero();
    }
}
