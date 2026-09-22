package com.g2rain.common.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * VT 工具：启动/提交时自动 {@link Contexts#wrap}。
 * <ul>
 *   <li>{@link #runVirtual}/{@link #callVirtual}/{@link #startVirtual}：直接起 VT，立刻返回，不等待</li>
 *   <li>{@link #virtualPerTask} + {@link #submit}：多任务共用一个短生命周期池（用完 close）</li>
 * </ul>
 */
public final class ContextExecutors {

    private ContextExecutors() {

    }

    /**
     * @return 每任务一虚拟线程的 ExecutorService（多任务并行时用完请 close）
     */
    public static ExecutorService virtualPerTask() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * 起 VT 执行，立刻返回；不要 Future 时用这个。
     */
    public static void startVirtual(Runnable task) {
        Thread.startVirtualThread(Contexts.wrap(task));
    }

    /**
     * 起 VT 执行，立刻返回 Future；要等结果自己 get。
     */
    public static CompletableFuture<Void> runVirtual(Runnable task) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Thread.startVirtualThread(Contexts.wrap(() -> {
            try {
                task.run();
                future.complete(null);
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        }));
        return future;
    }

    /**
     * 起 VT 执行 Callable，立刻返回 Future。
     */
    public static <T> CompletableFuture<T> callVirtual(Callable<T> task) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Thread.startVirtualThread(Contexts.wrap(() -> {
            try {
                future.complete(task.call());
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        }));
        return future;
    }

    /**
     * 提交 Runnable，自动 wrap（Principal + 已注册传播器）。
     */
    public static Future<?> submit(ExecutorService executor, Runnable task) {
        return executor.submit(Contexts.wrap(task));
    }

    /**
     * 提交 Callable，自动 wrap（Principal + 已注册传播器）。
     */
    public static <T> Future<T> submit(ExecutorService executor, Callable<T> task) {
        return executor.submit(Contexts.wrapCallable(task));
    }
}
