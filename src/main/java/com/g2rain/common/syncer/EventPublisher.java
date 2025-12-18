package com.g2rain.common.syncer;


/**
 * <p>事件发布者接口，定义事件消息发布的能力。</p>
 *
 * <p>实现该接口的类应负责将事件消息发送到相应的消息处理机制或总线中。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * EventPublisher publisher = new DefaultEventPublisher();
 * EventMessage<String> message = new EventMessage<>("USER_SERVICE", EventType.CREATE, "{\"id\":1}");
 * publisher.publish(message);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public interface EventPublisher {
    /**
     * 发布事件消息。
     *
     * @param eventMessage 待发布的事件消息
     * @param <V>          事件数据的类型
     */
    <V> void publish(EventMessage<V> eventMessage);
}
