package com.g2rain.common.web;


/**
 * <p>类型安全的请求级扩展属性键，将属性名与值类型绑定在一起，
 * 供 {@link PrincipalContext} / {@link PrincipalContextHolder} 存取自定义属性。</p>
 *
 * <p>仅进程内有效，不写入 HTTP Header。业务方通常定义为 {@code static final} 常量后复用。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * static final ContextKey<OrderBag> ORDER = ContextKey.of("order", OrderBag.class);
 *
 * PrincipalContextHolder.putAttribute(ORDER, bag);
 * OrderBag bag = PrincipalContextHolder.getAttribute(ORDER);
 * }</pre>
 *
 * @param name 属性名
 * @param type 值类型
 * @param <T>  值类型
 * @author alpha
 * @since 2026/8/7
 */
public record ContextKey<T>(String name, Class<T> type) {

    /**
     * 创建属性键。
     *
     * @param name 属性名
     * @param type 值类型
     * @param <T>  值类型
     * @return 新的 {@link ContextKey}
     */
    public static <T> ContextKey<T> of(String name, Class<T> type) {
        return new ContextKey<>(name, type);
    }
}
