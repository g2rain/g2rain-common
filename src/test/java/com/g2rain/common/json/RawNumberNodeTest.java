package com.g2rain.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
