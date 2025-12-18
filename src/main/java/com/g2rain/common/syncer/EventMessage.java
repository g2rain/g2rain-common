package com.g2rain.common.syncer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>事件消息封装类，用于在系统中传递事件数据。</p>
 *
 * <p>包含事件的来源、类型以及对应的数据内容。</p>
 *
 * <p><b>类型参数：</b><br>
 * <code>V</code> — 事件数据的类型。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * EventMessage<String> message = new EventMessage<>();
 * message.setDataSource("USER_SERVICE");
 * message.setEventType(EventType.CREATE);
 * message.setData("{\"id\":1,\"name\":\"测试\"}");
 * }</pre>
 *
 * @param <V> 事件数据的类型
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage<V> {
    /**
     * 数据源标识，通常用于标识事件的来源系统或模块
     */
    private String dataSource;

    /**
     * 事件类型（创建、更新、删除等）
     */
    private EventType eventType;

    /**
     * 事件数据内容
     */
    private V data;
}
