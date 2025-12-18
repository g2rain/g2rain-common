package com.g2rain.common.exception;


import com.g2rain.common.model.Result;
import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Strings;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 默认的 {@link ExceptionProcessor} 实现，用于处理 {@link BusinessException} 并生成标准 {@link Result} 响应。
 * <p>
 * 如果注入了 {@link ErrorMessageRegistry}，则会根据异常的错误码和可选的本地化信息获取错误消息模板，
 * 并结合 key 或 index 参数解析出最终错误消息。
 * <p>
 * 对于字段级错误（{@link FieldError}），也会尝试解析其错误码对应的模板并生成对应的错误消息。
 * <p>
 * 如果未注入 {@link ErrorMessageRegistry}，仅返回异常转换后的基础 {@link Result}，不做消息解析。
 *
 * @author alpha
 * @since 2025/10/5
 */
public record DefaultExceptionProcessor(ErrorMessageRegistry registry) implements ExceptionProcessor {

    /**
     * 处理 {@link BusinessException} 并转换为 {@link Result}。
     * <p>
     * 处理逻辑：
     * <ol>
     *     <li>将 {@link BusinessException} 转为 {@link Result} 对象。</li>
     *     <li>如果存在 {@link ErrorMessageRegistry}，根据异常错误码和 locale 获取消息模板，
     *         并根据 keyArgs 或 indexArgs 设置 {@code Result.errorMessage} 错误消息。</li>
     *     <li>对于字段级错误（{@link FieldError}），同样根据其错误码和参数解析错误消息。</li>
     *     <li>如果没有注册表或消息模板为空，则保留默认错误信息。</li>
     * </ol>
     *
     * @param ex     捕获到的 {@link BusinessException} 对象
     * @param locale 本地化信息，用于选择消息模板的语言
     * @return {@link Result} 响应对象，包含错误码和解析后的错误消息
     */
    @Override
    public Result<Void> process(BusinessException ex, String locale) {
        Result<Void> result = toResult(ex);
        if (Objects.isNull(this.registry)) {
            return result;
        }

        String template = this.registry.getMessage(result.getErrorCode(), locale);
        if (Strings.isNotBlank(template)) {
            if (Collections.isNotEmpty(result.getKeyArgs())) {
                result.setErrorMessage(MessageResolver.resolveByKey(template, result.getKeyArgs()));
            } else if (Collections.isNotEmpty(result.getIndexArgs())) {
                result.setErrorMessage(MessageResolver.resolveByIndex(template, result.getIndexArgs()));
            }
        }

        List<FieldError> fieldErrors = result.getFieldErrors();
        if (Collections.isEmpty(fieldErrors)) {
            return result;
        }

        for (FieldError fieldError : fieldErrors) {
            template = this.registry.getMessage(fieldError.getErrorCode(), locale);
            if (Strings.isBlank(template)) {
                continue;
            }

            Map<String, Object> keyArgs = fieldError.getKeyArgs();
            Object[] indexArgs = fieldError.getIndexArgs();

            if (Collections.isNotEmpty(keyArgs)) {
                result.setErrorMessage(MessageResolver.resolveByKey(template, keyArgs));
            } else if (Collections.isNotEmpty(indexArgs)) {
                result.setErrorMessage(MessageResolver.resolveByIndex(template, indexArgs));
            }
        }

        return result;
    }
}
