package com.g2rain.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("JSON编解码器工厂测试")
class JsonCodecFactoryTest {

    @Test
    @DisplayName("测试私有构造函数")
    void testPrivateConstructor() throws Exception {
        // 确保JsonCodecFactory类不能被实例化
        java.lang.reflect.Constructor<JsonCodecFactory> constructor = JsonCodecFactory.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        // 尝试创建实例应该成功（虽然不推荐）
        assertDoesNotThrow(() -> constructor.newInstance());
    }

    @Test
    @DisplayName("测试获取单例实例")
    void testInstance() {
        JsonCodec codec1 = JsonCodecFactory.instance();
        JsonCodec codec2 = JsonCodecFactory.instance();

        assertNotNull(codec1);
        assertNotNull(codec2);
        assertSame(codec1, codec2); // 确保是单例
    }
}
