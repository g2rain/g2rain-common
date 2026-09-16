package com.g2rain.common.json;


import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.UnaryOperator;

/**
 * {@link JsonNode} 树原地改写：迭代 DFS 遍历，对叶子节点应用变换并写回父节点。
 *
 * <p>{@link ObjectNode} / {@link ArrayNode} 只展开子节点；字符串、数字、布尔、null 等叶子交给 {@code leafHandler}。
 * 替换后的新节点不会再次入栈遍历。
 *
 * <p>例：{@code {"a":"x","b":{"c":"y"}}} 可将每个字符串叶子映射为其它 {@link JsonNode} 并原地 {@code set}。
 *
 * @author alpha
 * @since 2026/8/26
 */
public final class ObjectTreeRewriter {

    private ObjectTreeRewriter() {

    }

    /**
     * 迭代 DFS 遍历 {@code root}，对每个叶子调用 {@code consumer}。
     *
     * @param root     待遍历的 JSON 树，不可为 {@code null}
     * @param consumer 第一个参数为点分路径（如 {@code header.userId}、{@code items[0]}），第二个为叶子节点
     */
    public static void forEachLeaf(JsonNode root, BiConsumer<String, JsonNode> consumer) {
        Deque<LeafFrame> stack = new ArrayDeque<>();
        stack.push(new LeafFrame("", root));
        while (!stack.isEmpty()) {
            LeafFrame frame = stack.pop();
            JsonNode value = frame.node();
            switch (value) {
                case ObjectNode obj -> obj.propertyNames().forEach(name -> {
                    String path = frame.path();
                    String childPath = path.isEmpty() ? name : path + "." + name;
                    stack.push(new LeafFrame(childPath, obj.get(name)));
                });
                case ArrayNode arr -> {
                    for (int i = 0, j = arr.size(); i < j; i++) {
                        stack.push(new LeafFrame(frame.path() + "[" + i + "]", arr.get(i)));
                    }
                }
                default -> consumer.accept(frame.path(), value);
            }
        }
    }

    /**
     * 从 {@code root} 遍历并改写叶子；返回改写后的根（根为叶子时被整体替换，否则为原根引用）。
     *
     * @param root        待遍历的 JSON 树，不可为 {@code null}
     * @param leafHandler 叶子变换；入参为当前叶子，返回替换后的节点
     * @return 改写后的根节点
     */
    public static JsonNode transform(JsonNode root, UnaryOperator<JsonNode> leafHandler) {
        JsonNode result = root;
        Deque<Node> stack = new ArrayDeque<>();
        // 根节点：parent 为 null，field/index 占位
        stack.push(new Node(null, null, -1, root));
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            JsonNode value = node.value();
            switch (value) {
                // 对象：子字段入栈，稍后处理叶子
                case ObjectNode obj -> obj.propertyNames().forEach(name ->
                    stack.push(new Node(obj, name, -1, obj.get(name)))
                );
                // 数组：按索引入栈
                case ArrayNode arr -> {
                    for (int i = 0, j = arr.size(); i < j; i++) {
                        stack.push(new Node(arr, null, i, arr.get(i)));
                    }
                }
                default -> {
                    JsonNode val = leafHandler.apply(value);
                    JsonNode parent = node.parent();
                    // 根为标量叶子：整体替换返回值
                    if (Objects.isNull(parent)) {
                        result = val;
                        continue;
                    }

                    // 写回父 ObjectNode 或 ArrayNode
                    switch (parent) {
                        case ObjectNode o -> o.set(node.field(), val);
                        case ArrayNode a -> a.set(node.index(), val);
                        default -> { /* 入栈时 parent 仅 ObjectNode / ArrayNode */ }
                    }
                }
            }
        }

        return result;
    }

    /**
     * 栈帧：记录当前节点、父节点及在父节点中的位置（对象字段名或数组下标）。
     */
    private record Node(JsonNode parent, String field, int index, JsonNode value) {

    }

    /**
     * {@link #forEachLeaf} 栈帧：从根到当前节点的点分路径及待展开的 {@link JsonNode}。
     *
     * @param path 根起路径，如 {@code header.userId}、{@code items[0]}；根节点为空串
     * @param node 当前栈上的节点
     */
    private record LeafFrame(String path, JsonNode node) {

    }
}
