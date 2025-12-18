package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("事件消息测试")
class EventMessageTest {

    @Test
    @DisplayName("测试无参构造函数")
    void testNoArgsConstructor() {
        EventMessage<String> message = new EventMessage<>();

        assertNull(message.getDataSource());
        assertNull(message.getEventType());
        assertNull(message.getData());
    }

    @Test
    @DisplayName("测试全参构造函数")
    void testAllArgsConstructor() {
        EventMessage<String> message = new EventMessage<>("test-source", EventType.CREATE, "test-data");

        assertEquals("test-source", message.getDataSource());
        assertEquals(EventType.CREATE, message.getEventType());
        assertEquals("test-data", message.getData());
    }

    @Test
    @DisplayName("测试属性设置和获取")
    void testSetAndGetProperties() {
        EventMessage<String> message = new EventMessage<>();

        message.setDataSource("test-source");
        message.setEventType(EventType.CREATE);
        message.setData("test-data");

        assertEquals("test-source", message.getDataSource());
        assertEquals(EventType.CREATE, message.getEventType());
        assertEquals("test-data", message.getData());
    }
}
