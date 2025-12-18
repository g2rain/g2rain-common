package com.g2rain.common.json;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.g2rain.common.exception.FieldError;

import java.util.List;
import java.util.Map;

/**
 * Jackson Mixin，用于忽略序列化时的特定字段。
 * <p>
 * 该抽象类用于配合 {@link JsonCodec} 或 {@link tools.jackson.databind.json.JsonMapper}，
 * 在序列化对象时忽略 {@code keyArgs}、{@code indexArgs} 和 {@code data} 字段，
 * 避免这些字段出现在最终 JSON 输出中。
 * </p>
 *
 * <h2>功能</h2>
 * <ul>
 *     <li>忽略 keyArgs 字段</li>
 *     <li>忽略 indexArgs 字段</li>
 *     <li>忽略 data 字段</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * JsonMapper.builder().addMixIn(TargetClass.class, FailIgnoreFieldMixIn.class).build();
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/6
 */
public interface FailIgnoreFieldMixIn {

    /**
     * 忽略序列化 keyArgs 字段
     */
    @JsonIgnore
    Map<String, Object> getKeyArgs();

    /**
     * 忽略序列化 indexArgs 字段
     */
    @JsonIgnore
    Object[] getIndexArgs();

    /**
     * 忽略序列化 data 字段
     */
    @JsonIgnore
    Object getData();

    /**
     * 忽略序列化 fieldErrors 字段
     * <p>
     * 当通过 Jackson 序列化时，如果 fieldErrors 为 null 或空集合，该字段会被忽略。
     * 此注解依赖 Jackson 在序列化时调用 getter 方法，因此必须声明为抽象 getter 方法。
     * </p>
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<FieldError> getFieldErrors();
}
