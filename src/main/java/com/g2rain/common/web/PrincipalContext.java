package com.g2rain.common.web;


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
     * 网关跟踪标识
     * <p>用于链路追踪，便于日志收集和问题定位。</p>
     */
    private String traceId;

    /**
     * 前端请求标识
     * <p>唯一标识一次请求，方便追踪和调试。</p>
     */
    private String requestId;

    /**
     * 前端请求时间
     * <p>请求发起时间戳，用于性能统计和延迟分析。</p>
     */
    private String requestTime;

    /**
     * 当前请求的 acceptLanguage
     */
    private String acceptLanguage;

    /**
     * 调试日志输出级别标记位
     * <p>用于控制是否开启调试日志输出。</p>
     */
    private boolean debug;

    /**
     * 后端发起请求标记位
     * <p>标识该请求为后端发起，可跳过部分数据权限校验。</p>
     */
    private boolean backEnd;

    /**
     * 请求来源应用标识
     * <p>表示当前接口调用是由哪个应用发起的</p>
     */
    private Long applicationId;

    /**
     * 请求来源应用所属机构标识
     * <p>表示发起当前接口调用的应用所隶属的机构</p>
     */
    private Long applicationOrganId;

    /**
     * 创建一个空的 {@code PrincipalContext} 对象。
     *
     * @return 一个新的 {@code PrincipalContext} 实例
     */
    public static PrincipalContext of() {
        return new PrincipalContext();
    }

    /**
     * 根据请求头部的 Key 获取对应的属性值。
     *
     * @param headerKey 请求头部的 Key
     * @return 对应的属性值，找不到时返回 {@code null}
     */
    public String getValue(PrincipalHeaders headerKey) {
        return switch (headerKey) {
            case CLIENT_ID -> this.clientId;
            case TRACE_ID -> this.traceId;
            case REQUEST_ID -> this.requestId;
            case REQUEST_TIME -> this.requestTime;
            case DEBUG -> String.valueOf(this.debug);
            case BACK_END -> String.valueOf(this.backEnd);
            case ACCEPT_LANGUAGE -> this.acceptLanguage;
            case SESSION_TYPE -> toStrOrNull(this.sessionType);
            case PASSPORT_ID -> toStrOrNull(this.passportId);
            case USER_ID -> toStrOrNull(this.userId);
            case NAME -> this.name;
            case ADMIN_USER -> String.valueOf(this.adminUser);
            case ORGAN_ID -> toStrOrNull(this.organId);
            case ORGAN_NAME -> safeDecode(this.organName);
            case ORGAN_TYPE -> toStrOrNull(this.organType);
            case ADMIN_COMPANY -> String.valueOf(this.adminCompany);
            case APP_ID -> toStrOrNull(this.applicationId);
            case APP_ORGAN_ID -> toStrOrNull(this.applicationOrganId);
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
            case CLIENT_ID -> this.clientId = value;
            case TRACE_ID -> this.traceId = value;
            case REQUEST_ID -> this.requestId = value;
            case REQUEST_TIME -> this.requestTime = value;
            case DEBUG -> this.debug = Boolean.parseBoolean(value);
            case BACK_END -> this.backEnd = Boolean.parseBoolean(value);
            case ACCEPT_LANGUAGE -> this.acceptLanguage = value;
            // 这个地方没有做任何非空和合法性校验 是因为, 这个值如果非法说明是被攻击或者存在bug, 需要修复
            case SESSION_TYPE -> this.sessionType = SessionType.valueOf(value);
            case PASSPORT_ID -> this.passportId = parseLongOrNull(value);
            case USER_ID -> this.userId = parseLongOrNull(value);
            case NAME -> this.name = value;
            case ADMIN_USER -> this.adminUser = Boolean.parseBoolean(value);
            case ORGAN_TYPE -> this.organType = OrganType.safeOf(value);
            case ORGAN_ID -> this.organId = parseLongOrNull(value);
            case ORGAN_NAME -> this.organName = value;
            case ADMIN_COMPANY -> this.adminCompany = Boolean.parseBoolean(value);
            case APP_ID -> this.applicationId = parseLongOrNull(value);
            case APP_ORGAN_ID -> this.applicationOrganId = parseLongOrNull(value);
            default -> { /* 什么都不做 */ }
        }
    }

    /**
     * 安全地对字符串进行 URL 解码
     * <p>
     * 当传入字符串为空、仅包含空白字符时，返回 {@code null}；
     * 否则使用 UTF-8 对字符串进行 URL 解码。
     * </p>
     *
     * <p>
     * 该方法不会抛出 {@link NullPointerException}，保证空值安全。
     * </p>
     *
     * @param value 待解码的字符串
     * @return 解码后的字符串，或者 {@code null}（如果输入为空或仅包含空白字符）
     */
    private String safeDecode(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        return URLDecoder.decode(value,
            StandardCharsets.UTF_8
        );
    }

    /**
     * 将对象安全地转换为字符串
     * <p>
     * 如果传入对象为 {@code null}，返回 {@code null}；
     * 否则返回对象的 {@link Object#toString()} 结果。
     * </p>
     *
     * <p>
     * 该方法与 {@link Objects#toString(Object)} 类似，但默认值固定为 {@code null}，
     * 避免返回字符串 "null"。
     * </p>
     *
     * @param value 待转换的对象
     * @return 对象的字符串表示，或 {@code null}（如果输入为 {@code null}）
     */
    private String toStrOrNull(Object value) {
        return Objects.toString(value, null);
    }

    /**
     * 将字符串解析为 {@link Long}，解析失败时返回 {@code null}
     * <p>
     * 当传入值为空、仅包含空白字符，或不符合 {@link Long} 的数值格式时，
     * 本方法不会抛出异常，而是统一返回 {@code null}。
     * </p>
     *
     * <p>
     * 该方法遵循“尽力解析”的语义约定，
     * 调用方可通过 {@code null} 判断解析是否成功。
     * </p>
     *
     * @param value 待解析的字符串
     * @return 解析成功返回对应的 {@link Long}，否则返回 {@code null}
     */
    private Long parseLongOrNull(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
