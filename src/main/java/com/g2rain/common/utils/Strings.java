package com.g2rain.common.utils;


import java.util.Locale;
import java.util.Objects;

/**
 * <p>工具类 {@code Strings} 提供常用的字符串处理方法，包括字符串的空值检查、大小写转换、修剪、比较等操作。</p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * String result = Strings.trim("  hello ");
 * boolean isBlank = Strings.isBlank(" ");
 * boolean isNotEmpty = Strings.isNotEmpty("text");
 * boolean starts = Strings.startsWith("hello", "he");
 * boolean equals = Strings.equals("abc", "abc");
 * boolean equalsIgnoreCase = Strings.equalsIgnoreCase("abc", "ABC");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class Strings {
    /**
     * 私有构造，防止实例化
     */
    private Strings() {

    }

    /**
     * 修剪字符串的首尾空格。
     *
     * @param str 需要修剪的字符串
     * @return 修剪后的字符串，输入为 {@code null} 返回 {@code null}
     */
    public static String trim(String str) {
        return Objects.nonNull(str) ? str.trim() : null;
    }

    /**
     * 将字符串转换为小写（基于 {@link Locale#ROOT}）。
     *
     * @param str 需要转换的字符串
     * @return 小写字符串，输入为 {@code null} 返回 {@code null}
     */
    public static String toLowerCase(String str) {
        return Objects.nonNull(str) ? str.toLowerCase(Locale.ROOT) : null;
    }

    /**
     * 将字符串转换为大写（基于 {@link Locale#ROOT}）。
     *
     * @param str 需要转换的字符串
     * @return 大写字符串，输入为 {@code null} 返回 {@code null}
     */
    public static String toUpperCase(String str) {
        return Objects.nonNull(str) ? str.toUpperCase(Locale.ROOT) : null;
    }

    /**
     * 判断字符串是否为 {@code null} 或仅包含空白字符。
     *
     * @param str 待判断字符串
     * @return {@code true} 表示为空白或 {@code null}，否则 {@code false}
     */
    public static boolean isBlank(String str) {
        return Objects.isNull(str) || str.isBlank();
    }

    /**
     * 判断字符串是否不为空白。
     *
     * @param str 待判断字符串
     * @return {@code true} 表示不为空白，{@code false} 表示为空白或 {@code null}
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断字符序列是否为空。
     *
     * @param str 待判断字符序列
     * @return {@code true} 表示为空或 {@code null}，否则 {@code false}
     */
    public static boolean isEmpty(CharSequence str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    /**
     * 判断字符序列是否非空。
     *
     * @param str 待判断字符序列
     * @return {@code true} 表示非空，{@code false} 表示为空或 {@code null}
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否以指定前缀开始。
     *
     * @param str    待判断字符串
     * @param prefix 前缀字符串
     * @return {@code true} 表示以指定前缀开始，否则 {@code false}
     */
    public static boolean startsWith(String str, String prefix) {
        if (Objects.isNull(str) || Objects.isNull(prefix)) {
            return false;
        }
        return str.startsWith(prefix);
    }

    /**
     * 判断字符串是否以指定后缀结束。
     *
     * @param str    待判断字符串
     * @param suffix 后缀字符串
     * @return {@code true} 表示以指定后缀结束，否则 {@code false}
     */
    public static boolean endsWith(String str, String suffix) {
        if (Objects.isNull(str) || Objects.isNull(suffix)) {
            return false;
        }
        return str.endsWith(suffix);
    }

    /**
     * 获取字符串长度。
     *
     * @param str 待获取长度的字符串
     * @return 字符串长度，字符串为 {@code null} 返回 {@code 0}
     */
    public static int length(String str) {
        return Objects.nonNull(str) ? str.length() : 0;
    }

    /**
     * 判断两个字符串是否相等（考虑 {@code null}）。
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return {@code true} 表示相等，否则 {@code false}
     */
    public static boolean equals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    /**
     * 判断两个字符串是否相等（忽略大小写，考虑 {@code null}）。
     *
     * @param str1 字符串 1
     * @param str2 字符串 2
     * @return {@code true} 表示相等（忽略大小写），否则 {@code false}
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (Objects.isNull(str1) && Objects.isNull(str2)) {
            return true;
        }
        if (Objects.isNull(str1) || Objects.isNull(str2)) {
            return false;
        }
        return str1.equalsIgnoreCase(str2);
    }
}
