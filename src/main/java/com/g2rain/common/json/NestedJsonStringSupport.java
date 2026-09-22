package com.g2rain.common.json;


import com.g2rain.common.utils.Strings;

/**
 * 嵌套 JSON 字符串形态判断：首尾是否为 {@code {}} / {@code []}，供 {@link NestedJsonStrObjectDeserializer} 与 {@link NestedJsonStrNodeDeserializer} 共用。
 *
 * @author alpha
 * @since 2026/8/26
 */
final class NestedJsonStringSupport {

    private NestedJsonStringSupport() {

    }

    /**
     * 非 blank 且 trim 后以 {@code {}} 或 {@code []} 包裹。
     */
    static boolean isJsonObjectOrArray(String value) {
        if (Strings.isBlank(value)) {
            return false;
        }

        String text = value.trim();
        char first = text.charAt(0), last = text.charAt(text.length() - 1);
        return (first == '{' && last == '}') || (first == '[' && last == ']');
    }

    /**
     * 普通文本：不符合 {@link #isJsonObjectOrArray(String)}。
     */
    static boolean isPlainString(String value) {
        return !isJsonObjectOrArray(value);
    }
}
