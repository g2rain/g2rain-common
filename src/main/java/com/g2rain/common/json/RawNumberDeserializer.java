package com.g2rain.common.json;


import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.exception.SystemErrorCode;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.BooleanNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.StringNode;

import java.util.Base64;
import java.util.Objects;

/**
 * <p>{@code RawNumberDeserializer} 是 Jackson 自定义反序列化器，继承自 {@link StdDeserializer}。</p>
 * <p>
 * 用于反序列化 JSON 数据为 {@link JsonNode}，并保留原始数字格式（避免浮点精度丢失）。
 * 支持对象、数组、数字、字符串、布尔值、空值及嵌入对象的反序列化。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * JsonMapper mapper =JsonMapper.builder().build();
 * SimpleModule module = new SimpleModule();
 * module.addDeserializer(JsonNode.class, new RawNumberDeserializer());
 * mapper.registerModule(module);
 *
 * JsonNode node = mapper.readValue("{\"num\":123.45}", JsonNode.class);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class RawNumberDeserializer extends StdDeserializer<JsonNode> {

    /**
     * 默认构造方法。
     * 设置反序列化目标类型为 {@link JsonNode}。
     */
    public RawNumberDeserializer() {
        super(JsonNode.class);
    }

    /**
     * 自定义反序列化逻辑。
     * <p>
     * 根据当前 JSON Token 类型，递归反序列化为对应的 {@link JsonNode} 类型：
     * <ul>
     *     <li>{@link ObjectNode}：反序列化对象节点</li>
     *     <li>{@link ArrayNode}：反序列化数组节点</li>
     *     <li>{@link RawNumberNode}：保留原始数字格式</li>
     *     <li>{@link StringNode}：字符串值</li>
     *     <li>{@link BooleanNode}：布尔值</li>
     *     <li>{@link NullNode}：空值</li>
     * </ul>
     * </p>
     *
     * @param p   当前 JSON 解析器
     * @param ctx 反序列化上下文
     * @return 反序列化后的 {@link JsonNode}
     * @throws BusinessException 当遇到不可用的 Token 或序列化错误时抛出
     */
    @Override
    public JsonNode deserialize(JsonParser p, DeserializationContext ctx) {
        JsonToken token = p.currentToken();
        if (Objects.isNull(token)) {
            token = p.nextToken();
        }

        switch (token) {
            case START_OBJECT -> {
                ObjectNode objNode = JsonNodeFactory.instance.objectNode();
                while (p.nextToken() != JsonToken.END_OBJECT) {
                    String fieldName = p.currentName();
                    p.nextToken();
                    objNode.set(fieldName, deserialize(p, ctx));
                }
                return objNode;
            }
            case START_ARRAY -> {
                ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
                while (p.nextToken() != JsonToken.END_ARRAY) {
                    arrayNode.add(deserialize(p, ctx));
                }
                return arrayNode;
            }
            case VALUE_NUMBER_INT, VALUE_NUMBER_FLOAT -> {
                return new RawNumberNode(p.getValueAsString());
            }
            case VALUE_STRING -> {
                return StringNode.valueOf(p.getValueAsString());
            }
            case VALUE_TRUE -> {
                return BooleanNode.TRUE;
            }
            case VALUE_FALSE -> {
                return BooleanNode.FALSE;
            }
            case VALUE_NULL -> {
                return NullNode.instance;
            }
            case VALUE_EMBEDDED_OBJECT -> {
                Object embedded = p.getEmbeddedObject();
                if (Objects.isNull(embedded)) {
                    return NullNode.instance;
                }

                if (embedded instanceof byte[] bytes) {
                    return StringNode.valueOf(Base64.getEncoder().encodeToString(bytes));
                }

                return StringNode.valueOf(String.valueOf(embedded));
            }
            case NOT_AVAILABLE -> throw new BusinessException(SystemErrorCode.DESERIALIZER_NOT_AVAILABLE);
            default -> throw new BusinessException(SystemErrorCode.JSON_SERIALIZER_ERROR);
        }
    }
}
