package com.g2rain.common.concurrent;


/**
 * 可选跨线程上下文传播器（如 Micrometer/OTel Observation、Span，以及 MDC）。
 * <p>由 tracing starter 在启动时 {@link Contexts#register}；common 不直接依赖 OTel/MDC。
 * {@link Contexts#wrap} 在父线程 {@link #capture}、子线程 {@link #restore}，结束时 close scope。
 *
 * @author alpha
 * @since 2026/8/6
 */
public interface ContextPropagator {

    /**
     * 在父线程捕获当前可传播上下文（Observation / Span / MDC 等）。
     *
     * @return 不透明快照；无上下文时可返回 null
     */
    Object capture();

    /**
     * 在子线程恢复快照；返回的 scope 必须在任务 finally 中 close。
     *
     * @param snap {@link #capture()} 的返回值
     * @return 可关闭的作用域
     */
    AutoCloseable restore(Object snap);
}
