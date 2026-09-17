package com.g2rain.common.json;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.jdk.UntypedObjectDeserializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

/**
 * {@link Object} 反序列化增强：字段值为 JSON 对象/数组字符串时，继续解析为 {@link Map} / {@link List}。
 *
 * <p>例：{@code "params": "{\"id\":1,\"name\":\"test\"}"} → {@code params} 为 Map，而非 String。
 * 数组元素同理：{@code ["{\"a\":1}"]} → List 内为 Map。
 *
 * <p>字符串内再嵌字符串 JSON 时，通过 {@link DeserializationContext#createParser}
 * 与 {@link DeserializationContext#readValue} 逐层展开。
 *
 * <p>普通文本（不匹配 {@code {}} / {@code []} 首尾）保持 {@link String}；形似 JSON 但解析失败亦退回原字符串。
 *
 * @author alpha
 * @since 2026/8/26
 */
public final class NestedJsonStrObjectDeserializer extends UntypedObjectDeserializer {

    /**
     * 供父类构造 {@link tools.jackson.databind.type.TypeFactory}，不参与嵌套字符串解析。
     */
    private static final JsonMapper TYPE_FACTORY_MAPPER = JsonMapper.builder().build();

    /**
     * 初始化无类型映射：JSON 对象 → Map，JSON 数组 → List。
     */
    public NestedJsonStrObjectDeserializer() {
        super(
            TYPE_FACTORY_MAPPER.getTypeFactory().constructCollectionType(List.class, Object.class),
            TYPE_FACTORY_MAPPER.getTypeFactory().constructMapType(Map.class, String.class, Object.class)
        );
    }

    /**
     * 反序列化单个 JSON 值：字符串 token 尝试展开为结构，其它 token 走父类默认逻辑。
     */
    @Override
    public Object deserialize(JsonParser parser, DeserializationContext context) throws JacksonException {
        // 对象、数组、数字、布尔等：UntypedObjectDeserializer 默认处理
        if (parser.currentToken() != JsonToken.VALUE_STRING) {
            return super.deserialize(parser, context);
        }

        String value = parser.getString();
        // 非 JSON 对象/数组形态的字符串，原样返回
        if (NestedJsonStringSupport.isPlainString(value)) {
            return value;
        }

        try {
            // 子 parser + 同一 context，内层字符串 JSON 继续走本 deserializer
            try (JsonParser nested = context.createParser(value.trim())) {
                return context.readValue(nested, Object.class);
            }
        } catch (Exception ignored) {
            // 解析失败：保留原字符串
            return value;
        }
    }
}
