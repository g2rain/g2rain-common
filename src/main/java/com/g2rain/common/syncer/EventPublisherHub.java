package com.g2rain.common.syncer;


import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Strings;

import java.util.Map;
import java.util.Objects;

/**
 * <p>事件发布中心，负责管理并分发事件消息到多个 {@link EventPublisher} 实例。</p>
 *
 * <p>内部维护一个线程安全的发布者集合，每个发布者通常对应一个消息通道（binding）。
 * 可以通过 {@code bindingName} 指定发送的目标通道，也可以在仅存在单个发布者时省略。</p>
 *
 * <p>发送时，如果绑定名称为空且存在唯一发布者，则使用该发布者发送事件；
 * 如果指定的绑定不存在或集合为空，则事件将被安全忽略，不抛出异常。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * Map<String, EventPublisher> publishers = Map.of(
 *     "userChannel", new DefaultEventPublisher(),
 *     "orderChannel", new DefaultEventPublisher()
 * );
 * EventPublisherHub hub = new EventPublisherHub(publishers);
 *
 * // 发送完整事件消息
 * EventMessage<String> message = new EventMessage<>("USER_SERVICE", EventType.CREATE, "{\"id\":1}");
 * hub.send("userChannel", message);
 *
 * // 使用简化方法发送事件
 * hub.sendUpdate("orderChannel", "ORDER_SERVICE", "{\"id\":101}");
 * hub.sendDelete("userChannel", "USER_SERVICE", "{\"id\":2}");
 * }</pre>
 *
 * @param publishers 注册的事件发布者集合，key 为通道名称（bindingName）
 */
public record EventPublisherHub(Map<String, EventPublisher> publishers) {

    /**
     * 将事件消息发送到指定的发布者绑定。
     *
     * <p>如果绑定名称为空且只有一个发布者，则使用该发布者发送；
     * 如果绑定不存在或发布者集合为空，则事件被忽略。</p>
     *
     * @param bindingName  消息绑定名称，可为空
     * @param eventMessage 待发送的事件消息
     * @param <V>          事件数据类型
     */
    public <V> void send(String bindingName, EventMessage<V> eventMessage) {
        if (Collections.isEmpty(this.publishers)) {
            return;
        }

        if (Strings.isBlank(bindingName)) {
            if (this.publishers.size() != 1) {
                return;
            }

            this.publishers.values().iterator().next().publish(eventMessage);
        }

        EventPublisher eventPublisher = this.publishers.get(bindingName);
        if (Objects.isNull(eventPublisher)) {
            return;
        }

        eventPublisher.publish(eventMessage);
    }

    /**
     * 构建事件消息并发送到指定的发布者绑定。
     *
     * @param bindingName 消息绑定名称
     * @param dataSource  数据源标识
     * @param eventType   事件类型
     * @param data        事件数据
     * @param <V>         事件数据类型
     */
    public <V> void send(String bindingName, String dataSource, EventType eventType, V data) {
        send(bindingName, new EventMessage<>(dataSource, eventType, data));
    }

    /**
     * 发送“创建”类型事件。
     *
     * @param bindingName 消息绑定名称
     * @param dataSource  数据源标识
     * @param data        事件数据
     * @param <V>         事件数据类型
     */
    public <V> void sendCreate(String bindingName, String dataSource, V data) {
        send(bindingName, dataSource, EventType.CREATE, data);
    }

    /**
     * 发送“更新”类型事件。
     *
     * @param bindingName 消息绑定名称
     * @param dataSource  数据源标识
     * @param data        事件数据
     * @param <V>         事件数据类型
     */
    public <V> void sendUpdate(String bindingName, String dataSource, V data) {
        send(bindingName, dataSource, EventType.UPDATE, data);
    }

    /**
     * 发送“删除”类型事件。
     *
     * @param bindingName 消息绑定名称
     * @param dataSource  数据源标识
     * @param data        事件数据
     * @param <V>         事件数据类型
     */
    public <V> void sendDelete(String bindingName, String dataSource, V data) {
        send(bindingName, dataSource, EventType.DELETE, data);
    }
}
