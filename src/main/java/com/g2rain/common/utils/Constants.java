package com.g2rain.common.utils;


/**
 * <p>常量工具类 {@code Constants}，用于存放系统通用常量。</p>
 * <p>所有字段均为静态常量，类不可实例化。</p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * byte[] empty = Constants.EMPTY_BYTE;
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class Constants {

    /**
     * 私有构造，防止实例化。
     */
    private Constants() {

    }

    /**
     * 空字节数组常量。
     * <p>可用于避免多次创建空数组，提升性能。</p>
     */
    public static final byte[] EMPTY_BYTE = new byte[0];

    /**
     * Jackson 序列化动态忽略字段的过滤器ID
     */
    public static final String CONDITIONAL_JSON_IGNORE_FILTER_ID = "conditionalFilter";
}
