package com.g2rain.common.utils;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>工具类 {@code Moments} 提供基于 Java 8 {@link java.time} API 的日期时间格式化与解析方法。</p>
 * <p>功能包括：</p>
 * <ul>
 *     <li>默认日期时间格式化</li>
 *     <li>自定义格式化</li>
 *     <li>时间戳转换为日期时间字符串</li>
 *     <li>字符串解析为 {@link java.time.LocalDateTime}</li>
 *     <li>格式化模式缓存</li>
 * </ul>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * String nowStr = Moments.nowString(); // 默认格式
 * String custom = Moments.nowString("yyyy/MM/dd HH:mm"); // 自定义格式
 * LocalDateTime time = Moments.parse("2025-10-07 12:00:00");
 * String formatted = Moments.format(time, "yyyy-MM-dd");
 * Moments.clearCache("yyyy/MM/dd HH:mm");
 * }</pre>
 *
 * @author jagger
 * @since 2025/9/25
 */
public class Moments {
    /**
     * 私有构造，防止实例化
     */
    private Moments() {

    }

    /**
     * 默认日期时间格式模式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期时间格式化器
     */
    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    /**
     * 日期时间格式化器缓存
     */
    private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

    // 静态初始化块，在类加载时执行
    static {
        // 预加载默认格式，确保默认格式始终可用
        FORMATTER_CACHE.put(DEFAULT_PATTERN, DEFAULT_FORMATTER);
    }

    /**
     * 获取指定格式的 {@link DateTimeFormatter}，支持缓存。
     *
     * @param pattern 日期时间格式模式
     * @return {@link DateTimeFormatter} 对象
     */
    private static DateTimeFormatter getFormatter(String pattern) {
        if (Strings.isBlank(pattern)) {
            return DEFAULT_FORMATTER;
        }

        String formatKey = pattern.trim();
        return FORMATTER_CACHE.computeIfAbsent(formatKey, DateTimeFormatter::ofPattern);
    }

    /**
     * 使用默认格式化器格式化 {@link LocalDateTime}。
     *
     * @param dateTime 日期时间对象
     * @return 格式化后的字符串，日期为 {@code null} 返回 {@code null}
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_PATTERN);
    }

    /**
     * 使用指定格式模式格式化 {@link LocalDateTime}。
     *
     * @param dateTime 日期时间对象
     * @param pattern  格式化模式
     * @return 格式化后的字符串，日期为 {@code null} 返回 {@code null}
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        return getFormatter(pattern).format(dateTime);
    }

    /**
     * 将时间戳（毫秒）格式化为默认格式字符串。
     *
     * @param timestamp 时间戳（毫秒）
     * @return 格式化后的字符串
     */
    public static String formatEpochMillis(Long timestamp) {
        return DEFAULT_FORMATTER.format(
            Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }

    /**
     * 使用默认格式解析字符串为 {@link LocalDateTime}。
     *
     * @param dateTimeStr 日期时间字符串
     * @return 解析后的 {@link LocalDateTime}，格式错误或字符串为空返回 {@code null}
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return parse(dateTimeStr, DEFAULT_PATTERN);
    }

    /**
     * 使用指定格式解析字符串为 {@link LocalDateTime}。
     *
     * @param dateTimeStr 日期时间字符串
     * @param pattern     格式化模式
     * @return 解析后的 {@link LocalDateTime}
     * @throws IllegalArgumentException 格式错误时抛出异常
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        if (Strings.isBlank(dateTimeStr)) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateTimeStr.trim(), getFormatter(pattern));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                String.format("日期时间格式错误，需符合%s，输入值：%s", pattern, dateTimeStr), e
            );
        }
    }

    /**
     * 获取当前时间 {@link LocalDateTime}。
     *
     * @return 当前时间对象
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前时间的默认格式字符串。
     *
     * @return 当前时间字符串
     */
    public static String nowString() {
        return format(now());
    }

    /**
     * 获取当前时间的自定义格式字符串。
     *
     * @param pattern 格式化模式
     * @return 当前时间字符串
     */
    public static String nowString(String pattern) {
        return format(now(), pattern);
    }

    /**
     * 清理指定格式的缓存，若格式为 {@link #DEFAULT_PATTERN} 则不清理。
     *
     * @param pattern 格式化模式
     */
    public static void clearCache(String pattern) {
        if (Objects.isNull(pattern)) {
            FORMATTER_CACHE.keySet().removeIf(key -> !key.equals(DEFAULT_PATTERN));
            return;
        }

        String formatKey = pattern.trim();
        if (!formatKey.equals(DEFAULT_PATTERN)) {
            FORMATTER_CACHE.remove(formatKey);
        }
    }

    /**
     * 获取当前格式化器缓存大小。
     *
     * @return 缓存数量
     */
    public static int getCacheSize() {
        return FORMATTER_CACHE.size();
    }
}
