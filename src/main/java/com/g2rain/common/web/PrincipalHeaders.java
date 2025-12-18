package com.g2rain.common.web;


import lombok.Getter;

/**
 * <p>{@code PrincipalHeaders} 枚举定义了系统中所有常用的 HTTP 请求头字段及其说明，
 * 用于在 {@link PrincipalContext} 与请求头之间建立映射关系，方便统一管理。</p>
 *
 * <p>每个枚举项包含大写和小写的请求头字段名，便于在不同场景下使用。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * String clientIdHeader = PrincipalHeaders.CLIENT_ID.getUpper();
 * String clientIdLower = PrincipalHeaders.CLIENT_ID.getLower();
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Getter
public enum PrincipalHeaders {
    /**
     * 当前请求的 acceptLanguage
     */
    ACCEPT_LANGUAGE("Accept-Language", "accept-language"),

    /**
     * 客户端标识，用于标识调用方的客户端 ID，通常由网关或调用方设置
     */
    CLIENT_ID("X-CLIENT-ID", "x-client-id"),

    /**
     * 网关跟踪标识，用于链路追踪，便于日志追踪和问题排查
     */
    TRACE_ID("X-TRACE-ID", "x-trace-id"),

    /**
     * 前端请求标识，唯一标识一次请求，便于请求追踪
     */
    REQUEST_ID("X-REQUEST-ID", "x-request-id"),

    /**
     * 前端请求时间，请求发起时间戳，用于性能分析和延迟统计
     */
    REQUEST_TIME("X-REQUEST-TIME", "x-request-time"),

    /**
     * 会话类型，标识请求会话类型，用于区分不同认证或业务逻辑
     */
    SESSION_TYPE("X-SESSION-TYPE", "x-session-type"),

    /**
     * 账号标识，用户账号唯一标识，用于认证
     */
    PASSPORT_ID("X-PASSPORT-ID", "x-passport-id"),

    /**
     * 用户标识，用户唯一 ID，用于业务层用户识别
     */
    USER_ID("X-USER-ID", "x-user-id"),

    /**
     * 真实姓名，用户真实姓名，用于展示或身份确认
     */
    NAME("X-NAME", "x-name"),

    /**
     * 租户管理员标记位，标识当前用户是否为租户管理员
     */
    ADMIN_USER("X-ADMIN-USER", "x-admin-user"),

    /**
     * 组织类型，当前用户所属组织的类型标识
     */
    ORGAN_TYPE("X-ORGAN-TYPE", "x-organ-type"),

    /**
     * 组织标识，当前用户所属组织的唯一标识
     */
    ORGAN_ID("X-ORGAN-ID", "x-organ-id"),

    /**
     * 组织名称，当前用户所属组织的名称
     */
    ORGAN_NAME("X-ORGAN-NAME", "x-organ-name"),

    /**
     * 平台管理组织标记位，标识当前组织是否为平台管理组织
     */
    ADMIN_COMPANY("X-ADMIN-COMPANY", "x-admin-company"),

    /**
     * 应用标识，标识调用方的应用 ID
     */
    APP_ID("X-APP-ID", "x-app-id"),

    /**
     * 应用授权码对应组织，标识当前应用授权对应的组织 ID
     */
    APP_ORGAN_ID("X-APP-ORGAN-ID", "x-app-organ-id"),

    /**
     * 调试日志输出级别标记位，用于控制调试日志输出，方便调试和排查
     */
    DEBUG("X-DEBUG", "x-debug"),

    /**
     * 后端发起请求标记位，用于标识该请求是后端发起，可跳过部分数据权限校验
     */
    BACK_END("X-BACK-END", "x-back-end");

    /**
     * 大写请求头字段名称
     */
    private final String upper;

    /**
     * 小写请求头字段名称
     */
    private final String lower;

    /**
     * 构造方法
     *
     * @param upper 大写请求头字段名称
     * @param lower 小写请求头字段名称
     */
    PrincipalHeaders(String upper, String lower) {
        this.upper = upper;
        this.lower = lower;
    }
}
