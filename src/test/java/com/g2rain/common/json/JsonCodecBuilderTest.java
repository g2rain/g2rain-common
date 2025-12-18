package com.g2rain.common.json;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("JSON编解码器构建器测试")
class JsonCodecBuilderTest {

    @Test
    @DisplayName("测试默认构建器")
    void testDefaultBuilder() {
        JsonCodec codec = JsonCodecBuilder.builder().build();
        assertNotNull(codec);
        assertNotNull(codec.jsonMapper);
    }

    @Test
    @DisplayName("测试自定义JsonMapper构建器")
    void testCustomJsonMapperBuilder() {
        JsonCodec codec = JsonCodecBuilder.builder().withConfig(mapper -> {
            // 这里可以添加自定义配置
        }).build();
        assertNotNull(codec);
        assertNotNull(codec.jsonMapper);
    }

    @Test
    @DisplayName("测试默认配置")
    void testWithDefaults() {
        JsonCodec codec = JsonCodecBuilder.builder().withDefaults().build();
        assertNotNull(codec);
        assertNotNull(codec.jsonMapper);
    }
}
