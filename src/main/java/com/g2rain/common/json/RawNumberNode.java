package com.g2rain.common.json;


import lombok.Getter;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.node.BigIntegerNode;
import tools.jackson.databind.node.DecimalNode;
import tools.jackson.databind.node.IntNode;
import tools.jackson.databind.node.LongNode;
import tools.jackson.databind.node.NumericNode;
import tools.jackson.databind.node.TreeBuildingGenerator;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * <p>{@code RawNumberNode} 是 Jackson 的自定义数字节点，保留数字的原始文本，避免精度丢失。</p>
 * <p>
 * 文本形式（{@link #asString()}、序列化输出、相等比较）一律使用原文；
 * 数值访问（{@link #numberValue()}、{@link #longValue()} 等）委托给按原文选定的 Jackson 标准数字节点：
 * 不含小数点与指数的整数按范围取 {@link IntNode}、{@link LongNode} 或 {@link BigIntegerNode}，其余取 {@link DecimalNode}。
 * 因此它与 Jackson 自带数字节点的行为一致，可被 {@code treeToValue}、{@code convertValue} 等正常读取。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * RawNumberNode id = new RawNumberNode("45360");
 * id.asString();        // "45360"
 * id.longValue();       // 45360L
 * id.isIntegralNumber(); // true
 *
 * RawNumberNode price = new RawNumberNode("12345.6789");
 * price.decimalValue(); // 12345.6789
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class RawNumberNode extends NumericNode {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数值的 BigDecimal 表示，保证高精度
     */
    @Getter
    private final BigDecimal numericValue;

    /**
     * 原始数字字符串表示
     */
    @Getter
    private final String rawValue;

    /**
     * 承担数值语义的 Jackson 标准数字节点
     */
    private final NumericNode numeric;

    /**
     * 构造函数。
     *
     * @param rawValue 原始数字字符串
     * @throws NumberFormatException 原文不是合法数字时抛出
     */
    public RawNumberNode(String rawValue) {
        this.numericValue = new BigDecimal(rawValue);
        this.rawValue = rawValue;
        this.numeric = numericOf(rawValue, this.numericValue);
    }

    /**
     * 按原文选定承担数值语义的标准数字节点。
     *
     * @param rawValue 原始数字字符串
     * @param value    数值
     * @return 整数取 Int / Long / BigInteger 节点，其余取 Decimal 节点
     */
    private static NumericNode numericOf(String rawValue, BigDecimal value) {
        if (rawValue.indexOf('.') >= 0 || rawValue.indexOf('e') >= 0 || rawValue.indexOf('E') >= 0) {
            return DecimalNode.valueOf(value);
        }

        BigInteger integer = value.toBigIntegerExact();
        if (integer.bitLength() < Integer.SIZE) {
            return IntNode.valueOf(integer.intValue());
        }

        if (integer.bitLength() < Long.SIZE) {
            return LongNode.valueOf(integer.longValue());
        }

        return BigIntegerNode.valueOf(integer);
    }

    /*
     * 文本形式：原文
     */

    /**
     * 当前节点对应的 JSON Token：整数为 {@link JsonToken#VALUE_NUMBER_INT}，其余为 {@link JsonToken#VALUE_NUMBER_FLOAT}。
     *
     * @return JSON Token
     */
    @Override
    public JsonToken asToken() {
        return numeric.asToken();
    }

    /**
     * 文本值，即原始数字字符串。
     *
     * @return 原始数字字符串
     */
    @Override
    protected String _asString() {
        return this.rawValue;
    }

    /**
     * 序列化：构建 JSON 树（如 {@code valueToTree}）时原样放入本节点；其余生成器以原文输出数字（不加引号）。
     * <p>树构建生成器不支持 {@code writeNumber(String)}，须按节点写入。</p>
     *
     * @param g   JSON 生成器
     * @param ctx 序列化上下文
     */
    @Override
    public void serialize(JsonGenerator g, SerializationContext ctx) {
        if (g instanceof TreeBuildingGenerator) {
            g.writeTree(this);
            return;
        }

        g.writeNumber(this.rawValue);
    }

    /**
     * 按原文比较是否相等。
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
     * 按原文计算哈希值。
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.rawValue);
    }

    /*
     * 类型判断与数值访问：委托标准数字节点
     */

    @Override
    public JsonParser.NumberType numberType() {
        return numeric.numberType();
    }

    @Override
    public boolean isIntegralNumber() {
        return numeric.isIntegralNumber();
    }

    @Override
    public boolean isFloatingPointNumber() {
        return numeric.isFloatingPointNumber();
    }

    @Override
    public boolean isInt() {
        return numeric.isInt();
    }

    @Override
    public boolean isLong() {
        return numeric.isLong();
    }

    @Override
    public boolean isBigInteger() {
        return numeric.isBigInteger();
    }

    @Override
    public boolean isBigDecimal() {
        return numeric.isBigDecimal();
    }

    @Override
    public Number numberValue() {
        return numeric.numberValue();
    }

    @Override
    public short shortValue() {
        return numeric.shortValue();
    }

    @Override
    public short shortValue(short defaultValue) {
        return numeric.shortValue(defaultValue);
    }

    @Override
    public Optional<Short> shortValueOpt() {
        return numeric.shortValueOpt();
    }

    @Override
    public short asShort() {
        return numeric.asShort();
    }

    @Override
    public short asShort(short defaultValue) {
        return numeric.asShort(defaultValue);
    }

    @Override
    public Optional<Short> asShortOpt() {
        return numeric.asShortOpt();
    }

    @Override
    public int intValue() {
        return numeric.intValue();
    }

    @Override
    public int intValue(int defaultValue) {
        return numeric.intValue(defaultValue);
    }

    @Override
    public OptionalInt intValueOpt() {
        return numeric.intValueOpt();
    }

    @Override
    public int asInt() {
        return numeric.asInt();
    }

    @Override
    public int asInt(int defaultValue) {
        return numeric.asInt(defaultValue);
    }

    @Override
    public OptionalInt asIntOpt() {
        return numeric.asIntOpt();
    }

    @Override
    public long longValue() {
        return numeric.longValue();
    }

    @Override
    public long longValue(long defaultValue) {
        return numeric.longValue(defaultValue);
    }

    @Override
    public OptionalLong longValueOpt() {
        return numeric.longValueOpt();
    }

    @Override
    public long asLong() {
        return numeric.asLong();
    }

    @Override
    public long asLong(long defaultValue) {
        return numeric.asLong(defaultValue);
    }

    @Override
    public OptionalLong asLongOpt() {
        return numeric.asLongOpt();
    }

    @Override
    public BigInteger bigIntegerValue() {
        return numeric.bigIntegerValue();
    }

    @Override
    public BigInteger bigIntegerValue(BigInteger defaultValue) {
        return numeric.bigIntegerValue(defaultValue);
    }

    @Override
    public Optional<BigInteger> bigIntegerValueOpt() {
        return numeric.bigIntegerValueOpt();
    }

    @Override
    public BigInteger asBigInteger() {
        return numeric.asBigInteger();
    }

    @Override
    public BigInteger asBigInteger(BigInteger defaultValue) {
        return numeric.asBigInteger(defaultValue);
    }

    @Override
    public Optional<BigInteger> asBigIntegerOpt() {
        return numeric.asBigIntegerOpt();
    }

    @Override
    public float floatValue() {
        return numeric.floatValue();
    }

    @Override
    public float floatValue(float defaultValue) {
        return numeric.floatValue(defaultValue);
    }

    @Override
    public Optional<Float> floatValueOpt() {
        return numeric.floatValueOpt();
    }

    @Override
    public float asFloat() {
        return numeric.asFloat();
    }

    @Override
    public float asFloat(float defaultValue) {
        return numeric.asFloat(defaultValue);
    }

    @Override
    public Optional<Float> asFloatOpt() {
        return numeric.asFloatOpt();
    }

    @Override
    public double doubleValue() {
        return numeric.doubleValue();
    }

    @Override
    public double doubleValue(double defaultValue) {
        return numeric.doubleValue(defaultValue);
    }

    @Override
    public OptionalDouble doubleValueOpt() {
        return numeric.doubleValueOpt();
    }

    @Override
    public double asDouble() {
        return numeric.asDouble();
    }

    @Override
    public double asDouble(double defaultValue) {
        return numeric.asDouble(defaultValue);
    }

    @Override
    public OptionalDouble asDoubleOpt() {
        return numeric.asDoubleOpt();
    }

    @Override
    public BigDecimal decimalValue() {
        return numeric.decimalValue();
    }

    @Override
    public BigDecimal decimalValue(BigDecimal defaultValue) {
        return numeric.decimalValue(defaultValue);
    }

    @Override
    public Optional<BigDecimal> decimalValueOpt() {
        return numeric.decimalValueOpt();
    }

    @Override
    public BigDecimal asDecimal() {
        return numeric.asDecimal();
    }

    @Override
    public BigDecimal asDecimal(BigDecimal defaultValue) {
        return numeric.asDecimal(defaultValue);
    }

    @Override
    public Optional<BigDecimal> asDecimalOpt() {
        return numeric.asDecimalOpt();
    }

    @Override
    public boolean canConvertToShort() {
        return numeric.canConvertToShort();
    }

    @Override
    public boolean canConvertToInt() {
        return numeric.canConvertToInt();
    }

    @Override
    public boolean canConvertToLong() {
        return numeric.canConvertToLong();
    }

    @Override
    public boolean hasFractionalPart() {
        return numeric.hasFractionalPart();
    }

    @Override
    public boolean inShortRange() {
        return numeric.inShortRange();
    }

    @Override
    public boolean inIntRange() {
        return numeric.inIntRange();
    }

    @Override
    public boolean inLongRange() {
        return numeric.inLongRange();
    }

    @Override
    public boolean isNaN() {
        return numeric.isNaN();
    }

    @Override
    public short _asShortValueUnchecked() {
        return numeric._asShortValueUnchecked();
    }

    @Override
    public int _asIntValueUnchecked() {
        return numeric._asIntValueUnchecked();
    }

    @Override
    public long _asLongValueUnchecked() {
        return numeric._asLongValueUnchecked();
    }
}
