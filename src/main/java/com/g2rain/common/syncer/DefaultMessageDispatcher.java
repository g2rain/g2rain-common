package com.g2rain.common.syncer;


import com.g2rain.common.json.JsonCodec;
import com.g2rain.common.json.JsonCodecFactory;
import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Strings;

import java.util.Objects;
import java.util.Set;

/**
 * <p>默认的消息分发器实现类，负责将原始消息解析并分发到已注册的 {@link AbstractMessageStorage}。</p>
 *
 * <p>解析过程分为两步：</p>
 * <ol>
 *     <li>解析事件消息的元数据（数据源、事件类型），以提高性能。</li>
 *     <li>根据事件类型（创建、更新、删除）调用相应的存储器方法。</li>
 * </ol>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * MessageDispatcher dispatcher = new DefaultMessageDispatcher();
 * dispatcher.dispatch(rawMessageJsonString);
 * }</pre>
 *
 * <p>注意：如果没有注册的消息存储或消息为空，将直接返回，不进行处理。</p>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class DefaultMessageDispatcher implements MessageDispatcher {
    /**
     * JSON 编解码工具实例
     */
    private static final JsonCodec jsonCodec = JsonCodecFactory.instance();

    /**
     * 分发原始事件消息。
     * <p>该方法会解析事件消息的元数据，根据数据源筛选相关存储，并调用对应的事件方法。</p>
     *
     * @param rawMessage 原始 JSON 格式的事件消息
     */
    @Override
    public void dispatch(String rawMessage) {
        // 获取已注册的消息存储
        Set<AbstractMessageStorage<?, ?, ?>> messageStorages = MessageStorageRegistry.getMessageStorages();
        // 如果没有消息存储或消息为空，直接返回
        if (Collections.isEmpty(messageStorages) || Strings.isBlank(rawMessage)) {
            return;
        }

        /*
         * 解析事件消息中的基本信息，第一次解析仅解析事件源和事件类型。
         * 由于此时只需要处理事件的元数据，避免了过早地解析整个消息数据以提高性能。
         */
        EventMessage<String> em = jsonCodec.str2obj(rawMessage, EventMessage.class, String.class);
        if (Objects.isNull(em)) {
            return; // 如果解析失败，直接返回
        }

        // 解析事件的源、类型和数据
        String dataSource = em.getDataSource();
        EventType eventType = em.getEventType();
        String rawData = em.getData();
        // 如果任何字段为空，则跳过该消息
        if (Strings.isBlank(dataSource) || Objects.isNull(eventType) || Strings.isBlank(rawData)) {
            return;
        }

        // 根据数据源过滤出相关的消息存储，并进行相应的事件处理
        messageStorages.stream().filter(s -> dataSource.equals(s.dataSource())).forEach(ms ->
            // 调用 doDispatch 进行事件分发
            doDispatch(ms, eventType, rawData)
        );
    }

    /**
     * 执行具体事件分发。
     * <p>根据事件类型调用存储器的创建、更新或删除方法。</p>
     *
     * @param ms        消息存储器
     * @param eventType 事件类型（CREATE、UPDATE、DELETE）
     * @param rawData   事件的原始数据
     * @param <K>       存储键类型
     * @param <V>       存储值类型
     * @param <T>       存储返回类型
     */
    private <K, V, T> void doDispatch(AbstractMessageStorage<K, V, T> ms, EventType eventType, String rawData) {
        try {
            // 将数据转换为具体类型
            V data = jsonCodec.str2obj(rawData, ms.getValueType());
            // 如果数据转换失败，则跳过
            if (Objects.isNull(data)) {
                return;
            }

            // 获取数据的键
            K key = ms.getKey(data);
            switch (eventType) {
                // 如果是创建事件，调用 create 方法
                case CREATE -> ms.create(key, data);
                // 如果是更新事件，调用 update 方法
                case UPDATE -> ms.update(key, data);
                // 如果是删除事件，调用 remove 方法
                case DELETE -> ms.delete(key);
            }
        } catch (Exception e) {
            // log.error("消息处理失败: {}", e.getMessage(), e); // 处理异常，记录错误信息
        }
    }
}
