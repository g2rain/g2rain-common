package com.g2rain.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("消息解析器测试")
class MessageResolverTest {

    @Test
    @DisplayName("测试通过索引解析消息模板")
    void testResolveByIndex() {
        String template = "Hello {0:name}, you are {1:age} years old";
        String result = MessageResolver.resolveByIndex(template, "John", "25");
        assertEquals("Hello John, you are 25 years old", result);
    }

    @Test
    @DisplayName("测试通过键值解析消息模板")
    void testResolveByKey() {
        String template = "Hello {0:name}, you are {1:age} years old";
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        params.put("name", "John");
        params.put("age", 25);
        String result = MessageResolver.resolveByKey(template, params);
        assertEquals("Hello John, you are 25 years old", result);
    }

    @Test
    @DisplayName("测试解析空模板")
    void testResolveEmptyTemplate() {
        String result = MessageResolver.resolveByIndex("", "arg1");
        assertEquals("", result);

        result = MessageResolver.resolveByKey("", new java.util.HashMap<>());
        assertEquals("", result);
    }

    @Test
    @DisplayName("测试解析无占位符模板")
    void testResolveTemplateWithoutPlaceholders() {
        String template = "Hello World";
        String result = MessageResolver.resolveByIndex(template, "arg1");
        assertEquals("Hello World", result);

        result = MessageResolver.resolveByKey(template, new java.util.HashMap<>());
        assertEquals("Hello World", result);
    }

    @Test
    @DisplayName("测试解析带有缺失参数的模板")
    void testResolveWithMissingParameters() {
        String template = "Hello {0:name}, you are {1:age} years old";
        String result = MessageResolver.resolveByIndex(template, "John");
        assertEquals("Hello John, you are {1:age} years old", result);

        template = "Hello {0:name}, you are {1:age} years old";
        String result2 = MessageResolver.resolveByKey(template, java.util.Map.of("name", "John"));
        assertEquals("Hello John, you are {1:age} years old", result2);
    }
}
