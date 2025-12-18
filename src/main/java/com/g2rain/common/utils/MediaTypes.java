package com.g2rain.common.utils;


import java.util.Locale;

/**
 * <p>工具类 {@code MediaTypes} 提供常用 MIME 类型的常量判断方法，以及对 Content-Type 字符串的规范化处理。</p>
 * <p>支持判断是否为 octet-stream、multipart/form-data、application/x-www-form-urlencoded、application/json 等类型。</p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * boolean isJson = MediaTypes.isJson("application/json;charset=UTF-8");
 * boolean isForm = MediaTypes.isFormUrlEncoded("application/x-www-form-urlencoded; charset=UTF-8");
 * boolean isMultipart = MediaTypes.isMultipartFormData("multipart/form-data;boundary=----WebKitFormBoundary");
 * boolean isOctet = MediaTypes.isOctetStream("application/octet-stream");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class MediaTypes {

    /**
     * 私有构造，防止实例化。
     */
    private MediaTypes() {

    }

    /**
     * octet-stream MIME 类型。
     */
    private static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    /**
     * multipart/form-data MIME 类型。
     */
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    /**
     * application/x-www-form-urlencoded MIME 类型。
     */
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * application/json MIME 类型。
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * 判断 Content-Type 是否为 {@code application/octet-stream}。
     *
     * @param contentType Content-Type 字符串
     * @return 是 octet-stream 返回 {@code true}，否则 {@code false}
     */
    public static boolean isOctetStream(String contentType) {
        return APPLICATION_OCTET_STREAM.equalsIgnoreCase(normalize(contentType));
    }

    /**
     * 判断 Content-Type 是否为 {@code multipart/form-data}。
     *
     * @param contentType Content-Type 字符串
     * @return 是 multipart/form-data 返回 {@code true}，否则 {@code false}
     */
    public static boolean isMultipartFormData(String contentType) {
        return MULTIPART_FORM_DATA.equalsIgnoreCase(normalize(contentType));
    }

    /**
     * 判断 Content-Type 是否为 {@code application/x-www-form-urlencoded}。
     *
     * @param contentType Content-Type 字符串
     * @return 是 form-urlencoded 返回 {@code true}，否则 {@code false}
     */
    public static boolean isFormUrlEncoded(String contentType) {
        return APPLICATION_FORM_URLENCODED.equalsIgnoreCase(normalize(contentType));
    }

    /**
     * 判断 Content-Type 是否为 {@code application/json}。
     *
     * @param contentType Content-Type 字符串
     * @return 是 JSON 返回 {@code true}，否则 {@code false}
     */
    public static boolean isJson(String contentType) {
        return APPLICATION_JSON.equalsIgnoreCase(normalize(contentType));
    }

    /**
     * 规范化 Content-Type 字符串：
     * <ul>
     *     <li>去除分号及其后的参数</li>
     *     <li>去除首尾空格</li>
     *     <li>转换为小写</li>
     * </ul>
     *
     * @param contentType Content-Type 字符串
     * @return 规范化后的 Content-Type
     */
    private static String normalize(String contentType) {
        if (Strings.isBlank(contentType)) {
            return "";
        }

        int idx = contentType.indexOf(";");
        if (idx != -1) {
            contentType = contentType.substring(0, idx);
        }

        return contentType.trim().toLowerCase(Locale.ROOT);
    }
}
