package com.g2rain.common.syncer;


import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>消息存储注册中心，负责管理系统中所有 {@link AbstractMessageStorage} 实例的注册与获取。</p>
 *
 * <p>使用该类可以统一管理不同数据源的消息存储，确保事件分发时能够找到对应的存储处理逻辑。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * AbstractMessageStorage<String, MyData, MyResult> storage = new MyMessageStorage();
 * MessageStorageRegistry.register(storage);
 *
 * Set<AbstractMessageStorage<?, ?, ?>> storages = MessageStorageRegistry.getMessageStorages();
 * storages.forEach(System.out::println);
 * }</pre>
 *
 * <p>注意：该类不可实例化，所有方法为静态方法。</p>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class MessageStorageRegistry {

    /**
     * 存储已注册的消息存储集合，线程安全
     */
    private static final Set<AbstractMessageStorage<?, ?, ?>> messageStorages = new CopyOnWriteArraySet<>();

    private MessageStorageRegistry() {
        // 私有构造，防止实例化
    }

    /**
     * 注册一个消息存储实例。如果已有相同数据源的存储，会被替换。
     *
     * @param messageStorage 待注册的消息存储实例
     * @param <K>            消息存储的键类型
     * @param <V>            消息存储的值类型
     * @param <T>            消息存储的返回类型
     */
    static <K, V, T> void register(AbstractMessageStorage<K, V, T> messageStorage) {
        if (Objects.isNull(messageStorage)) {
            return;
        }

        messageStorages.removeIf(s -> s.dataSource().equals(messageStorage.dataSource()));
        messageStorages.add(messageStorage);
    }

    /**
     * 获取所有已注册的消息存储实例。
     *
     * @return 不可修改的消息存储集合
     */
    @SuppressWarnings("java:S1452")
    public static Set<AbstractMessageStorage<?, ?, ?>> getMessageStorages() {
        return java.util.Collections.unmodifiableSet(messageStorages);
    }
}
