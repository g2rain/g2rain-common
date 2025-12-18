package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("事件发布器中心测试")
class EventPublisherHubTest {

    @Test
    @DisplayName("测试构造函数")
    void testConstructor() {
        Set<EventPublisher> publishers = Set.of(mock(EventPublisher.class));
        EventPublisherHub hub = new EventPublisherHub(publishers);

        assertNotNull(hub);
    }

    @Test
    @DisplayName("测试带参数构造函数")
    void testConstructorWithPublishers() {
        EventPublisher publisher1 = mock(EventPublisher.class);
        EventPublisher publisher2 = mock(EventPublisher.class);
        Set<EventPublisher> publishers = Set.of(publisher1, publisher2);

        EventPublisherHub hub = new EventPublisherHub(publishers);

        assertNotNull(hub);
    }

    @Test
    @DisplayName("测试发送事件消息")
    void testSendEventMessage() {
        EventPublisher publisher = mock(EventPublisher.class);
        Set<EventPublisher> publishers = Set.of(publisher);
        EventPublisherHub hub = new EventPublisherHub(publishers);

        EventMessage<String> eventMessage = new EventMessage<>("test", EventType.CREATE, "data");
        hub.send(eventMessage);

        // 验证发布者被调用
        verify(publisher).publish(eventMessage);
    }

    @Test
    @DisplayName("测试发送事件参数")
    void testSendEventParameters() {
        EventPublisher publisher = mock(EventPublisher.class);
        Set<EventPublisher> publishers = Set.of(publisher);
        EventPublisherHub hub = new EventPublisherHub(publishers);

        hub.send("test", EventType.CREATE, "data");

        // 验证发布者被调用
        verify(publisher).publish(any(EventMessage.class));
    }

    @Test
    @DisplayName("测试空发布者集合")
    void testEmptyPublishers() {
        Set<EventPublisher> publishers = Set.of();
        EventPublisherHub hub = new EventPublisherHub(publishers);

        EventMessage<String> eventMessage = new EventMessage<>("test", EventType.CREATE, "data");
        assertDoesNotThrow(() -> hub.send(eventMessage));
    }
}
