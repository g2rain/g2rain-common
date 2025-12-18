package com.g2rain.common.syncer;


/**
 * <p>消息分发器接口，定义了对原始消息进行分发处理的方法。</p>
 *
 * <p>实现该接口的类负责接收原始消息字符串并根据业务逻辑进行解析、处理或路由。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * MessageDispatcher dispatcher = new DefaultMessageDispatcher();
 * String rawMessage = "{\"dataSource\":\"USER_SERVICE\",\"eventType\":\"CREATE\",\"data\":\"{}\"}";
 * dispatcher.dispatch(rawMessage);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public interface MessageDispatcher {
    /**
     * 分发原始消息。
     *
     * @param rawMessage 待分发的原始消息字符串
     */
    void dispatch(String rawMessage);
}
