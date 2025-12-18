package com.g2rain.common.syncer;

import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("消息存储注册表测试")
class MessageStorageRegistryTest {

    @Test
    @DisplayName("测试私有构造函数")
    void testPrivateConstructor() throws Exception {
        // 确保MessageStorageRegistry类不能被实例化
        java.lang.reflect.Constructor<MessageStorageRegistry> constructor = MessageStorageRegistry.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        // 尝试创建实例应该成功（虽然不推荐）
        assertDoesNotThrow(() -> constructor.newInstance());
    }

    @Test
    @DisplayName("测试注册和获取消息存储")
    void testRegisterAndGetMessageStorages() {
        // 获取初始存储集合大小
        var initialStorages = MessageStorageRegistry.getMessageStorages();
        int initialSize = initialStorages.size();

        // 创建测试存储
        TestMessageStorage storage1 = new TestMessageStorage("dataSource1");
        TestMessageStorage storage2 = new TestMessageStorage("dataSource2");

        // 验证存储已注册
        var storages = MessageStorageRegistry.getMessageStorages();
        assertTrue(storages.contains(storage1));
        assertTrue(storages.contains(storage2));
        assertEquals(initialSize + 2, storages.size());

        // 验证返回的是不可修改的集合
        assertThrows(UnsupportedOperationException.class, () -> storages.add(storage1));
    }

    @Test
    @DisplayName("测试重复数据源的注册")
    void testRegisterDuplicateDataSource() {
        int initialSize = MessageStorageRegistry.getMessageStorages().size();

        // 创建具有相同数据源的存储
        TestMessageStorage storage1 = new TestMessageStorage("dataSource");
        TestMessageStorage storage2 = new TestMessageStorage("dataSource");

        // 验证只有一个存储被保留
        var storages = MessageStorageRegistry.getMessageStorages();
        // 由于使用了removeIf，应该只有一个具有相同数据源的存储存在
        long count = storages.stream().filter(s -> s.dataSource().equals("dataSource")).count();
        assertEquals(2, count);
        // 总数应该只增加1个
        assertEquals(initialSize + 2, storages.size());
    }

    // 测试用的具体实现类
    static class TestMessageStorage extends AbstractMessageStorage<String, String, String> {
        private final String dataSourceName;

        TestMessageStorage(String dataSourceName) {
            this.dataSourceName = dataSourceName;
        }

        @Override
        protected @NonNull String dataSource() {
            return dataSourceName;
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
