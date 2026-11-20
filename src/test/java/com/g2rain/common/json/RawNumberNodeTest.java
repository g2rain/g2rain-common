package com.g2rain.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("原始数字节点测试")
class RawNumberNodeTest {

    @Test
    @DisplayName("测试构造函数和基本属性")
    void testConstructorAndGetters() {
        RawNumberNode node = new RawNumberNode("123.45");

        assertEquals("123.45", node.getRawValue());
        assertEquals(new java.math.BigDecimal("123.45"), node.getNumericValue());
    }

    @Test
    @DisplayName("测试asToken方法")
    void testAsToken() {
        RawNumberNode node = new RawNumberNode("123.45");
        assertEquals(tools.jackson.core.JsonToken.VALUE_NUMBER_FLOAT, node.asToken());
    }

    @Test
    @DisplayName("测试getNodeType方法")
    void testGetNodeType() {
        RawNumberNode node = new RawNumberNode("123.45");
        assertEquals(tools.jackson.databind.node.JsonNodeType.NUMBER, node.getNodeType());
    }

    @Test
    @DisplayName("测试asString方法")
    void testAsString() {
        RawNumberNode node = new RawNumberNode("123.45");
        assertEquals("123.45", node.asString());
    }

    @Test
    @DisplayName("测试equals和hashCode方法")
    void testEqualsAndHashCode() {
        RawNumberNode node1 = new RawNumberNode("123.45");
        RawNumberNode node2 = new RawNumberNode("123.45");
        RawNumberNode node3 = new RawNumberNode("123.46");

        assertEquals(node1, node2);
        assertNotEquals(node1, node3);
        assertEquals(node1.hashCode(), node2.hashCode());
        assertNotEquals(node1.hashCode(), node3.hashCode());
    }

    @Test
    @DisplayName("测试toString表示")
    void testToString() {
        RawNumberNode node = new RawNumberNode("123.45");
        assertNotNull(node.toString());
    }

    @Test
    @DisplayName("测试整数原文的数值访问")
    void testIntegralNumericAccess() {
        RawNumberNode node = new RawNumberNode("45360");

        assertEquals(45360, node.numberValue().intValue());
        assertEquals(45360, node.intValue());
        assertEquals(45360L, node.longValue());
        assertEquals(new java.math.BigDecimal("45360"), node.decimalValue());
        assertTrue(node.isIntegralNumber());
        assertTrue(node.canConvertToLong());
        assertEquals(tools.jackson.core.JsonToken.VALUE_NUMBER_INT, node.asToken());
        assertEquals("45360", node.asString());
    }

    @Test
    @DisplayName("测试超出 long 范围的整数")
    void testBigIntegerNumericAccess() {
        RawNumberNode node = new RawNumberNode("123456789012345678901234567890");

        assertEquals(new java.math.BigInteger("123456789012345678901234567890"), node.bigIntegerValue());
        assertTrue(node.isBigInteger());
        assertFalse(node.canConvertToLong());
    }

    @Test
    @DisplayName("测试小数原文的数值访问")
    void testDecimalNumericAccess() {
        RawNumberNode node = new RawNumberNode("123.45");

        assertEquals(new java.math.BigDecimal("123.45"), node.numberValue());
        assertEquals(123.45, node.doubleValue());
        assertTrue(node.isFloatingPointNumber());
        assertFalse(node.isIntegralNumber());
        assertTrue(node.hasFractionalPart());
    }

    @Test
    @DisplayName("测试解析、序列化与对象转换")
    void testParseSerializeAndConvert() {
        JsonCodec codec = JsonCodecBuilder.builder()
            .withConfig(builder -> builder.addModule(new NestedJsonStringModule()))
            .withDefaults().build();
        tools.jackson.databind.JsonNode tree = codec.str2obj("{\"id\":45360,\"price\":1.50}", tools.jackson.databind.JsonNode.class);

        assertInstanceOf(RawNumberNode.class, tree.get("id"));
        assertEquals(45360L, tree.get("id").numberValue().longValue());
        assertEquals("{\"id\":45360,\"price\":1.50}", codec.obj2str(tree));

        java.util.Map<String, Object> map = codec.convert(tree, new tools.jackson.core.type.TypeReference<>() {
        });
        assertEquals(45360, ((Number) map.get("id")).intValue());
        assertEquals(0, new java.math.BigDecimal("1.50").compareTo(new java.math.BigDecimal(map.get("price").toString())));
    }
}
