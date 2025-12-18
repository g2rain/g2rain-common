/**
 * <h1>Syncer 包功能概述</h1>
 *
 * <p><b>Syncer</b> 包提供了系统内事件同步、消息分发、存储管理等核心功能，主要用于在分布式系统中实现数据和事件的同步、分发和处理。</p>
 *
 * <h2>核心功能</h2>
 * <ul>
 *     <li><b>事件发布与分发：</b>通过 {@link com.g2rain.common.syncer.EventPublisher} 和 {@link com.g2rain.common.syncer.EventPublisherHub} 实现事件的发布和广播。</li>
 *     <li><b>事件类型定义：</b>使用 {@link com.g2rain.common.syncer.EventType} 定义事件操作类型（CREATE、UPDATE、DELETE）。</li>
 *     <li><b>消息存储管理：</b>通过 {@link com.g2rain.common.syncer.AbstractMessageStorage} 与 {@link com.g2rain.common.syncer.MessageStorageRegistry} 实现不同数据源消息存储的注册与访问。</li>
 *     <li><b>消息分发：</b>{@link com.g2rain.common.syncer.MessageDispatcher} 接口定义消息分发方法，{@link com.g2rain.common.syncer.DefaultMessageDispatcher} 提供默认实现。</li>
 *     <li><b>事件封装：</b>{@link com.g2rain.common.syncer.EventMessage} 用于封装事件的来源、类型及数据内容。</li>
 * </ul>
 *
 * <h2>主要类关系</h2>
 * <pre>
 * EventPublisherHub → EventPublisher → MessageDispatcher → AbstractMessageStorage
 *                           ↑                             ↓
 *                     EventMessage                  MessageStorageRegistry
 * </pre>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * // 创建事件发布中心
 * EventPublisherHub hub = new EventPublisherHub(Set.of(new MyEventPublisher()));
 *
 * // 发送事件
 * hub.send("USER_SERVICE", EventType.CREATE, new UserData("张三", 25));
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
 *     <li>所有事件处理必须确保数据源正确，否则事件将被忽略。</li>
 *     <li>消息存储注册请在系统初始化阶段完成，以确保事件分发正确执行。</li>
 *     <li>事件消息的数据结构需与目标消息存储的类型一致，否则可能导致反序列化失败。</li>
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
