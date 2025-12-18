package com.g2rain.common.json;


import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Constants;
import com.g2rain.common.utils.Strings;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>{@code JsonCodec} 是基于 Jackson {@link JsonMapper} 封装的 JSON 编解码工具类。</p>
 * <p>
 * 提供对象与 JSON 字符串、字节数组之间的相互转换方法，
 * 以及 JSON 节点的查找、排序和基本数据类型提取工具。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * JsonMapper jsonMapper = JsonMapper.builder().build();
 * JsonCodec codec = new JsonCodec(jsonMapper);
 *
 * MyObject obj = new MyObject("value");
 * String jsonStr = codec.obj2str(obj);
 * MyObject obj2 = codec.str2obj(jsonStr, MyObject.class);
 *
 * byte[] jsonBytes = codec.obj2byte(obj);
 * MyObject obj3 = codec.byte2obj(jsonBytes, new TypeReference<MyObject>() {});
 *
 * JsonNode node = codec.byte2node(jsonBytes);
 * JsonNode subNode = codec.lookupNode(node, "path.to.node");
 *
 * String sortedJson = codec.sort(jsonBytes);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@SuppressWarnings("ClassCanBeRecord")
public class JsonCodec {

    /**
     * Jackson JSON 处理对象
     */
    final JsonMapper jsonMapper;

    /**
     * 构造 {@code JsonCodec} 实例。
     *
     * @param jsonMapper Jackson {@link JsonMapper} 实例
     */
    JsonCodec(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    /**
     * 将对象转换为 JSON 字符串。
     *
     * @param obj 待序列化对象
     * @param <T> 对象类型
     * @return JSON 字符串，输入为 {@code null} 返回 {@code null}
     * @throws BusinessException 序列化失败时抛出
     */
    public <T> String obj2str(T obj) {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj instanceof String str) {
            return str;
        }

        return jsonMapper.writeValueAsString(obj);
    }

    /**
     * 将对象转换为 JSON 字节数组。
     *
     * @param obj 待序列化对象
     * @param <T> 对象类型
     * @return JSON 字节数组，输入为 {@code null} 返回空字节数组
     * @throws BusinessException 序列化失败时抛出
     */
    public <T> byte[] obj2byte(T obj) {
        if (Objects.isNull(obj)) {
            return Constants.EMPTY_BYTE;
        }

        if (obj instanceof String str) {
            return str.getBytes(StandardCharsets.UTF_8);
        }

        return jsonMapper.writeValueAsBytes(obj);
    }

    /**
     * 将任意对象转换为 {@link Map}。
     * <p>
     * 使用 Jackson 的 {@link tools.jackson.databind.json.JsonMapper#convertValue(Object, tools.jackson.core.type.TypeReference)}
     * 将对象的属性映射为 Map 的键值对。
     * </p>
     *
     * <p>
     * 如果传入对象为 {@code null}，返回一个空的 {@link HashMap}。
     * </p>
     *
     * @param <T> 待转换对象的类型
     * @param obj 待转换的对象
     * @return 对象属性与值组成的 {@link Map}，对象为 {@code null} 时返回空 Map
     */
    public <T> Map<String, Object> obj2map(T obj) {
        if (Objects.isNull(obj)) {
            return new HashMap<>();
        }

        return jsonMapper.convertValue(obj, new TypeReference<>() {
        });
    }

    /**
     * 将 JSON 字符串反序列化为对象。
     *
     * @param str      JSON 字符串
     * @param rawClass 原始类
     * @param typeArgs 泛型类型参数
     * @param <T>      对象类型
     * @return 反序列化对象，输入为空字符串返回 {@code null}
     * @throws BusinessException 反序列化失败时抛出
     */
    public <T> T str2obj(String str, Class<?> rawClass, Class<?>... typeArgs) {
        if (Strings.isBlank(str)) {
            return null;
        }
        JavaType type = jsonMapper.getTypeFactory().constructParametricType(rawClass, typeArgs);
        return jsonMapper.readValue(str, type);
    }

    /**
     * 将 JSON 字节数组反序列化为对象。
     *
     * @param bytes   JSON 字节数组
     * @param typeRef 类型引用
     * @param <T>     对象类型
     * @return 反序列化对象，输入为空返回 {@code null}
     * @throws BusinessException 反序列化失败时抛出
     */
    public <T> T byte2obj(byte[] bytes, TypeReference<T> typeRef) {
        if (Collections.isEmpty(bytes)) {
            return null;
        }

        return jsonMapper.readValue(bytes, typeRef);
    }

    /**
     * 将 JSON 字节数组反序列化为 {@link JsonNode}。
     *
     * @param bytes JSON 字节数组
     * @return {@link JsonNode} 节点，输入为空返回 {@code null}
     * @throws BusinessException 反序列化失败时抛出
     */
    public JsonNode byte2node(byte[] bytes) {
        if (Collections.isEmpty(bytes)) {
            return null;
        }

        return jsonMapper.readTree(bytes);
    }

