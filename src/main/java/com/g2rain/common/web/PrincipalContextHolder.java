package com.g2rain.common.web;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;

import java.util.Objects;

/**
 * <p>{@code PrincipalContextHolder} 提供基于线程的 {@link PrincipalContext} 存取管理。
 * 它使用 {@link TransmittableThreadLocal} 存储会话上下文，保证在跨线程调用时上下文数据的传递。</p>
 *
 * <p>该类提供了静态方法用于获取、设置、删除当前线程的 {@link PrincipalContext} 及其属性，
 * 避免直接操作 {@link TransmittableThreadLocal}，简化上下文管理。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * PrincipalContextHolder.setClientId("client-123");
 * PrincipalContextHolder.setSessionType(SessionType.USER);
 * String traceId = PrincipalContextHolder.getTraceId();
 * PrincipalContextHolder.remove();
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public class PrincipalContextHolder {

    /**
     * 私有构造方法，防止实例化。
     */
    private PrincipalContextHolder() {
    }

    /**
     * 存储当前线程 {@link PrincipalContext} 的容器。
     */
    private static final TransmittableThreadLocal<PrincipalContext> contextContainer = new TransmittableThreadLocal<>();

    /**
     * 获取当前线程的 {@link PrincipalContext}。
     * <p>如果当前线程没有上下文，则创建一个新的空上下文并存储。</p>
     *
     * @return 当前线程的 {@link PrincipalContext}
     */
    public static PrincipalContext get() {
        PrincipalContext context = contextContainer.get();
        if (Objects.isNull(context)) {
            context = PrincipalContext.of();
            contextContainer.set(context);
        }
        return context;
    }

    /**
     * 删除当前线程的 {@link PrincipalContext}。
     */
    public static void remove() {
        contextContainer.remove();
    }

    // 以下为 PrincipalContext 属性的快捷访问方法
    public static void setAcceptLanguage(String acceptLanguage) {
        get().setAcceptLanguage(acceptLanguage);
    }

    public static String getAcceptLanguage() {
        return get().getAcceptLanguage();
    }

    public static void setClientId(String clientId) {
        get().setClientId(clientId);
    }

    public static String getClientId() {
        return get().getClientId();
    }

    public static void setTraceId(String traceId) {
        get().setTraceId(traceId);
    }

    public static String getTraceId() {
        return get().getTraceId();
    }

    public static void setRequestId(String requestId) {
        get().setRequestId(requestId);
    }

    public static String getRequestId() {
        return get().getRequestId();
    }

    public static void setRequestTime(String requestTime) {
        get().setRequestTime(requestTime);
    }

    public static String getRequestTime() {
        return get().getRequestTime();
    }

    public static void setSessionType(SessionType sessionType) {
        get().setSessionType(sessionType);
    }

    public static SessionType getSessionType() {
        return get().getSessionType();
    }

    public static void setPassportId(String passportId) {
        get().setPassportId(passportId);
    }

    public static String getPassportId() {
        return get().getPassportId();
    }

    public static void setUserId(String userId) {
        get().setUserId(userId);
    }

    public static String getUserId() {
        return get().getUserId();
    }

    public static void setName(String name) {
        get().setName(name);
    }

    public static String getName() {
        return get().getName();
    }

    public static void setAdminUser(boolean adminUser) {
        get().setAdminUser(adminUser);
    }

    public static boolean isAdminUser() {
        return get().isAdminUser();
    }

    public static void setOrganType(OrganType organType) {
        get().setOrganType(organType);
    }

    public static OrganType getOrganType() {
        return get().getOrganType();
    }

    public static void setOrganId(String organId) {
        get().setOrganId(organId);
    }

    public static String getOrganId() {
        return get().getOrganId();
    }

    public static void setOrganName(String organName) {
        get().setOrganName(organName);
    }

    public static String getOrganName() {
        return get().getOrganName();
    }

    public static void setAdminCompany(boolean admin) {
        get().setAdminCompany(admin);
    }

    public static boolean isAdminCompany() {
        return get().isAdminCompany();
    }

    public static void setApplicationId(String applicationId) {
        get().setApplicationId(applicationId);
    }

    public static String getApplicationId() {
        return get().getApplicationId();
    }

    public static void setApplicationOrganId(String applicationOrganId) {
        get().setApplicationOrganId(applicationOrganId);
    }

    public static String getApplicationOrganId() {
        return get().getApplicationOrganId();
    }

    public static void setDebug(boolean debug) {
        get().setDebug(debug);
    }

    public static boolean isDebug() {
        return get().isDebug();
    }

    public static void setBackEnd(boolean backEnd) {
        get().setBackEnd(backEnd);
    }

    public static boolean isBackEnd() {
        return get().isBackEnd();
    }
}
