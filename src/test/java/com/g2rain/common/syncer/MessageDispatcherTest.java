package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("消息分发器接口测试")
class MessageDispatcherTest {

    @Test
    @DisplayName("测试消息分发器接口定义")
    void testMessageDispatcherInterface() {
        // 创建一个简单的实现来测试接口
        MessageDispatcher dispatcher = new MessageDispatcher() {
            @Override
            public void dispatch(String rawMessage) {
                // 空实现，仅用于测试接口定义
                assertNotNull(rawMessage);
            }
        };

        assertDoesNotThrow(() -> dispatcher.dispatch("test message"));
    }
}
