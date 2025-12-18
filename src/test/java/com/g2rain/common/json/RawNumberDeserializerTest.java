package com.g2rain.common.json;


import com.g2rain.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.BooleanNode;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.StringNode;

import java.math.BigDecimal;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("原始数字反序列化器测试")
class RawNumberDeserializerTest {

    private RawNumberDeserializer deserializer;
    private JsonParser jsonParser;
    private DeserializationContext context;

    @BeforeEach
    void setUp() {
        deserializer = new RawNumberDeserializer();
        jsonParser = mock(JsonParser.class);
        context = mock(DeserializationContext.class);
    }

    @Test
    @DisplayName("测试构造函数")
    void testConstructor() {
        RawNumberDeserializer deserializer = new RawNumberDeserializer();
        assertNotNull(deserializer);
    }

    @Test
    @DisplayName("测试反序列化对象")
    void testDeserializeObject() {
        // 测试我们自己的反序列化器是否能正确处理对象类型
        when(jsonParser.currentToken()).thenReturn(JsonToken.START_OBJECT);
        when(jsonParser.nextToken()).thenReturn(JsonToken.END_OBJECT);

        JsonNode result = deserializer.deserialize(jsonParser, context);
        // 验证我们自己的反序列化器创建了正确的节点类型
        assertTrue(result instanceof ObjectNode);
        assertEquals(0, ((ObjectNode) result).size());
    }

    @Test
    @DisplayName("测试反序列化数组")
    void testDeserializeArray() {
        // 测试我们自己的反序列化器是否能正确处理数组类型
        when(jsonParser.currentToken()).thenReturn(JsonToken.START_ARRAY);
        when(jsonParser.nextToken()).thenReturn(JsonToken.END_ARRAY);

        JsonNode result = deserializer.deserialize(jsonParser, context);
        // 验证我们自己的反序列化器创建了正确的节点类型
        assertTrue(result instanceof ArrayNode);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("测试反序列化数字")
    void testDeserializeNumber() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_NUMBER_FLOAT);
        when(jsonParser.getValueAsString()).thenReturn("123.45");

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof RawNumberNode);
        assertEquals("123.45", result.asString());
        assertEquals(new BigDecimal("123.45"), ((RawNumberNode) result).getNumericValue());
    }

    @Test
    @DisplayName("测试反序列化字符串")
    void testDeserializeString() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_STRING);
        when(jsonParser.getValueAsString()).thenReturn("test");

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof StringNode);
        assertEquals("test", result.asString());
    }

    @Test
    @DisplayName("测试反序列化布尔值true")
    void testDeserializeBooleanTrue() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_TRUE);

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof BooleanNode);
        assertTrue(result.asBoolean());
    }

    @Test
    @DisplayName("测试反序列化布尔值false")
    void testDeserializeBooleanFalse() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_FALSE);

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof BooleanNode);
        assertFalse(result.asBoolean());
    }

    @Test
    @DisplayName("测试反序列化null值")
    void testDeserializeNull() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_NULL);

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof NullNode);
    }

    @Test
    @DisplayName("测试反序列化嵌入对象")
    void testDeserializeEmbeddedObject() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_EMBEDDED_OBJECT);
        when(jsonParser.getEmbeddedObject()).thenReturn("embedded".getBytes());

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof StringNode);
        assertEquals(Base64.getEncoder().encodeToString("embedded".getBytes()), result.asString());
    }

    @Test
    @DisplayName("测试反序列化NOT_AVAILABLE标记")
    void testDeserializeNotAvailable() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.NOT_AVAILABLE);

        assertThrows(BusinessException.class, () -> deserializer.deserialize(jsonParser, context));
    }

    @Test
    @DisplayName("测试反序列化未知标记")
    void testDeserializeUnknownToken() {
        when(jsonParser.currentToken()).thenReturn(JsonToken.VALUE_EMBEDDED_OBJECT);
        when(jsonParser.getEmbeddedObject()).thenReturn(new Object());

        JsonNode result = deserializer.deserialize(jsonParser, context);
        assertTrue(result instanceof StringNode);
    }
}
