package com.g2rain.common.json;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g2rain.common.exception.FieldError;

import java.util.List;
import java.util.Map;

/**
 * Jackson Mixin，用于在序列化成功响应时忽略特定字段。
 * <p>
 * 该抽象类用于配合 {@link JsonCodec} 或 {@link tools.jackson.databind.json.JsonMapper}，
 * 在序列化对象时忽略以下字段，以简化成功响应的 JSON 输出。
 * </p>
 *
 * <h2>功能</h2>
 * <ul>
 *     <li>忽略 errorCode 字段</li>
 *     <li>忽略 errorMessage 字段</li>
 *     <li>忽略 keyArgs 字段</li>
 *     <li>忽略 indexArgs 字段</li>
 *     <li>忽略 fieldErrors 字段</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * JsonMapper.builder().addMixIn(TargetClass.class, SuccessIgnoreFieldMixIn.class).build();
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/6
 */
public interface SuccessIgnoreFieldMixIn {

    /**
     * 忽略序列化 errorCode 字段
     */
    @JsonIgnore
    String getErrorCode();

    /**
     * 忽略序列化 errorMessage 字段
     */
    @JsonIgnore
    String getErrorMessage();

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
     * 忽略序列化 fieldErrors 字段
     */
    @JsonIgnore
    List<FieldError> getFieldErrors();
}
