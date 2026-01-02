/**
 * <h1>Syncer 包概述</h1>
 *
 * <p>Syncer 包提供系统内事件同步、消息分发和消息存储管理的核心功能，
 * 主要用于分布式系统中实现数据和事件的同步、广播和处理。</p>
 *
 * <h2>核心功能</h2>
 * <ul>
 *     <li><b>事件发布与分发：</b>通过 {@link com.g2rain.common.syncer.EventPublisher} 和
 *         {@link com.g2rain.common.syncer.EventPublisherHub} 实现事件的发布与广播，
 *         支持多通道（binding）选择。</li>
 *     <li><b>事件类型定义：</b>使用 {@link com.g2rain.common.syncer.EventType} 定义事件操作类型（CREATE、UPDATE、DELETE）。</li>
 *     <li><b>消息存储管理：</b>通过 {@link com.g2rain.common.syncer.AbstractMessageStorage} 和
 *         {@link com.g2rain.common.syncer.MessageStorageRegistry} 注册和访问不同数据源的消息存储。</li>
 *     <li><b>消息分发：</b>{@link com.g2rain.common.syncer.MessageDispatcher} 定义消息分发接口，
 *         {@link com.g2rain.common.syncer.DefaultMessageDispatcher} 提供默认实现。</li>
 *     <li><b>事件封装：</b>{@link com.g2rain.common.syncer.EventMessage} 封装事件来源、类型和数据内容。</li>
 * </ul>
 *
 * <h2>主要类关系</h2>
 * <pre>
 * EventPublisherHub → EventPublisher → MessageDispatcher → AbstractMessageStorage
 *                         ↑                                        ↓
 *                     EventMessage                     MessageStorageRegistry
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * // 创建事件发布中心，支持多通道
 * Map<String, EventPublisher> publishers = Map.of(
 *     "userChannel", new MyEventPublisher(),
 *     "orderChannel", new MyEventPublisher()
 * );
 * EventPublisherHub hub = new EventPublisherHub(publishers);
 *
 * // 发送事件
 * hub.sendCreate("userChannel", "USER_SERVICE", new UserData("张三", 25));
 *
 * // 注册消息存储
 * MessageStorageRegistry.register(new MyMessageStorage());
 *
 * // 消息分发
 * MessageDispatcher dispatcher = new DefaultMessageDispatcher();
 * dispatcher.dispatch("{\"dataSource\":\"USER_SERVICE\",\"eventType\":\"CREATE\",\"data\":\"{}\"}");
 * }</pre>
 *
 * <h2>注意事项</h2>
 * <ul>
 *     <li>事件处理时请确保数据源标识正确，否则事件将被忽略。</li>
 *     <li>消息存储注册应在系统初始化阶段完成，以确保事件分发正确执行。</li>
 *     <li>事件数据结构需与目标消息存储类型一致，否则可能导致反序列化失败。</li>
 * </ul>
 *
 * @author alpha
 * @see com.g2rain.common.syncer.EventPublisherHub
 * @see com.g2rain.common.syncer.EventPublisher
 * @see com.g2rain.common.syncer.EventType
 * @see com.g2rain.common.syncer.MessageDispatcher
 * @see com.g2rain.common.syncer.AbstractMessageStorage
 * @see com.g2rain.common.syncer.MessageStorageRegistry
 * @since 2025/10/5
 */
package com.g2rain.common.syncer;
