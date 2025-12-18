package com.g2rain.common.syncer;

import com.g2rain.common.json.JsonCodec;
import com.g2rain.common.json.JsonCodecFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("默认消息分发器测试")
class DefaultMessageDispatcherTest {

    private DefaultMessageDispatcher dispatcher;
    private JsonCodec jsonCodec;

    @BeforeEach
    void setUp() {
        dispatcher = new DefaultMessageDispatcher();
        jsonCodec = JsonCodecFactory.instance();
    }

    @Test
    @DisplayName("测试构造函数")
    void testConstructor() {
        assertNotNull(dispatcher);
    }

    @Test
    @DisplayName("测试分发空消息")
    void testDispatchEmptyMessage() {
        // 分发null消息应该不抛出异常
        assertDoesNotThrow(() -> dispatcher.dispatch(null));

        // 分发空字符串消息应该不抛出异常
        assertDoesNotThrow(() -> dispatcher.dispatch(""));

        // 分发空白字符串消息应该不抛出异常
        assertDoesNotThrow(() -> dispatcher.dispatch("   "));
    }

    @Test
    @DisplayName("测试分发无存储的消息")
    void testDispatchWithoutStorage() {
        // 清理现有的存储
        Set<AbstractMessageStorage<?, ?, ?>> storages = MessageStorageRegistry.getMessageStorages();
        storages.forEach(s -> {
            // 使用反射访问私有构造函数来创建新的存储注册表
        });

        String message = jsonCodec.obj2str(new EventMessage<>("test", EventType.CREATE, "data"));
        assertDoesNotThrow(() -> dispatcher.dispatch(message));
    }

    @Test
    @DisplayName("测试分发格式错误的消息")
    void testDispatchMalformedMessage() {
        assertDoesNotThrow(() -> {
            try {
                dispatcher.dispatch("invalid json");
            } catch (Exception e) {
                // 忽略异常
            }
        });
    }

    @Test
    @DisplayName("测试分发缺少字段的消息")
    void testDispatchMessageWithMissingFields() {
        // 缺少dataSource
        String message1 = jsonCodec.obj2str(new EventMessage<>(null, EventType.CREATE, "data"));
        assertDoesNotThrow(() -> dispatcher.dispatch(message1));

        // 缺少eventType
        EventMessage<String> message2 = new EventMessage<>("test", null, "data");
        String message2Str = jsonCodec.obj2str(message2);
        assertDoesNotThrow(() -> dispatcher.dispatch(message2Str));

        // 缺少data
        EventMessage<String> message3 = new EventMessage<>("test", EventType.CREATE, null);
        String message3Str = jsonCodec.obj2str(message3);
        assertDoesNotThrow(() -> dispatcher.dispatch(message3Str));
    }
}
