package com.g2rain.common.syncer;


/**
 * <p>事件类型枚举，表示事件的操作类型。</p>
 *
 * <p>常用的事件类型包括创建、删除和更新，用于事件驱动系统中区分不同的事件操作。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * EventType type = EventType.CREATE;
 * if (type == EventType.CREATE) {
 *     System.out.println("这是一个创建事件");
 * }
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public enum EventType {
    /**
     * 创建事件
     */
    CREATE,

    /**
     * 删除事件
     */
    DELETE,

    /**
     * 更新事件
     */
    UPDATE
}
