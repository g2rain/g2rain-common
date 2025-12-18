package com.g2rain.common.exception;

import java.util.Map;

/**
 * <p>错误码基础接口。</p>
 * <p>
 * 所有模块的错误码都必须实现此接口，以提供统一的错误码和错误信息模板。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * public enum MyErrorCode implements ErrorCode {
 *     INVALID_INPUT("400", "Invalid input: {0}");
 *
 *     private final String code;
 *     private final String messageTemplate;
 *
 *     MyErrorCode(String code, String messageTemplate) {
 *         this.code = code;
 *         this.messageTemplate = messageTemplate;
 *     }
 *
 *     @Override
 *     public String code() {
 *         return code;
 *     }
 *
 *     @Override
 *     public String messageTemplate() {
 *         return messageTemplate;
 *     }
 * }
 *
 * String msg = MyErrorCode.INVALID_INPUT.getMessage("username");
 * System.out.println(msg); // 输出: Invalid input: username
 * }</pre>
 *
 * @author jagger
 * @since 2025/10/5
 */
public interface ErrorCode {

    /**
     * <p>获取错误码。</p>
     *
     * @return 错误码整数
     */
    String code();

    /**
     * <p>获取消息模板，可包含顺序占位符 {@code {0}} 或键值占位符 {@code {key}}。</p>
     *
     * @return 消息模板字符串
     */
    String messageTemplate();

    /**
     * <p>使用顺序参数填充占位符（例如 {@code {0}}、{@code {1}}）。</p>
     *
     * <b>示例：</b>
     * <pre>{@code
     * String msg = errorCode.getMessage("username", "password");
     * }</pre>
     *
     * @param args 顺序参数数组
     * @return 填充后的消息字符串
     */
    default String getMessage(Object... args) {
        return MessageResolver.resolveByIndex(messageTemplate(), args);
    }

    /**
     * <p>使用键值对参数填充占位符（例如 {@code {username}}、{@code {orderNo}}）。</p>
     *
     * <b>示例：</b>
     * <pre>{@code
     * Map<String, Object> params = new HashMap<>();
     * params.put("username", "JohnDoe");
     * params.put("orderNo", 12345);
     * String msg = errorCode.getMessage(params);
     * }</pre>
     *
     * @param params 键值对参数映射
     * @return 填充后的消息字符串
     */
    default String getMessage(Map<String, Object> params) {
        return MessageResolver.resolveByKey(messageTemplate(), params);
    }
}
