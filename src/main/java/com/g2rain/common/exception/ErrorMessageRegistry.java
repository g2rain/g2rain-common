package com.g2rain.common.exception;


import com.g2rain.common.syncer.AbstractMessageStorage;

/**
 * <p>错误消息注册中心抽象类，用于存储和获取本地化的错误消息。</p>
 * <p>
 * 继承自 {@link AbstractMessageStorage}，以支持基于错误码和区域信息检索错误消息。
 * 所有具体模块需要实现此类，并提供 {@link #getMessage(String, String)} 方法的具体实现。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * public class MyErrorMessageRegistry extends ErrorMessageRegistry {
 *     @Override
 *     public String getMessage(String errorCode, String locale) {
 *         // 从存储中根据错误码和区域信息获取错误消息
 *         return "Localized error message";
 *     }
 * }
 *
 * ErrorMessageRegistry registry = new MyErrorMessageRegistry();
 * String msg = registry.getMessage("400", "zh_CN");
 * System.out.println(msg); // 输出: Localized error message
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public abstract class ErrorMessageRegistry extends AbstractMessageStorage<String, LocalizedErrorMessage, String> {

    /**
     * <p>根据错误码和区域信息获取本地化的错误消息。</p>
     *
     * <b>使用示例：</b>
     * <pre>{@code
     * String message = registry.getMessage("ERROR_CODE", "en_US");
     * }</pre>
     *
     * @param errorCode 错误码
     * @param locale    区域信息，例如 "zh_CN"、"en_US"
     * @return 本地化的错误消息字符串，如果未找到返回 {@code null}
     */
    public abstract String getMessage(String errorCode, String locale);
}
