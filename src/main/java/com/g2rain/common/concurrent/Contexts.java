package com.g2rain.common.concurrent;

import com.g2rain.common.web.PrincipalContextHolder;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * 跨虚拟线程 / 线程池上下文传播：在父线程捕获 Principal（及可选链路/MDC），子线程恢复，结束时清理。
 * <p>再起 VT / 线程池且依赖身份或日志关联时必须经本类 wrap。
 * Principal 复用 {@link PrincipalContextHolder#wrap}；
 * OTel/MDC 等经 {@link #register(ContextPropagator)} 注入（tracing-otel），未注册则跳过。
 */
public final class Contexts {

    /** 可选传播器；由 tracing starter 注册，未注册时 wrap 仅传 Principal。 */
    private static volatile ContextPropagator PROPAGATOR = null;

    private Contexts() {

    }

    /**
     * 注册传播器（进程内通常只调一次；后注册覆盖前者）。
     *
     * @param p 实现；传 null 等价于取消注册
     */
    public static void register(ContextPropagator p) {
        PROPAGATOR = p;
    }

    /**
     * 包装 Runnable：提交到其它线程前在<strong>当前</strong>线程调用。
     *
     * @param task 业务任务
     * @return 可安全提交到子线程的任务
     */
    public static Runnable wrap(Runnable task) {
        // 父线程捕获：Principal 由 Holder.wrap 内部抓；链路/MDC 由 PROPAGATOR
        Object tracing = Objects.nonNull(PROPAGATOR) ? PROPAGATOR.capture() : null;
        // wrap 在 principal 为空时原样返回 task
        Runnable body = PrincipalContextHolder.wrap(task);

        return () -> {
            AutoCloseable scope = null;
            try {
                // restore 内含 OTel setThreadLocals + MDC；scope 结束时一并还原
                if (Objects.nonNull(PROPAGATOR) && Objects.nonNull(tracing)) {
                    scope = PROPAGATOR.restore(tracing);
                }
                body.run();
            } finally {
                // 必须 close，否则子 VT 上 ThreadLocal / MDC 泄漏或串请求
                if (Objects.nonNull(scope)) {
                    try {
                        scope.close();
                    } catch (Exception ignored) {

                    }
                }
            }
        };
    }

    /**
     * 包装 Callable，语义同 {@link #wrap(Runnable)}。
     *
     * @param task 带返回值的任务
     * @param <T>  返回类型
     * @return 包装后的 Callable
     */
    public static <T> Callable<T> wrapCallable(Callable<T> task) {
        Object tracing = Objects.nonNull(PROPAGATOR) ? PROPAGATOR.capture() : null;
        Callable<T> body = PrincipalContextHolder.wrap(task);

        return () -> {
            AutoCloseable scope = null;
            try {
                if (Objects.nonNull(PROPAGATOR) && Objects.nonNull(tracing)) {
                    scope = PROPAGATOR.restore(tracing);
                }
                return body.call();
            } finally {
                if (Objects.nonNull(scope)) {
                    try {
                        scope.close();
                    } catch (Exception ignored) {

                    }
                }
            }
        };
    }

    /**
     * 包装 Supplier：内部转 Callable；异常原样抛出（不翻译为业务异常）。
     * <p>{@link Callable#call()} 声明 checked，故对非 {@link RuntimeException} 用
     * {@link IllegalStateException} 包一层并保留 cause；业务异常类型不变。
     *
     * @param task Supplier
     * @param <T>  返回类型
     * @return 包装后的 Supplier
     */
    public static <T> Supplier<T> wrapSupplier(Supplier<T> task) {
        Callable<T> callable = wrapCallable(task::get);
        return () -> {
            try {
                return callable.call();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