    /**
     * 按路径查找 {@link JsonNode} 节点。
     *
     * @param node JSON 节点
     * @param path 路径，格式如 "a.b.c"
     * @return 查找到的节点，找不到返回 {@code null}
     */
    public JsonNode lookupNode(JsonNode node, String path) {
        if (Objects.isNull(node) || Strings.isBlank(path)) {
            return node;
        }
        String[] nodeNames = path.split("\\.");
        for (String nodeName : nodeNames) {
            if (!node.has(nodeName)) {
                return null;
            }
            node = node.get(nodeName);
        }
        return node;
    }

    /**
     * 对 JSON 字节数组按键名进行排序，返回排序后的 JSON 字符串。
     *
     * @param bytes JSON 字节数组
     * @return 排序后的 JSON 字符串，输入为空返回空字符串
     * @throws BusinessException 当反序列化或序列化失败时抛出
     */
    public String sort(byte[] bytes) {
        // 判断输入字节数组是否为空，若为空直接返回空字符串
        if (Collections.isEmpty(bytes)) {
            return "";
        }

        // 将字节数组反序列化为 JsonNode 对象树
        JsonNode json = jsonMapper.readValue(bytes, JsonNode.class);

        // 使用栈实现深度优先遍历，避免递归调用
        Deque<JsonNode> stack = new ArrayDeque<>();
        stack.push(json);

        // 遍历 JSON 树，处理对象节点和数组节点
        while (!stack.isEmpty()) {
            JsonNode node = stack.pop(); // 弹出栈顶节点进行处理

            // 如果当前节点是对象节点
            if (node.isObject()) {
                ObjectNode obj = (ObjectNode) node;

                // 临时存储对象的所有字段，保持原有顺序
                LinkedHashMap<String, JsonNode> children = LinkedHashMap.newLinkedHashMap(obj.size());
                obj.properties().forEach(entry -> children.put(entry.getKey(), entry.getValue()));

                // 清空当前对象节点，以便重新插入排序后的字段
                obj.removeAll();

                // 按 Unicode 字典序对字段名排序，并将对应字段重新加入对象节点
                children.keySet().stream().sorted(Comparator.naturalOrder()).forEach(key ->
                    obj.set(key, children.get(key))
                );

                // 将对象的容器子节点压入栈，以便继续处理
                pushStack(obj, stack);
                continue; // 当前对象处理完成，继续下一个节点
            }

            // 如果当前节点是数组节点，将数组中的容器节点压入栈
            if (node.isArray()) {
                pushStack(node, stack);
            }
        }

        // 将排序后的 JSON 对象树序列化为字符串并返回
        return jsonMapper.writeValueAsString(json);
    }

    /**
     * 将容器节点的子节点压入栈中（仅对象节点或数组节点）。
     *
     * @param node  当前 JSON 节点
     * @param stack 节点栈
     */
    private void pushStack(JsonNode node, Deque<JsonNode> stack) {
        // 遍历当前节点的所有子节点
        for (JsonNode child : node) {
            // 仅将容器节点（对象或数组）压入栈，非容器节点无需排序
            if (!child.isContainer()) {
                continue;
            }
            stack.push(child);
        }
    }

    /**
     * 获取 {@link JsonNode} 的文本值。
     *
     * @param node JSON 节点
     * @return 文本值，节点为 {@code null} 返回 {@code null}
     */
    public String asString(JsonNode node) {
        return Objects.nonNull(node) ? node.asString() : null;
    }

    /**
     * 获取 {@link JsonNode} 的布尔值。
     *
     * @param node JSON 节点
     * @return 布尔值，节点为 {@code null} 返回 {@code false}
     */
    public boolean asBoolean(JsonNode node) {
        return Objects.nonNull(node) && node.asBoolean();
    }

    /**
     * 获取 {@link JsonNode} 的整数值。
     *
     * @param node JSON 节点
     * @return 整数值，节点为 {@code null} 返回 {@code 0}
     */
    public int asInt(JsonNode node) {
        return Objects.nonNull(node) ? node.asInt() : 0;
    }

    /**
     * 获取 {@link JsonNode} 的双精度浮点值。
     *
     * @param node JSON 节点
     * @return 双精度浮点值，节点为 {@code null} 返回 {@code 0.0}
     */
    public double asDouble(JsonNode node) {
        return Objects.nonNull(node) ? node.asDouble() : 0.0;
    }

    /**
     * 获取 {@link JsonNode} 的长整型值。
     *
     * @param node JSON 节点
     * @return 长整型值，节点为 {@code null} 返回 {@code 0L}
     */
    public long asLong(JsonNode node) {
        return Objects.nonNull(node) ? node.asLong() : 0L;
    }
}
