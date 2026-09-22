package com.g2rain.common.json;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

/**
 * Jackson 3 空值序列化器：字段为 {@code null} 时写出 {@code ""}，而非 JSON {@code null}。
 *
 * <p>配合 {@code @JsonSerialize(nullsUsing = EmptyStringSerializer.class)} 使用，
 * 用于 SSE / 对外契约要求 key 恒在、空值用默认值占位的场景。
 *
 * @author alpha
 * @since 2026/8/27
 */
public final class EmptyStringSerializer extends ValueSerializer<Object> {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializationContext ctx) {
        gen.writeString("");
    }
}
