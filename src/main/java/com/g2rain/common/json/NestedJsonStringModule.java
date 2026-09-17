package com.g2rain.common.json;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.module.SimpleModule;

/**
 * 注册嵌套 JSON 字符串反序列化：{@link Object} 与 {@link JsonNode} 遇 {@code "{}"} / {@code "[]"} 形态字符串时自动展开。
 *
 * <p>注册后，Map 字段值、List 元素及 {@code str2obj(..., JsonNode.class)} 路径均受益。
 *
 * @author alpha
 * @since 2026/8/26
 */
public final class NestedJsonStringModule extends SimpleModule {

    /**
     * 注册 {@link NestedJsonStrObjectDeserializer} 与 {@link NestedJsonStrNodeDeserializer}。
     */
    public NestedJsonStringModule() {
        super("nested-json-str");// 模块标识名
        addDeserializer(Object.class, new NestedJsonStrObjectDeserializer());
        addDeserializer(JsonNode.class, new NestedJsonStrNodeDeserializer());
    }
}
