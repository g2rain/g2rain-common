package com.g2rain.common.syncer;


import lombok.NonNull;

/**
 * <p>抽象消息存储器基类，定义了消息存储的基本操作模板，供不同存储实现继承。</p>
 *
 * <p>子类需要实现具体的数据源标识、数据类型、键的获取方法以及增删改查等操作。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * public class MyMessageStorage extends AbstractMessageStorage<String, MyMessage, MyMessage> {
 *     @Override
 *     protected @NonNull String dataSource() {
 *         return "myMessageStore";
 *     }
 *
 *     @Override
 *     protected @NonNull Class<MyMessage> getValueType() {
 *         return MyMessage.class;
 *     }
 *
 *     @Override
 *     protected @NonNull String getKey(@NonNull MyMessage value) {
 *         return value.getId();
 *     }
 *
 *     @Override
 *     protected void create(@NonNull String key, MyMessage value) {
 *         // 实现创建逻辑
 *     }
 *
 *     @Override
 *     protected void delete(@NonNull String key) {
 *         // 实现删除逻辑
 *     }
 *
 *     @Override
 *     protected void update(@NonNull String key, MyMessage value) {
 *         // 实现更新逻辑
 *     }
 *
 *     @Override
 *     protected MyMessage get(@NonNull String key) {
 *         // 实现获取逻辑
 *         return null;
 *     }
 * }
 * }</pre>
 *
 * @param <K> 消息键类型
 * @param <V> 消息值类型
 * @param <T> 消息返回类型
 * @author alpha
 * @since 2025/10/5
 */
public abstract class AbstractMessageStorage<K, V, T> {
    /**
     * 构造方法，注册当前消息存储实现到 {@link MessageStorageRegistry}
     */
    protected AbstractMessageStorage() {
        MessageStorageRegistry.register(this);
    }

    /**
     * 加载所有数据，通常由子类实现具体加载逻辑。
     */
    public void load() {

    }

    /**
     * 获取数据源标识，用于区分不同存储实现。
     *
     * @return 数据源标识，不能为 {@code null}
     */
    protected abstract @NonNull String dataSource();

    /**
     * 获取存储值的类型，用于序列化和反序列化。
     *
     * @return 值类型的 {@link Class} 对象，不能为 {@code null}
     */
    protected abstract @NonNull Class<V> getValueType();

    /**
     * 从值对象中获取对应的键。
     *
     * @param value 值对象，不能为 {@code null}
     * @return 键对象，不能为 {@code null}
     */
    protected abstract @NonNull K getKey(@NonNull V value);

    /**
     * 创建一条消息记录。
     *
     * @param key   消息键，不能为 {@code null}
     * @param value 消息值
     */
    protected abstract void create(@NonNull K key, V value);

    /**
     * 删除指定键的消息记录。
     *
     * @param key 消息键，不能为 {@code null}
     */
    protected abstract void delete(@NonNull K key);

    /**
     * 更新指定键的消息记录。
     *
     * @param key   消息键，不能为 {@code null}
     * @param value 消息值
     */
    protected abstract void update(@NonNull K key, V value);

    /**
     * 获取指定键的消息记录。
     *
     * @param key 消息键，不能为 {@code null}
     * @return 对应的消息记录，可能为 {@code null}
     */
    protected abstract T get(@NonNull K key);
}
