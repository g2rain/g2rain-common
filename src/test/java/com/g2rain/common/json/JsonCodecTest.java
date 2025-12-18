package com.g2rain.common.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("JSON编解码器测试")
class JsonCodecTest {

    private JsonCodec jsonCodec;

    @BeforeEach
    void setUp() {
        jsonCodec = new JsonCodec(JsonMapper.builder().build());
    }

    @Test
    @DisplayName("测试对象转字符串")
    void testObj2str() {
        // 测试null值
        assertNull(jsonCodec.obj2str(null));

        // 测试字符串
        assertEquals("test", jsonCodec.obj2str("test"));

        // 测试对象
        TestData data = new TestData("test", 123);
        String json = jsonCodec.obj2str(data);
        assertTrue(json.contains("\"name\":\"test\""));
        assertTrue(json.contains("\"value\":123"));
    }

    @Test
    @DisplayName("测试对象转字节数组")
    void testObj2byte() {
        // 测试null值
        assertArrayEquals(new byte[0], jsonCodec.obj2byte(null));

        // 测试字符串
        byte[] bytes = jsonCodec.obj2byte("test");
        assertArrayEquals("test".getBytes(), bytes);

        // 测试对象
        TestData data = new TestData("test", 123);
        byte[] objBytes = jsonCodec.obj2byte(data);
        assertNotNull(objBytes);
        assertTrue(objBytes.length > 0);
    }

    @Test
    @DisplayName("测试字符串转对象")
    void testStr2obj() {
        // 测试null和空字符串
        assertNull(jsonCodec.str2obj(null, TestData.class));
        assertNull(jsonCodec.str2obj("", TestData.class));
        assertNull(jsonCodec.str2obj(" ", TestData.class));

        // 测试正常转换
        String json = "{\"name\":\"test\",\"value\":123}";
        TestData data = jsonCodec.str2obj(json, TestData.class);
        assertNotNull(data);
        assertEquals("test", data.getName());
        assertEquals(123, data.getValue());
    }

    @Test
    @DisplayName("测试字节数组转对象")
    void testByte2obj() {
        // 测试null和空数组
        assertNull(jsonCodec.byte2obj(null, new tools.jackson.core.type.TypeReference<TestData>() {
        }));
        assertNull(jsonCodec.byte2obj(new byte[0], new tools.jackson.core.type.TypeReference<TestData>() {
        }));

        // 测试正常转换
        String json = "{\"name\":\"test\",\"value\":123}";
        TestData data = jsonCodec.byte2obj(json.getBytes(), new tools.jackson.core.type.TypeReference<>() {
        });
        assertNotNull(data);
        assertEquals("test", data.getName());
        assertEquals(123, data.getValue());
    }

    @Test
    @DisplayName("测试字节数组转JsonNode")
    void testByte2node() {
        // 测试null和空数组
        assertNull(jsonCodec.byte2node(null));
        assertNull(jsonCodec.byte2node(new byte[0]));

        // 测试正常转换
        String json = "{\"name\":\"test\",\"value\":123}";
        tools.jackson.databind.JsonNode node = jsonCodec.byte2node(json.getBytes());
        assertNotNull(node);
        assertTrue(node.has("name"));
        assertTrue(node.has("value"));
        assertEquals("test", node.get("name").asString());
        assertEquals(123, node.get("value").asInt());
    }

    @Test
    @DisplayName("测试查找JsonNode")
    void testLookupNode() {
        String json = "{\"level1\":{\"level2\":{\"name\":\"test\"}}}";
        tools.jackson.databind.JsonNode node = jsonCodec.byte2node(json.getBytes());

        // 正常查找
        tools.jackson.databind.JsonNode found = jsonCodec.lookupNode(node, "level1.level2.name");
        assertNotNull(found);
        assertEquals("test", found.asString());

        // 查找不存在的路径
        tools.jackson.databind.JsonNode notFound = jsonCodec.lookupNode(node, "level1.level2.notexist");
        assertNull(notFound);

        // 查找空路径
        tools.jackson.databind.JsonNode sameNode = jsonCodec.lookupNode(node, null);
        assertEquals(node, sameNode);
    }

    @Test
    @DisplayName("测试JsonNode转文本")
    void testAsString() {
        String json = "{\"name\":\"test\"}";
        tools.jackson.databind.JsonNode node = jsonCodec.byte2node(json.getBytes());

        assertEquals("test", jsonCodec.asString(node.get("name")));
        assertNull(jsonCodec.asString(null));
    }

    @Test
    @DisplayName("测试JsonNode转基本类型")
    void testAsBasicTypes() {
        String json = "{\"bool\":true,\"int\":123,\"double\":123.45,\"long\":123456789}";
        tools.jackson.databind.JsonNode node = jsonCodec.byte2node(json.getBytes());

        assertTrue(jsonCodec.asBoolean(node.get("bool")));
        assertEquals(123, jsonCodec.asInt(node.get("int")));
        assertEquals(123.45, jsonCodec.asDouble(node.get("double")));
        assertEquals(123456789L, jsonCodec.asLong(node.get("long")));

        // 测试null值
        assertFalse(jsonCodec.asBoolean(null));
        assertEquals(0, jsonCodec.asInt(null));
        assertEquals(0.0, jsonCodec.asDouble(null));
        assertEquals(0L, jsonCodec.asLong(null));
    }

    // 测试用的内部类
    public static class TestData {
        private String name;
        private int value;

        public TestData() {
        }

        public TestData(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }
}
