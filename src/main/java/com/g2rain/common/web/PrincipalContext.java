package com.g2rain.common.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import com.g2rain.common.utils.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <p>{@code PrincipalContext} 表示当前会话的上下文信息，继承自 {@link BasePrincipal}，包含会话、组织、
 * 应用及请求相关的上下文数据。</p>
 *
 * <p>此类用于在系统内部传递会话上下文信息，如用户身份、组织信息、客户端标识等，
 * 同时支持基于 {@link PrincipalHeaders} 动态获取和设置上下文属性。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * PrincipalContext context = PrincipalContext.of();
 * context.setClientId("client-123");
 * context.setTraceId("trace-456");
 * context.setRequestId(UUID.randomUUID().toString());
 * context.setSessionType(SessionType.USER);
 * context.setOrganType(OrganType.COMPANY);
 * context.setOrganId("org-789");
 * context.setApplicationId("app-abc");
 * context.setDebug(true);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class PrincipalContext extends BasePrincipal {
    /**
     * 当前请求的 acceptLanguage
     */
    private String acceptLanguage;

    /**
     * 网关跟踪标识
     * <p>用于链路追踪，便于日志收集和问题定位。</p>
     */
    @JsonProperty(value = "trace_id")
    private String traceId;

    /**
     * 前端请求标识
     * <p>唯一标识一次请求，方便追踪和调试。</p>
     */
    @JsonProperty(value = "request_id")
    private String requestId;

    /**
     * 前端请求时间
     * <p>请求发起时间戳，用于性能统计和延迟分析。</p>
     */
    @JsonProperty(value = "request_time")
    private String requestTime;

    /**
     * 应用标识
     * <p>当 {@link #sessionType} 为 {@link SessionType#APP} 时有效。</p>
     */
    @JsonProperty(value = "application_id")
    private String applicationId;

    /**
     * 应用所属组织
     * <p>当 {@link #sessionType} 为 {@link SessionType#APP} 时有效，标识应用所属的组织。</p>
     */
    @JsonProperty(value = "application_organ_id")
    private String applicationOrganId;

    /**
     * 调试日志输出级别标记位
     * <p>用于控制是否开启调试日志输出。</p>
     */
    @JsonProperty(value = "debug")
    private boolean debug;

    /**
     * 后端发起请求标记位
     * <p>标识该请求为后端发起，可跳过部分数据权限校验。</p>
     */
    @JsonProperty(value = "back_end")
    private boolean backEnd;

    /**
     * 创建一个空的 {@code PrincipalContext} 对象。
     *
     * @return 一个新的 {@code PrincipalContext} 实例
     */
    public static PrincipalContext of() {
        return new PrincipalContext();
    }

    /**
     * 获取会话类型的字符串表示。
     *
     * @return 会话类型字符串，如果会话类型为空返回 {@code null}
     */
    public String getSessionTypeStr() {
        return Objects.toString(sessionType, null);
    }

    /**
     * 获取组织类型的字符串表示。
     *
     * @return 组织类型字符串，如果组织类型为空返回 {@code null}
     */
    public String getOrganTypeStr() {
        return Objects.toString(organType, null);
    }

    /**
     * 获取租户标识。
     *
     * @return 如果组织类型为租户类型则返回组织 ID，否则返回 {@code null}
     */
    public String getTenantId() {
        return OrganType.isTenant(this.organType) ? this.organId : null;
    }

    /**
     * 获取公司标识。
     *
     * @return 如果组织类型为公司类型则返回组织 ID，否则返回 {@code null}
     */
    public String getCompanyId() {
        return OrganType.isCompany(this.organType) ? this.organId : null;
    }

    /**
     * 判断是否为开发者请求。
     *
     * @return {@code true} 表示当前请求为开发者请求
     */
    public boolean isDeveloper() {
        return Strings.isNotBlank(getUserId()) && Strings.isNotBlank(getApplicationOrganId());
    }

    /**
     * 根据请求头部的 Key 获取对应的属性值。
     *
     * @param headerKey 请求头部的 Key
     * @return 对应的属性值，找不到时返回 {@code null}
     */
    public String getValue(PrincipalHeaders headerKey) {
        return switch (headerKey) {
            case ACCEPT_LANGUAGE -> this.acceptLanguage;
            case CLIENT_ID -> this.clientId;
            case TRACE_ID -> this.traceId;
            case REQUEST_ID -> this.requestId;
            case REQUEST_TIME -> this.requestTime;
            case SESSION_TYPE -> getSessionTypeStr();
            case PASSPORT_ID -> this.passportId;
            case USER_ID -> this.userId;
            case NAME -> this.name;
            case ADMIN_USER -> String.valueOf(this.adminUser);
            case ORGAN_TYPE -> getOrganTypeStr();
            case ORGAN_ID -> this.organId;
            case ORGAN_NAME -> {
                if (Strings.isNotBlank(this.organName)) {
                    yield URLDecoder.decode(this.organName, StandardCharsets.UTF_8);
                } else {
                    yield null;
                }
            }
            case ADMIN_COMPANY -> String.valueOf(this.adminCompany);
            case APP_ID -> this.applicationId;
            case APP_ORGAN_ID -> this.applicationOrganId;
            case DEBUG -> String.valueOf(this.debug);
            case BACK_END -> String.valueOf(this.backEnd);
            case null -> null;
        };
    }

    /**
     * 根据请求头部的 Key 设置对应的属性值。
     *
     * @param headerKey 请求头部的 Key
     * @param value     请求头部 KEY 对应的属性值
     */
    public void setValue(PrincipalHeaders headerKey, String value) {
        if (Objects.isNull(headerKey)) {
            return;
        }

        switch (headerKey) {
            case ACCEPT_LANGUAGE -> this.acceptLanguage = value;
            case CLIENT_ID -> this.clientId = value;
            case TRACE_ID -> this.traceId = value;
            case REQUEST_ID -> this.requestId = value;
            case REQUEST_TIME -> this.requestTime = value;
            case SESSION_TYPE -> this.sessionType = Strings.isNotBlank(value) ? SessionType.valueOf(value) : null;
            case PASSPORT_ID -> this.passportId = value;
            case USER_ID -> this.userId = value;
            case NAME -> this.name = value;
            case ADMIN_USER -> this.adminUser = Strings.isBlank(value) ? Boolean.FALSE : Boolean.parseBoolean(value);
            case ORGAN_TYPE -> this.organType = Strings.isNotBlank(value) ? OrganType.valueOf(value) : null;
            case ORGAN_ID -> this.organId = value;
            case ORGAN_NAME -> this.organName = value;
            case ADMIN_COMPANY ->
                this.adminCompany = Strings.isBlank(value) ? Boolean.FALSE : Boolean.parseBoolean(value);
            case APP_ID -> this.applicationId = value;
            case APP_ORGAN_ID -> this.applicationOrganId = value;
            case DEBUG -> this.debug = Strings.isBlank(value) ? Boolean.FALSE : Boolean.parseBoolean(value);
            case BACK_END -> this.backEnd = Strings.isBlank(value) ? Boolean.FALSE : Boolean.parseBoolean(value);
            default -> { /* 什么都不做 */ }
        }
    }
}
