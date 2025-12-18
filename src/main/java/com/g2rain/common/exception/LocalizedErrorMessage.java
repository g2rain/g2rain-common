package com.g2rain.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>{@code LocalizedErrorMessage} 表示本地化的错误消息对象。</p>
 * <p>
 * 用于存储某一错误码在特定区域的消息模板，支持多语言或区域化错误提示。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * LocalizedErrorMessage msg = new LocalizedErrorMessage();
 * msg.setErrorCode("INVALID_INPUT");
 * msg.setLocale("zh_CN");
 * msg.setMessageTemplate("输入无效: {0}");
 *
 * LocalizedErrorMessage msg2 = new LocalizedErrorMessage("INVALID_INPUT", "en_US", "Invalid input: {0}");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedErrorMessage {

    /**
     * 错误码字符串，对应 {@link ErrorCode#code()} 或自定义错误码。
     */
    private String errorCode;

    /**
     * 区域信息，例如 "zh_CN" 或 "en_US"。
     */
    private String locale;

    /**
     * 错误消息模板，可包含占位符，如 {0} 或 {key}。
     */
    private String messageTemplate;
}
