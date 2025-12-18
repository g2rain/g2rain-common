/**
 * <h1>Jackson JSON 封装工具包</h1>
 *
 * <p>
 * 本包提供了一套基于 Jackson 的 JSON 编解码封装，主要功能包括：
 * </p>
 *
 * <ul>
 *     <li><b>通用序列化与反序列化</b>：{@link com.g2rain.common.json.JsonCodec} 提供对象与 JSON 之间的双向转换。</li>
 *     <li><b>自定义反序列化</b>：{@link com.g2rain.common.json.RawNumberDeserializer} 保留数字原始格式，避免精度丢失。</li>
 *     <li><b>原始数字节点封装</b>：{@link com.g2rain.common.json.RawNumberNode} 封装原始数字字符串，提供 {@link java.math.BigDecimal} 访问。</li>
 *     <li><b>灵活配置构建器</b>：{@link com.g2rain.common.json.JsonCodecBuilder} 提供多种 Jackson 配置方法。</li>
 *     <li><b>工厂类访问</b>：{@link com.g2rain.common.json.JsonCodecFactory} 提供默认配置实例的快捷访问。</li>
 * </ul>
 *
 * <h2>主要功能说明</h2>
 * <p>
 * - 默认序列化包含所有字段（包括 null 值）<br>
 * - 支持注释 JSON 的解析<br>
 * - 支持空字符串转 null 对象<br>
 * - 保留浮点数精度<br>
 * - 提供 JSON 字段排序功能
 * </p>
 *
 * <h2>使用示例</h2>
 * <pre>{@code
 * // 获取默认 JsonCodec
 * JsonCodec codec = JsonCodecFactory.instance();
 *
 * // 对象转 JSON 字符串
 * String json = codec.obj2str(myObject);
 *
 * // JSON 字符串转对象
 * MyObject obj = codec.str2obj(json, MyObject.class);
 *
 * // 对象转字节数组
 * byte[] bytes = codec.obj2byte(myObject);
 *
 * // 反序列化为 JsonNode
 * JsonNode node = codec.byte2node(bytes);
 *
 * // 查找 JSON 节点
 * JsonNode subNode = codec.lookupNode(node, "data.items[0]");
 *
 * // 自定义构建器配置
 * JsonCodec customCodec = JsonCodecBuilder.builder()
 *         .withDefaults()
 *         .withConfig(mapper -> mapper.enable(SerializationFeature.INDENT_OUTPUT))
 *         .build();
 * }</pre>
 *
 * <h2>包结构</h2>
 * <ul>
 *     <li>{@link com.g2rain.common.json.JsonCodec}</li>
 *     <li>{@link com.g2rain.common.json.JsonCodecBuilder}</li>
 *     <li>{@link com.g2rain.common.json.JsonCodecFactory}</li>
 *     <li>{@link com.g2rain.common.json.RawNumberDeserializer}</li>
 *     <li>{@link com.g2rain.common.json.RawNumberNode}</li>
 * </ul>
 *
 * <h2>建议使用场景</h2>
 * <ul>
 *     <li>对 JSON 数据进行高精度处理</li>
 *     <li>需要自定义 JSON 序列化与反序列化规则的项目</li>
 *     <li>需要统一 JSON 编解码配置的系统</li>
 * </ul>
 *
 * <p>该封装旨在提升 Jackson 的易用性和安全性，同时保留可扩展性。</p>
 *
 * @author alpha
 * @since 2025/10/5
 */
package com.g2rain.common.json;
