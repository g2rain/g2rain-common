package com.g2rain.common.syncer;


import com.g2rain.common.utils.Collections;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>事件发布中心，管理并分发事件消息到多个 {@link EventPublisher} 实例。</p>
 *
 * <p>它维护一个线程安全的发布者集合，能够将事件广播给所有注册的发布者。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * Set<EventPublisher> publishers = Set.of(new DefaultEventPublisher());
 * EventPublisherHub hub = new EventPublisherHub(publishers);
 *
 * // 直接发送事件消息对象
 * EventMessage<String> message = new EventMessage<>("USER_SERVICE", EventType.CREATE, "{\"id\":1}");
 * hub.send(message);
 *
 * // 使用简化方法发送事件
 * hub.send("USER_SERVICE", EventType.UPDATE, "{\"id\":2}");
 * }</pre>
 *
 * @param publishers 注册的事件发布者集合
 * @author alpha
 * @since 2025/10/5
 */
public record EventPublisherHub(Set<EventPublisher> publishers) {

    /**
     * 构造函数，将传入的发布者集合封装为线程安全的 {@link CopyOnWriteArraySet}。
     *
     * @param publishers 事件发布者集合
     */
    public EventPublisherHub(Set<EventPublisher> publishers) {
        this.publishers = new CopyOnWriteArraySet<>(publishers);
    }

    /**
     * 发送事件消息给所有注册的发布者。
     *
     * @param eventMessage 待发送的事件消息
     * @param <V>          事件数据的类型
     */
    public <V> void send(EventMessage<V> eventMessage) {
        if (Collections.isEmpty(this.publishers)) {
            return;
        }

        for (EventPublisher publisher : this.publishers) {
            publisher.publish(eventMessage);
        }
    }

    /**
     * 简化发送事件的方法，通过数据源、事件类型和数据构建事件消息并发送。
     *
     * @param dataSource 数据源标识
     * @param eventType  事件类型
     * @param data       事件数据
     * @param <V>        事件数据的类型
     */
    public <V> void send(String dataSource, EventType eventType, V data) {
        send(new EventMessage<>(dataSource, eventType, data));
    }

    /**
     * 发送“创建”事件消息。
     *
     * @param dataSource 数据源标识
     * @param data       事件数据
     * @param <V>        事件数据的类型
     */
    public <V> void sendCreate(String dataSource, V data) {
        send(dataSource, EventType.CREATE, data);
    }

    /**
     * 发送“更新”事件消息。
     *
     * @param dataSource 数据源标识
     * @param data       事件数据
     * @param <V>        事件数据的类型
     */
    public <V> void sendUpdate(String dataSource, V data) {
        send(dataSource, EventType.UPDATE, data);
    }

    /**
     * 发送“删除”事件消息。
     *
     * @param dataSource 数据源标识
     * @param data       事件数据
     * @param <V>        事件数据的类型
     */
    public <V> void sendDelete(String dataSource, V data) {
        send(dataSource, EventType.DELETE, data);
    }
}
