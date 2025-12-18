package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("事件发布器接口测试")
class EventPublisherTest {

    @Test
    @DisplayName("测试事件发布器接口定义")
    void testEventPublisherInterface() {
        // 创建一个简单的实现来测试接口
        EventPublisher publisher = new EventPublisher() {
            @Override
            public <V> void publish(EventMessage<V> eventMessage) {
                // 空实现，仅用于测试接口定义
                assertNotNull(eventMessage);
            }
        };

        EventMessage<String> message = new EventMessage<>("test", EventType.CREATE, "data");
        assertDoesNotThrow(() -> publisher.publish(message));
    }
}
