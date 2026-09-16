package com.g2rain.common.json;


import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.StringNode;

import java.util.Objects;

/**
 * {@link JsonNode} 反序列化增强：字符串 token 形似 JSON 对象/数组时展开为子树，否则为 {@link StringNode}。
 *
 * <p>供 {@code str2obj(..., JsonNode.class)} 使用；{@link Object} 路径见 {@link NestedJsonStrObjectDeserializer}。
 * 解析失败时退回原字符串节点。
 *
 * @author alpha
 * @since 2026/8/26
 */
public class NestedJsonStrNodeDeserializer extends RawNumberDeserializer {

    /**
     * 字符串 token 尝试二次解析；其它 token 走父类默认 {@link JsonNode} 逻辑。
     */
    @Override
    public JsonNode deserialize(JsonParser parser, DeserializationContext context) throws JacksonException {
        // 首 token 未就绪时先推进
        JsonToken token = parser.currentToken();
        if (Objects.isNull(token)) {
            token = parser.nextToken();
        }

        // 对象、数组、数字、布尔等：RawNumberDeserializer / 父类默认处理
        if (token != JsonToken.VALUE_STRING) {
            return super.deserialize(parser, context);
        }

        String value = parser.getString();
        // 非 JSON 对象/数组形态的字符串，包装为 StringNode
        if (NestedJsonStringSupport.isPlainString(value)) {
            return StringNode.valueOf(value);
        }

        try {
            // 子 parser + 同一 context，内层字符串 JSON 继续走 JsonNode 路径
            try (JsonParser nested = context.createParser(value.trim())) {
                return context.readValue(nested, JsonNode.class);
            }
        } catch (Exception ignored) {
            // 解析失败：保留原字符串节点
            return StringNode.valueOf(value);
        }
    }
}
