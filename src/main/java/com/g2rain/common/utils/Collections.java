package com.g2rain.common.utils;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * <p>工具类 {@code Collections} 提供对集合、数组、Map 等对象的常用判空和包含检查方法。</p>
 * <p>支持 {@code Collection}、{@code Map}、数组以及普通对象的判空。</p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * boolean emptyList = Collections.isEmpty(new ArrayList<>());
 * boolean notEmptyMap = Collections.isNotEmpty(Map.of("key", "value"));
 * boolean containsValue = Collections.contains(List.of("a", "b"), "a");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class Collections {

    /**
     * 私有构造，防止实例化。
     */
    private Collections() {

    }

    /**
     * 判断对象是否为空。
     * <p>
     * 支持以下类型：
     * <ul>
     *     <li>{@code null} 对象</li>
     *     <li>{@link Map} 对象（{@code isEmpty()}）</li>
     *     <li>{@link Collection} 对象（{@code isEmpty()}）</li>
     *     <li>数组（长度为 0）</li>
     *     <li>其他对象永远返回 {@code false}</li>
     * </ul>
     * </p>
     *
     * @param obj 待判断对象
     * @return 对象为空返回 {@code true}，否则 {@code false}
     */
    public static boolean isEmpty(Object obj) {
        return switch (obj) {
            case null -> true;
            case Map<?, ?> map -> map.isEmpty();
            case Collection<?> collection -> collection.isEmpty();
            default -> obj.getClass().isArray() && Array.getLength(obj) == 0;
        };
    }

    /**
     * 判断对象是否非空。
     *
     * @param obj 待判断对象
     * @return 对象非空返回 {@code true}，否则 {@code false}
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 检查集合中是否包含指定对象。
     *
     * @param collection 集合对象
     * @param obj        待检查对象
     * @return 集合非空且包含对象返回 {@code true}，否则 {@code false}
     */
    public static boolean contains(Collection<?> collection, Object obj) {
        return !isEmpty(collection) && collection.contains(obj);
    }
}
