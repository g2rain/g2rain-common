package com.g2rain.common.exception;


import com.g2rain.common.model.Result;
import com.g2rain.common.utils.Collections;
import lombok.NonNull;

import java.util.Map;
import java.util.Objects;

/**
 * <p>{@code ExceptionConverter} 提供将 {@link Result} 转换为 {@link BusinessException} 以及构建错误消息的方法工具类。</p>
 * <p>
 * 此类为最终类，所有方法均为静态方法，不能实例化。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * Result<String> result = Result.error(MyErrorCode.INVALID_INPUT, "Invalid username");
 * BusinessException ex = ExceptionConverter.of(result);
 *
 * String message = ExceptionConverter.buildMessage(MyErrorCode.INVALID_INPUT, null, new Object[]{"username"});
 * System.out.println(message); // 输出: Invalid input: username
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class ExceptionConverter {

    /**
     * 私有构造函数，防止实例化。
     */
    private ExceptionConverter() {
    }

    /**
     * 将 {@link Result} 转换为 {@link BusinessException}。
     *
     * @param result 不可为 {@code null} 的业务结果对象
     * @param <T>    Result 中的数据类型
     * @return {@link BusinessException} 对象
     */
    public static <T> BusinessException of(@NonNull Result<T> result) {
        return new BusinessException(result.getErrorCode(), result.getErrorMessage(),
            result.getKeyArgs(), result.getIndexArgs(), result.getFieldErrors()
        );
    }

    /**
     * 根据 {@link ErrorCode} 和参数构建错误消息字符串。
     * 优先使用键值参数填充占位符，如果键值参数为空则使用索引参数填充。
     *
     * @param errorCode 错误码对象
     * @param keyArgs   键值对参数映射，可为 {@code null}
     * @param indexArgs 顺序参数数组，可为 {@code null}
     * @return 填充后的错误消息字符串
     */
    public static String buildMessage(ErrorCode errorCode, Map<String, Object> keyArgs, Object[] indexArgs) {
        if (Collections.isNotEmpty(keyArgs)) {
            return errorCode.getMessage(keyArgs);
        }

        if (Collections.isNotEmpty(indexArgs)) {
            return errorCode.getMessage(indexArgs);
        }

        return errorCode.messageTemplate();
    }

    /**
     * 从异常链中查找第一个 BusinessException，如果不存在则返回默认异常。
     * <p>
     * 遍历异常链最多 10 层，避免过深的循环。若找到 BusinessException 则直接返回，
     * 否则返回一个新的 BusinessException，表示系统内部错误。
     *
     * @param cause 起始异常
     * @return 找到的 BusinessException 或默认系统内部异常
     */
    public static BusinessException findBusinessExceptionOrDefault(Throwable cause) {
        Throwable current = cause;
        for (int i = 0; i < 10 && Objects.nonNull(current); i++) {
            if (current instanceof BusinessException be) {
                return be;
            }

            current = current.getCause();
        }

        return new BusinessException(SystemErrorCode.SYSTEM_INTERNAL_ERROR, "unknown");
    }
}
