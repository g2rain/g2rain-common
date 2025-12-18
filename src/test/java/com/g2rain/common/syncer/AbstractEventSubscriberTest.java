package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@DisplayName("抽象事件订阅者测试")
class AbstractEventSubscriberTest {

    @Test
    @DisplayName("测试默认构造函数")
    void testDefaultConstructor() {
        AbstractEventSubscriber subscriber = new AbstractEventSubscriber() {
        };

        assertNotNull(subscriber);
        assertNotNull(subscriber.dispatcher);
        assertTrue(subscriber.dispatcher instanceof DefaultMessageDispatcher);
    }

    @Test
    @DisplayName("测试带参数构造函数")
    void testConstructorWithDispatcher() {
        MessageDispatcher mockDispatcher = mock(MessageDispatcher.class);
        AbstractEventSubscriber subscriber = new AbstractEventSubscriber(mockDispatcher) {
        };

        assertNotNull(subscriber);
        assertEquals(mockDispatcher, subscriber.dispatcher);
    }
}
