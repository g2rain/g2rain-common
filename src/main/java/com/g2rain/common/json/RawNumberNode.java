package com.g2rain.common.json;


import lombok.Getter;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.node.JsonNodeType;
import tools.jackson.databind.node.ValueNode;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>{@code RawNumberNode} 是 Jackson 的自定义 {@link ValueNode} 实现，</p>
 * <p>
 * 用于保留原始数字字符串格式，避免浮点数精度丢失。
 * 在反序列化时，可保持数字的原始表示，同时提供 {@link BigDecimal} 数值访问。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * RawNumberNode node = new RawNumberNode("12345.6789");
 * System.out.println(node.asString()); // 输出 "12345.6789"
 * System.out.println(node.getNumericValue()); // 输出 12345.6789 的 BigDecimal 值
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Getter
public class RawNumberNode extends ValueNode {

    /**
     * 数值的 BigDecimal 表示，保证高精度
     */
    private final BigDecimal numericValue;

    /**
     * 原始数字字符串表示
     */
    private final String rawValue;

    /**
     * 构造函数。
     *
     * @param rawValue 原始数字字符串
     */
    public RawNumberNode(String rawValue) {
        this.numericValue = new BigDecimal(rawValue);
        this.rawValue = rawValue;
    }

    /**
     * 获取当前节点对应的 JSON Token 类型。
     *
     * @return {@link JsonToken#VALUE_NUMBER_FLOAT}
     */
    @Override
    public JsonToken asToken() {
        return JsonToken.VALUE_NUMBER_FLOAT;
    }

    /**
     * 获取当前节点的类型。
     *
     * @return {@link JsonNodeType#NUMBER}
     */
    @Override
    public JsonNodeType getNodeType() {
        return JsonNodeType.NUMBER;
    }

    /**
     * 获取当前节点的文本值（原始数字字符串）。
     *
     * @return 原始数字字符串
     */
    @Override
    public String asString() {
        return this.rawValue;
    }

    /**
     * 序列化当前节点为 JSON。
     *
     * @param g   JSON 生成器
     * @param ctx 序列化上下文
     */
    @Override
    public void serialize(JsonGenerator g, SerializationContext ctx) {
        g.writeRawValue(this.rawValue);
    }

    /**
     * 返回原始数字字符串
     *
     * @return 原始数字字符串
     */
    @Override
    protected String _valueDesc() {
        return this.rawValue;
    }

    /**
     * 比较两个 {@code RawNumberNode} 对象是否相等。
     *
     * @param o 待比较对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawNumberNode that)) return false;
        return Objects.equals(rawValue, that.rawValue);
    }

    /**
     * 获取对象的哈希值。
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.rawValue);
    }
}
