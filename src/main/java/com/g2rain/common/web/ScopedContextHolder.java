package com.g2rain.common.web;


import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * <h1>Scoped Context Holder</h1>
 *
 * <p>{@code ScopedContextHolder} 是一个通用的上下文容器类，基于 Java {@link ScopedValue} 实现。
 * 它允许在当前线程范围内存储和访问一个特定类型的上下文对象 {@code C}，并支持线程池任务的上下文透传。</p>
 *
 * <p>核心特点：</p>
 * <ul>
 *     <li>线程安全：每个线程拥有独立的上下文值，避免多线程访问冲突</li>
 *     <li>自动绑定和释放：通过 {@link #runWith(Object, Runnable)} 和 {@link #callWith(Object, Callable)}
 *         可以在作用域内执行任务，并在结束后自动恢复原上下文</li>
 *     <li>线程池任务支持：{@link #wrap(Runnable)} 和 {@link #wrap(Callable)} 用于将当前上下文透传到线程池任务中</li>
 * </ul>
 *
 * @param <C> 上下文对象的类型
 * @author alpha
 * @since 2026/2/27
 */
public final class ScopedContextHolder<C> {

    /**
     * 当前线程绑定的上下文对象
     * <p>使用 {@link ScopedValue} 来实现线程范围的存储和访问</p>
     */
    private final ScopedValue<C> context = ScopedValue.newInstance();

    /**
     * 私有构造函数，禁止外部直接实例化
     */
    private ScopedContextHolder() {
    }

    /**
     * 创建一个新的 {@link ScopedContextHolder} 实例
     *
     * @param <C> 上下文对象类型
     * @return 新的 {@link ScopedContextHolder} 实例
     */
    public static <C> ScopedContextHolder<C> create() {
        return new ScopedContextHolder<>();
    }

    // ===== 基础能力 =====

    /**
     * 获取当前线程绑定的上下文对象
     *
     * <p>如果上下文未绑定，返回 {@code null}</p>
     *
     * @return 当前线程的上下文对象，可能为 {@code null}
     */
    public C get() {
        return context.isBound() ? context.get() : null;
    }

    /**
     * 获取当前线程绑定的上下文对象
     *
     * <p>如果上下文未绑定，将抛出 {@link IllegalStateException}</p>
     *
     * @return 当前线程的上下文对象
     * @throws IllegalStateException 如果上下文未绑定
     */
    public C require() {
        return context.orElseThrow(() -> new IllegalStateException("Scoped context not bound"));
    }

    /**
     * 在指定上下文值作用域内执行任务
     *
     * <p>执行完成后，自动恢复原上下文状态</p>
     *
     * @param value 上下文值
     * @param task  待执行的任务
     */
    public void runWith(C value, Runnable task) {
        ScopedValue.where(context, value).run(task);
    }

    /**
     * 在指定上下文值作用域内执行任务并返回结果
     *
     * <p>执行完成后，自动恢复原上下文状态</p>
     *
     * @param value 上下文值
     * @param task  待执行的任务
     * @param <T>   任务返回类型
     * @return 任务执行结果
     * @throws Exception 任务执行过程中可能抛出的异常
     */
    public <T> T callWith(C value, Callable<T> task) throws Exception {
        return ScopedValue.where(context, value).call(task::call);
    }

    // ===== 线程池传播 =====

    /**
     * 包装 {@link Runnable}，使其在执行时自动透传当前线程上下文
     *
     * <p>如果当前上下文为空，则直接返回原任务</p>
     *
     * @param task 待包装的 {@link Runnable} 任务
     * @return 包装后的任务，可在其他线程执行时透传上下文
     */
    public Runnable wrap(Runnable task) {
        C captured = get();
        if (Objects.isNull(captured)) {
            return task;
        }

        return () -> runWith(captured, task);
    }

    /**
     * 包装 {@link Callable}，使其在执行时自动透传当前线程上下文
     *
     * <p>如果当前上下文为空，则直接返回原任务</p>
     *
     * @param task 待包装的 {@link Callable} 任务
     * @param <T>  任务返回类型
     * @return 包装后的任务，可在其他线程执行时透传上下文
     */
    public <T> Callable<T> wrap(Callable<T> task) {
        C captured = get();
        if (Objects.isNull(captured)) {
            return task;
        }

        return () -> callWith(captured, task);
    }
}
