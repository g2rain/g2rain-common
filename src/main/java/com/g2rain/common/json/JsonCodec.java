package com.g2rain.common.json;


import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Constants;
import com.g2rain.common.utils.Strings;
import org.jetbrains.annotations.Contract;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

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
public class JsonCodec {

    /**
     * 点分路径 → JsonPointer 字符串：{@code a.b[1].c} → {@code /a/b/1/c}。
     */
    Function<String, String> POINTER_PATH = path -> "/" + path.replace('.', '/').replaceAll("\\[(\\d+)]", "/$1");

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
     * 将 Java 对象转为 {@link JsonNode} 树。
     *
     * @param obj 待转换对象；已是 {@link JsonNode} 时原样返回
     * @return 对应 JSON 树；{@code null} 输入返回 {@code null}
     */
    public JsonNode obj2node(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj instanceof JsonNode node) {
            return node;
        }

        return jsonMapper.valueToTree(obj);
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
     * 将 JSON 字符串反序列化为指定类型。
     *
     * @param str     JSON 字符串
     * @param typeRef 目标类型（如 {@code new TypeReference<Map<String, Object>>() {}}）
     * @param <T>     反序列化结果类型
     * @return 反序列化对象；{@code str} 为 blank 时返回 {@code null}
     * @throws BusinessException 反序列化失败时抛出
     */
    public <T> T str2obj(String str, TypeReference<T> typeRef) {
        if (Strings.isBlank(str)) {
            return null;
        }

        return jsonMapper.readValue(str, typeRef);
    }

    /**
     * 将 JSON 对象字符串反序列化为 {@code Map<String, Object>}。
     *
     * <p>根节点须为 JSON 对象；blank 或反序列化结果为 {@code null} 时返回空 {@link LinkedHashMap}。
     *
     * @param str JSON 对象字符串
     * @return 键值 Map；无内容时返回可变的空 Map
     * @throws BusinessException 非法 JSON 或根节点非对象时抛出
     */
    public Map<String, Object> str2map(String str) {
        if (Strings.isBlank(str)) {
            return null;
        }

        return str2obj(str, new TypeReference<>() {
        });
    }

