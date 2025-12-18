package com.g2rain.common.exception;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>{@code BusinessException} 是业务异常类，继承自 {@link RuntimeException}，用于封装业务逻辑中的错误信息。</p>
 * <p>
 * 该类包含错误码、错误信息、可选的键值参数、索引参数以及字段错误信息，
 * 支持灵活构造业务异常并提供方便的错误上下文查询。
 * </p>
 * <p>
 * 通过设置系统属性 <code>business.exception.stacktrace.disable</code> 为 <code>true</code>
 * 可禁用堆栈跟踪，从而在生产环境中提升异常抛出性能。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * Map<String, Object> keyArgs = new HashMap<>();
 * keyArgs.put("field", "username");
 * BusinessException exception = new BusinessException(ErrorCode.INVALID_INPUT, keyArgs);
 * throw exception;
 * }</pre>
 *
 * @author jagger
 * @since 2025/10/5
 */
public class BusinessException extends RuntimeException implements Serializable {

    /**
     * <p>控制是否禁用堆栈跟踪的系统属性标志。</p>
     * <p>
     * 通过设置系统属性 <code>business.exception.stacktrace.disable</code> 为 <code>true</code>
     * 可禁用堆栈跟踪，用于生产环境中提高异常抛出性能。
     * </p>
     */
    private static final boolean STACKTRACE_DISABLE = Boolean.getBoolean("business.exception.stacktrace.disable");

    /**
     * <p>封装基础错误信息的对象。</p>
     */
    private final transient BaseError baseError;

    /**
     * <p>字段级别的错误信息列表。</p>
     */
    private final transient List<FieldError> fieldErrors;

    /**
     * <p>构造一个带有 {@link ErrorCode} 的业务异常。</p>
     *
     * @param errorCode 错误码枚举对象
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode, null, null, null, null);
    }

    /**
     * <p>构造一个带有 {@link ErrorCode} 和键值参数的业务异常。</p>
     *
     * @param errorCode 错误码枚举对象
     * @param keyArgs   键值参数映射
     */
    public BusinessException(ErrorCode errorCode, Map<String, Object> keyArgs) {
        this(errorCode, keyArgs, null, null, null);
    }

    /**
     * <p>构造一个带有 {@link ErrorCode} 和索引参数的业务异常。</p>
     *
     * @param errorCode 错误码枚举对象
     * @param indexArgs 索引参数数组
     */
    public BusinessException(ErrorCode errorCode, Object... indexArgs) {
        this(errorCode, null, indexArgs, null, null);
    }

    /**
     * <p>构造一个带有 {@link ErrorCode} 和字段错误信息的业务异常。</p>
     *
     * @param errorCode   错误码枚举对象
     * @param fieldErrors 字段错误信息列表
     */
    public BusinessException(ErrorCode errorCode, List<FieldError> fieldErrors) {
        this(errorCode, null, null, fieldErrors, null);
    }

    /**
     * <p>构造一个带有 {@link ErrorCode} 和异常原因的业务异常。</p>
     *
     * @param errorCode 错误码枚举对象
     * @param cause     异常原因
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, null, null, null, cause);
    }

    /**
     * <p>构造一个完整的 {@code BusinessException} 对象。</p>
     *
     * @param errorCode   错误码枚举对象
     * @param keyArgs     键值参数映射
     * @param indexArgs   索引参数数组
     * @param fieldErrors 字段错误信息列表
     * @param cause       异常原因
     */
    public BusinessException(ErrorCode errorCode, Map<String, Object> keyArgs,
                             Object[] indexArgs, List<FieldError> fieldErrors, Throwable cause) {
        super(ExceptionConverter.buildMessage(errorCode, keyArgs, indexArgs), cause);
        this.baseError = new BaseError(errorCode, super.getMessage(), keyArgs, indexArgs);
        this.fieldErrors = Objects.nonNull(fieldErrors) ? new ArrayList<>(fieldErrors) : new ArrayList<>();
    }

    /**
     * <p>内部构造方法，基于自定义错误码构造 {@code BusinessException}。</p>
     *
     * @param errorCode    错误码字符串
     * @param errorMessage 错误信息
     * @param keyArgs      键值参数映射
     * @param indexArgs    索引参数数组
     * @param fieldErrors  字段错误信息列表
     */
    BusinessException(String errorCode, String errorMessage, Map<String, Object> keyArgs,
                      Object[] indexArgs, List<FieldError> fieldErrors) {
        super(errorMessage);
        this.baseError = new BaseError(errorCode, errorMessage, keyArgs, indexArgs);
        this.fieldErrors = Objects.nonNull(fieldErrors) ? new ArrayList<>(fieldErrors) : new ArrayList<>();
    }

    /**
     * <p>添加一个字段错误信息。</p>
     *
     * @param fieldError 字段错误信息
     * @return 当前 {@code BusinessException} 对象
     */
    @SuppressWarnings("UnusedReturnValue")
    public BusinessException addFieldError(FieldError fieldError) {
        if (Objects.nonNull(fieldError)) {
            this.fieldErrors.add(fieldError);
        }
        return this;
    }

    /**
     * <p>批量添加字段错误信息。</p>
     *
     * @param errors 字段错误信息集合
     * @return 当前 {@code BusinessException} 对象
     */
    public BusinessException addFieldErrors(Collection<FieldError> errors) {
        if (com.g2rain.common.utils.Collections.isNotEmpty(errors)) {
            this.fieldErrors.addAll(errors);
        }
        return this;
    }

    /**
     * <p>判断是否包含字段错误信息。</p>
     *
     * @return {@code true} 如果存在字段错误，否则 {@code false}
     */
    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    /**
     * <p>获取字段错误信息列表（不可修改）。</p>
     *
     * @return 字段错误信息列表
     */
    public List<FieldError> getFieldErrors() {
        return Collections.unmodifiableList(fieldErrors);
    }

    /**
     * <p>获取索引参数数组。</p>
     *
     * @return 索引参数数组
     */
    public Object[] getIndexArgs() {
        return baseError.getIndexArgs();
    }

    /**
     * <p>获取键值参数映射。</p>
     *
     * @return 键值参数映射
     */
    public Map<String, Object> getKeyArgs() {
        return baseError.getKeyArgs();
    }

    /**
     * <p>获取错误码字符串。</p>
     *
     * @return 错误码
     */
    public String getErrorCode() {
        return baseError.getErrorCode();
    }

    /**
     * <p>获取错误信息字符串。</p>
     *
     * @return 错误信息
     */
    public String getErrorMessage() {
        return baseError.getErrorMessage();
    }

    /**
     * <p>填充异常堆栈信息。</p>
     * <p>
     * 当系统属性 <code>business.exception.stacktrace.disable</code> 设置为
     * <code>true</code> 时，将禁用堆栈跟踪。
     * </p>
     *
     * @return 当前异常对象
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return STACKTRACE_DISABLE ? this : super.fillInStackTrace();
    }
}
