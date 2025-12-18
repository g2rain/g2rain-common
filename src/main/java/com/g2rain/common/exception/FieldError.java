package com.g2rain.common.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <p>{@code FieldError} 表示字段级别的错误信息，继承自 {@link BaseError}。</p>
 * <p>
 * 用于封装特定字段的错误码、错误信息、键值参数和索引参数，便于返回详细的字段验证或业务错误信息。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * FieldError error1 = new FieldError("username", MyErrorCode.INVALID_INPUT);
 * FieldError error2 = new FieldError("password", MyErrorCode.INVALID_INPUT, Map.of("minLength", 8));
 * FieldError error3 = new FieldError("email", MyErrorCode.INVALID_FORMAT, "invalid@example.com");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Getter
@NoArgsConstructor
public class FieldError extends BaseError {

    /**
     * 出现错误的字段名。
     */
    private String field;

    /**
     * 构造指定字段和错误码的字段错误对象。
     *
     * @param field     出现错误的字段名
     * @param errorCode 错误码对象
     */
    public FieldError(String field, ErrorCode errorCode) {
        this(field, errorCode, null, null);
    }

    /**
     * 构造指定字段、错误码及键值参数的字段错误对象。
     *
     * @param field     出现错误的字段名
     * @param errorCode 错误码对象
     * @param keyArgs   键值参数映射
     */
    public FieldError(String field, ErrorCode errorCode, Map<String, Object> keyArgs) {
        this(field, errorCode, keyArgs, null);
    }

    /**
     * 构造指定字段、错误码及索引参数的字段错误对象。
     *
     * @param field     出现错误的字段名
     * @param errorCode 错误码对象
     * @param indexArgs 索引参数数组
     */
    public FieldError(String field, ErrorCode errorCode, Object... indexArgs) {
        this(field, errorCode, null, indexArgs);
    }

    /**
     * 构造指定字段、错误码、键值参数及索引参数的字段错误对象。
     *
     * @param field     出现错误的字段名
     * @param errorCode 错误码对象
     * @param keyArgs   键值参数映射
     * @param indexArgs 索引参数数组
     */
    public FieldError(String field, ErrorCode errorCode, Map<String, Object> keyArgs, Object[] indexArgs) {
        super(errorCode, ExceptionConverter.buildMessage(errorCode, keyArgs, indexArgs), keyArgs, indexArgs);
        this.field = field;
    }
}