    /**
     * 将 JSON 字符串解析为 {@link JsonNode} 树。
     *
     * @param str JSON 字符串
     * @return 根节点；blank 时返回 {@code null}
     * @throws BusinessException 非法 JSON 时抛出
     */
    public JsonNode str2node(String str) {
        if (Strings.isBlank(str)) {
            return null;
        }

        return jsonMapper.readTree(str);
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
     * 按点分路径读取 {@link JsonNode}（内部转 JsonPointer，走 Jackson {@link JsonNode#at}）。
     *
     * <p>path 格式：{@code a.b.c[1].sd}（点分 + 数字下标）；不支持 {@code []} 数组模板。
     *
     * @param node JSON 树根
     * @param path 点分路径；blank 时原样返回 {@code node}
     * @return 命中节点；不存在或 {@code MissingNode} 时返回 {@code null}
     */
    public JsonNode lookupNode(JsonNode node, String path) {
        if (Objects.isNull(node) || Strings.isBlank(path)) {
            return node;
        }

        // a.b[1].c → /a/b/1/c，交给 Jackson 按 JsonPointer 定位
        JsonNode result = node.at(POINTER_PATH.apply(path));
        return result.isMissingNode() ? null : result;
    }

    /**
     * 按点分路径 upsert 叶子，等价于 {@link #upsertNode(JsonNode, String, JsonNode, boolean) upsertNode(..., false)}。
     *
     * <p>不自动建中间 object/array，不扩数组；其余语义见四参重载。
     */
    public boolean upsertNode(JsonNode node, String path, JsonNode value) {
        return upsertNode(node, path, value, false);
    }

    /**
     * 按点分路径 upsert 叶子，可选自动补中间路径。
     *
     * <p>path 格式同 {@link #lookupNode(JsonNode, String)}（{@code a.b[1].c}）；内部将 {@code [n]} 展开为路径段再逐段遍历。
     * {@code value} 为 Java {@code null} 时写入 JSON null（{@link JsonMapper#nullNode()}）。
     *
     * <p>{@code createMissing=false}：中间节点缺失、为 null、类型不匹配，或数组下标越界 → {@code false}，不写入。
     * {@code createMissing=true}：按下一 segment 推断并补 object/array，数组扩至目标下标后再写入叶子。
     *
     * @param node          可变的 JSON 树根（通常为 {@link ObjectNode}）
     * @param path          点分路径
     * @param value         待写入节点
     * @param createMissing 是否自动补中间 object/array 并扩数组
     * @return 写入成功 {@code true}，否则 {@code false}
     */
    public boolean upsertNode(JsonNode node, String path, JsonNode value, boolean createMissing) {
        if (Objects.isNull(node) || Strings.isBlank(path)) {
            return false;
        }

        // a.b[1].c → ["a","b","1","c"]，按段下钻；末段为叶子
        String[] parts = path.replaceAll("\\[(\\d+)]", ".$1").split("\\.");
        JsonNode current = node;

        for (int i = 0, j = parts.length - 1; i < j; i++) {
            String part = parts[i], next = parts[i + 1];

            if (current.isObject()) {
                ObjectNode obj = (ObjectNode) current;
                JsonNode child = obj.get(part);

                if (invalidNode(child, next)) {
                    if (!createMissing) return false;
                    child = next.matches("\\d+") ? obj.arrayNode() : obj.objectNode();
                    obj.set(part, child);
                }

                current = child;
            } else if (current.isArray()) {
                ArrayNode arr = (ArrayNode) current;
                int index = Integer.parseInt(part);

                if (index >= arr.size()) {
                    if (!createMissing) return false;
                    while (arr.size() <= index) {
                        arr.addNull();
                    }
                }

                JsonNode child = arr.get(index);

                if (invalidNode(child, next)) {
                    if (!createMissing) return false;
                    child = next.matches("\\d+") ? arr.arrayNode() : arr.objectNode();
                    arr.set(index, child);
                }

                current = child;
            } else {
                return false;
            }
        }

        String leaf = parts[parts.length - 1];

        if (current.isObject()) {
            ((ObjectNode) current).set(leaf, Objects.isNull(value) ? jsonMapper.nullNode() : value);
            return true;
        }

        if (current.isArray()) {
            ArrayNode arr = (ArrayNode) current;
            int index = Integer.parseInt(leaf);

            if (index >= arr.size()) {
                if (!createMissing) return false;
                while (arr.size() <= index) {
                    arr.addNull();
                }
            }

            arr.set(index, Objects.isNull(value) ? jsonMapper.nullNode() : value);
            return true;
        }

        return false;
    }

    /**
     * 中间节点不可用：缺失、JSON null，或类型与下一 segment 不匹配（数字 → array，否则 → object）。
     */
    private boolean invalidNode(JsonNode node, String next) {
        if (Objects.isNull(node) || node.isNull()) {
            return true;
        }

        return next.matches("\\d+") ? !node.isArray() : !node.isObject();
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

    /**
     * 节点是否「有值」：非 {@code null} 且非 JSON {@code null}。
     *
     * <p>空白字符串仍视为有值；与 {@link #lookupNode} 返回 {@code null}（缺失）区分。
     * 例：{@code hasValue(null)} → false；{@code hasValue(jsonMapper.nullNode())} → false；
     * {@code hasValue(node(" "))} → true。
     *
     * @param node JSON 节点；可 null
     * @return 有值 {@code true}
     */
    @Contract("null -> false")
    public boolean hasValue(JsonNode node) {
        return Objects.nonNull(node) && !node.isNull();
    }

    /**
     * 将节点规范为可比字符串：标量 {@link JsonNode#asString()} 后 trim；对象/数组走 {@link #obj2str}。
     *
     * <p>用于合同白名单、Evidence 子串匹配、Working/Facts 值相等判定。
     * 例：{@code " 北京 "} → {@code "北京"}；{@code 200} → {@code "200"}；无值节点 → {@code ""}。
     *
     * @param node JSON 节点；可 null
     * @return 规范化字符串；无值时空串
     */
    public String normalizeNode(JsonNode node) {
        if (!hasValue(node)) {
            return "";
        }

        return node.isValueNode() ? node.asString().trim() : obj2str(node);
    }

    /**
     * 两节点规范化字符串是否相等（{@link #normalizeNode} 后 {@link String#equals}）。
     *
     * <p>任一侧无值 → false。例：{@code "123"} 与数字 {@code 123} → true。
     *
     * @param left  左节点；可 null
     * @param right 右节点；可 null
     * @return 双方有值且规范化后相等 → true
     */
    public boolean nodesEqual(JsonNode left, JsonNode right) {
        if (!hasValue(left) || !hasValue(right)) {
            return false;
        }

        return normalizeNode(left).equals(normalizeNode(right));
    }
}
