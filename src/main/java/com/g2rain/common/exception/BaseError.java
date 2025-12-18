package com.g2rain.common.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>{@code BaseError} 类用于封装系统或业务错误信息。</p>
 * <p>
 * 包含错误码、错误信息、以及可选的参数信息，
 * 方便统一错误处理与日志记录。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * Map<String, Object> keyArgs = new HashMap<>();
 * keyArgs.put("field", "username");
 * Object[] indexArgs = new Object[] {"JohnDoe"};
 * BaseError error = new BaseError("400", "Invalid username", keyArgs, indexArgs);
 * System.out.println(error.getErrorCode()); // 输出：400
 * System.out.println(error.getErrorMessage()); // 输出：Invalid username
 * }</pre>
 *
 * @author jagger
 * @since 2025/10/5
 */
@NoArgsConstructor
public class BaseError {

    /**
     * <p>错误码，用于唯一标识错误类型。</p>
     * <p>可能来源于枚举 {@link ErrorCode} 或自定义字符串。</p>
     */
    @Getter
    private String errorCode;

    /**
     * <p>错误信息，描述具体错误内容。</p>
     */
    @Getter
    private String errorMessage;

    /**
     * <p>错误相关的键值参数，用于详细描述错误上下文。</p>
     * <p>例如：{"field": "username"}</p>
     */
    private Map<String, Object> keyArgs;

    /**
     * <p>错误相关的索引参数，用于详细描述错误上下文。</p>
     * <p>例如：{"JohnDoe"}</p>
     */
    private Object[] indexArgs;

    /**
     * <p>基于 {@link ErrorCode} 构造 {@code BaseError}。</p>
     * <p><b>示例：</b></p>
     * <pre>{@code
     * Map<String, Object> keyArgs = new HashMap<>();
     * keyArgs.put("field", "username");
     * Object[] indexArgs = new Object[] {"JohnDoe"};
     * BaseError error = new BaseError(ErrorCode.INVALID_INPUT, "Invalid username", keyArgs, indexArgs);
     * }</pre>
     *
     * @param errorCode    错误码枚举对象，不能为空
     * @param errorMessage 错误信息描述
     * @param keyArgs      键值参数，用于提供错误上下文，可为 {@code null}
     * @param indexArgs    索引参数，用于提供错误上下文，可为 {@code null}
     */
    public BaseError(@NonNull ErrorCode errorCode, String errorMessage, Map<String, Object> keyArgs, Object[] indexArgs) {
        this.errorCode = errorCode.code();
        this.errorMessage = errorMessage;
        this.keyArgs = Objects.nonNull(keyArgs) ? new HashMap<>(keyArgs) : null;
        this.indexArgs = Objects.nonNull(indexArgs) ? indexArgs.clone() : null;
    }

    /**
     * <p>基于自定义错误码构造 {@code BaseError}。</p>
     * <p><b>示例：</b></p>
     * <pre>{@code
     * Map<String, Object> keyArgs = new HashMap<>();
     * keyArgs.put("field", "username");
     * Object[] indexArgs = new Object[] {"JohnDoe"};
     * BaseError error = new BaseError("400", "Invalid username", keyArgs, indexArgs);
     * }</pre>
     *
     * @param errorCode    自定义错误码字符串
     * @param errorMessage 错误信息描述
     * @param keyArgs      键值参数，可为 {@code null}
     * @param indexArgs    索引参数，可为 {@code null}
     */
    BaseError(String errorCode, String errorMessage, Map<String, Object> keyArgs, Object[] indexArgs) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.keyArgs = Objects.nonNull(keyArgs) ? new HashMap<>(keyArgs) : null;
        this.indexArgs = Objects.nonNull(indexArgs) ? indexArgs.clone() : null;
    }

    /**
     * <p>获取不可修改的键值参数映射。</p>
     * <p><b>示例：</b></p>
     * <pre>{@code
     * Map<String, Object> args = error.getKeyArgs();
     * args.forEach((k, v) -> System.out.println(k + ": " + v));
     * }</pre>
     *
     * @return 键值参数映射，若无则返回 {@code null}
     */
    public Map<String, Object> getKeyArgs() {
        return Objects.nonNull(this.keyArgs) ? Collections.unmodifiableMap(this.keyArgs) : null;
    }

    /**
     * <p>获取索引参数数组。</p>
     * <p><b>示例：</b></p>
     * <pre>{@code
     * Object[] args = error.getIndexArgs();
     * for (Object arg : args) {
     *     System.out.println(arg);
     * }
     * }</pre>
     *
     * @return 索引参数数组，若无则返回 {@code null}
     */
    public Object[] getIndexArgs() {
        return Objects.nonNull(this.indexArgs) ? this.indexArgs.clone() : null;
    }
}
