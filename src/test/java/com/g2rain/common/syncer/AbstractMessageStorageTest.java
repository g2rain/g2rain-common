package com.g2rain.common.syncer;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("抽象消息存储测试")
class AbstractMessageStorageTest {

    @Test
    @DisplayName("测试构造函数和注册")
    void testConstructorAndRegistration() {
        // 创建一个具体的实现来测试抽象类
        TestMessageStorage storage = new TestMessageStorage();

        assertNotNull(storage);
        // 验证存储已被注册到注册表中
        assertTrue(MessageStorageRegistry.getMessageStorages().contains(storage));
    }

    @Test
    @DisplayName("测试load方法")
    void testLoad() {
        TestMessageStorage storage = new TestMessageStorage();
        // load方法应该可以正常执行
        assertDoesNotThrow(storage::load);
    }

    // 测试用的具体实现类
    static class TestMessageStorage extends AbstractMessageStorage<String, String, String> {
        @Override
        protected @NonNull String dataSource() {
            return "testDataSource";
        }

        @Override
        protected @NonNull Class<String> getValueType() {
            return String.class;
        }

        @Override
        protected @NonNull String getKey(@NonNull String value) {
            return "key";
        }

        @Override
        protected void create(@NonNull String key, String value) {
            // 空实现
        }

        @Override
        protected void delete(@NonNull String key) {
            // 空实现
        }

        @Override
        protected void update(@NonNull String key, String value) {
            // 空实现
        }

        @Override
        protected String get(@NonNull String key) {
            return "value";
        }
    }
}
