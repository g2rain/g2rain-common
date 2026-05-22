package com.g2rain.common.web;


import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * <h1>Principal Context Holder</h1>
 *
 * <p>{@code PrincipalContextHolder} 是基于 {@link ScopedContextHolder} 的线程上下文容器，
 * 用于在当前线程中存储和访问 {@link PrincipalContext}，实现请求级别的数据透传。</p>
 *
 * <p>主要特性：</p>
 * <ul>
 *     <li>线程安全：每个线程独立持有 {@link PrincipalContext}</li>
 *     <li>简化访问：提供静态快捷方法，直接获取或设置 {@link PrincipalContext} 内部属性</li>
 *     <li>线程池支持：通过 {@link #wrap(Runnable)} 和 {@link #wrap(Callable)} 自动透传上下文</li>
 * </ul>
 *
 * <p>典型用途：</p>
 * <ul>
 *     <li>在微服务调用链中透传用户信息、请求 ID、trace ID 等</li>
 *     <li>配合 Feign 或 WebClient 进行 header 透传</li>
 * </ul>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class PrincipalContextHolder {

    /**
     * 当前线程的 {@link PrincipalContext} 容器
     */
    private static final ScopedContextHolder<PrincipalContext> HOLDER = ScopedContextHolder.create();

    /**
     * 私有构造方法，防止实例化
     */
    private PrincipalContextHolder() {

    }

    /**
     * 获取当前线程的 {@link PrincipalContext}，如果不存在则抛出异常
     *
     * @return 当前线程的 {@link PrincipalContext}
     * @throws IllegalStateException 如果上下文未绑定
     */
    public static PrincipalContext require() {
        return HOLDER.require();
    }

    /**
     * 获取当前线程的 {@link PrincipalContext}，如果不存在则返回null
     *
     * @return 当前线程的 {@link PrincipalContext}
     * @throws IllegalStateException 如果上下文未绑定
     */
    public static PrincipalContext get() {
        return HOLDER.get();
    }

    /**
     * 在指定 {@link PrincipalContext} 作用域下执行 {@link Runnable} 任务
     *
     * @param ctx  线程上下文
     * @param task 待执行任务
     */
    public static void runWith(PrincipalContext ctx, Runnable task) {
        HOLDER.runWith(ctx, task);
    }

    /**
     * 在指定 {@link PrincipalContext} 作用域下执行 {@link Callable} 任务
     *
     * @param ctx  线程上下文
     * @param task 待执行任务
     * @param <T>  任务返回类型
     * @return 任务执行结果
     * @throws Exception 任务执行过程中可能抛出的异常
     */
    public static <T> T callWith(PrincipalContext ctx, Callable<T> task) throws Exception {
        return HOLDER.callWith(ctx, task);
    }

    /**
     * 包装 {@link Runnable}，在执行时自动透传当前线程的 {@link PrincipalContext}
     *
     * @param task 待包装任务
     * @return 包装后的任务
     */
    public static Runnable wrap(Runnable task) {
        return HOLDER.wrap(task);
    }

    /**
     * 包装 {@link Callable}，在执行时自动透传当前线程的 {@link PrincipalContext}
     *
     * @param task 待包装任务
     * @param <T>  任务返回类型
     * @return 包装后的任务
     */
    public static <T> Callable<T> wrap(Callable<T> task) {
        return HOLDER.wrap(task);
    }

    /**
     * 设置 clientId
     */
    public static void setClientId(String clientId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setClientId(clientId);
    }

    /**
     * 获取 clientId
     */
    public static String getClientId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getClientId();
    }

    /**
     * 设置 traceId
     */
    public static void setTraceId(String traceId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setTraceId(traceId);
    }

    /**
     * 获取 traceId
     */
    public static String getTraceId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getTraceId();
    }

    /**
     * 设置 requestId
     */
    public static void setRequestId(String requestId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setRequestId(requestId);
    }

    /**
     * 获取 requestId
     */
    public static String getRequestId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getRequestId();
    }

    /**
     * 设置 requestTime
     */
    public static void setRequestTime(String requestTime) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setRequestTime(requestTime);
    }

    /**
     * 获取 requestTime
     */
    public static String getRequestTime() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getRequestTime();
    }

    /**
     * 设置 debug 标记
     */
    public static void setDebug(boolean debug) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setDebug(debug);
    }

    /**
     * 获取 debug 标记
     */
    public static boolean isDebug() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return false;
        }

        return principalContext.isDebug();
    }

    /**
     * 设置 backEnd 标记
     */
    public static void setBackEnd(boolean backEnd) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setBackEnd(backEnd);
    }

    /**
     * 获取 backEnd 标记
     */
    public static boolean isBackEnd() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return false;
        }

        return principalContext.isBackEnd();
    }

    /**
     * 设置 acceptLanguage
     */
    public static void setAcceptLanguage(String acceptLanguage) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setAcceptLanguage(acceptLanguage);
    }

    /**
     * 获取 acceptLanguage
     */
    public static String getAcceptLanguage() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getAcceptLanguage();
    }

    /**
     * 设置 sessionType
     */
    public static void setSessionType(SessionType sessionType) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setSessionType(sessionType);
    }

    /**
     * 获取 sessionType
     */
    public static SessionType getSessionType() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getSessionType();
    }

    /**
     * 设置 passportId
     */
    public static void setPassportId(Long passportId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setPassportId(passportId);
    }

    /**
     * 获取 passportId
     */
    public static Long getPassportId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getPassportId();
    }

    /**
     * 设置 userId
     */
    public static void setUserId(Long userId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setUserId(userId);
    }

    /**
     * 获取 userId
     */
    public static Long getUserId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getUserId();
    }

    /**
     * 设置 name
     */
    public static void setName(String name) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setName(name);
    }

    /**
     * 获取 name
     */
    public static String getName() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getName();
    }

    /**
     * 设置 adminUser 标记
     */
    public static void setAdminUser(boolean adminUser) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setAdminUser(adminUser);
    }

    /**
     * 获取 adminUser 标记
     */
    public static boolean isAdminUser() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return false;
        }

        return principalContext.isAdminUser();
    }

    /**
     * 设置 organId
     */
    public static void setOrganId(Long organId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setOrganId(organId);
    }

    /**
     * 获取 organId
     */
    public static Long getOrganId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getOrganId();
    }

    /**
     * 设置 organName
     */
    public static void setOrganName(String organName) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setOrganName(organName);
    }

    /**
     * 获取 organName
     */
    public static String getOrganName() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getOrganName();
    }

    /**
     * 设置 organType
     */
    public static void setOrganType(OrganType organType) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setOrganType(organType);
    }

    /**
     * 获取 organType
     */
    public static OrganType getOrganType() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getOrganType();
    }

    /**
     * 设置 adminCompany 标记
     */
    public static void setAdminCompany(boolean admin) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setAdminCompany(admin);
    }

    /**
     * 获取 adminCompany 标记
     */
    public static boolean isAdminCompany() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return false;
        }

        return principalContext.isAdminCompany();
    }

    /**
     * 设置 applicationId
     */
    public static void setApplicationId(Long applicationId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setApplicationId(applicationId);
    }

    /**
     * 获取 applicationId
     */
    public static Long getApplicationId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getApplicationId();
    }

    /**
     * 设置 applicationOrganId
     */
    public static void setApplicationOrganId(Long applicationOrganId) {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return;
        }

        principalContext.setApplicationOrganId(applicationOrganId);
    }

    /**
     * 获取 applicationOrganId
     */
    public static Long getApplicationOrganId() {
        PrincipalContext principalContext = get();
        if (Objects.isNull(principalContext)) {
            return null;
        }

        return principalContext.getApplicationOrganId();
    }
}
