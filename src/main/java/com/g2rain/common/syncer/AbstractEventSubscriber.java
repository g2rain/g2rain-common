package com.g2rain.common.syncer;


import lombok.Setter;

/**
 * <p>抽象事件订阅者基类，提供默认的消息分发器注入机制，所有事件订阅者应继承此类以获得消息分发能力。</p>
 *
 * <p>子类继承此类后，可以使用 {@link #dispatcher} 发送或接收事件消息，无需重复配置消息分发器。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * public class MySubscriber extends AbstractEventSubscriber {
 *     public MySubscriber() {
 *         super(); // 使用默认消息分发器
 *     }
 *
 *     public void handleEvent(Event event) {
 *         dispatcher.dispatch(event);
 *     }
 * }
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
public abstract class AbstractEventSubscriber {
    /**
     * 消息分发器，用于分发和处理事件
     */
    protected MessageDispatcher dispatcher;

    /**
     * 默认构造方法，使用 {@link DefaultMessageDispatcher} 作为消息分发器。
     */
    protected AbstractEventSubscriber() {
        this(new DefaultMessageDispatcher());
    }

    /**
     * 构造方法，可指定自定义的 {@link MessageDispatcher}。
     *
     * @param dispatcher 自定义的消息分发器
     */
    protected AbstractEventSubscriber(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
