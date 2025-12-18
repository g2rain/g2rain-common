package com.g2rain.common.enums;


import java.util.Objects;

/**
 * {@code SessionType} 枚举类用于表示会话身份类型。
 * <p>
 * 当前支持的会话类型包括：
 * <ul>
 *     <li>{@link #ANONYMOUS} — 游客身份</li>
 *     <li>{@link #USER} — 用户登录 IoT 身份类型</li>
 *     <li>{@link #PASSPORT} — 账号登录 IoT 身份类型</li>
 *     <li>{@link #APP_TENANT} — 开发者应用 + 商户身份</li>
 *     <li>{@link #APP} — 开发者应用</li>
 * </ul>
 * <p>
 * 提供了身份类型判断方法，方便业务逻辑快速识别会话类型。
 * <p>
 * <b>使用示例：</b>
 * <pre>{@code
 * SessionType sessionType = SessionType.USER;
 * if (SessionType.isUser(sessionType)) {
 *     System.out.println("当前为用户登录会话");
 * }
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public enum SessionType {

    /**
     * 游客身份
     */
    ANONYMOUS,

    /**
     * 用户登录
     */
    USER,

    /**
     * 账号登录
     */
    PASSPORT,

    /**
     * 开放平台
     */
    APP,

    /**
     * 开发者应用 + 商户身份
     */
    APP_TENANT;

    /**
     * 判断给定会话类型是否为 {@link #ANONYMOUS}（游客身份）。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = SessionType.isAnonymous(SessionType.ANONYMOUS);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param sessionType 待判断的会话类型
     * @return {@code true} 如果是 {@link #ANONYMOUS} 类型，否则 {@code false}
     */
    public static boolean isAnonymous(SessionType sessionType) {
        return Objects.nonNull(sessionType) && ANONYMOUS == sessionType;
    }

    /**
     * 判断给定会话类型是否为 {@link #USER}（用户登录身份）。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = SessionType.isUser(SessionType.USER);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param sessionType 待判断的会话类型
     * @return {@code true} 如果是 {@link #USER} 类型，否则 {@code false}
     */
    public static boolean isUser(SessionType sessionType) {
        return Objects.nonNull(sessionType) && USER == sessionType;
    }

    /**
     * 判断给定会话类型是否为 {@link #PASSPORT}（账号登录身份）。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = SessionType.isPassport(SessionType.PASSPORT);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param sessionType 待判断的会话类型
     * @return {@code true} 如果是 {@link #PASSPORT} 类型，否则 {@code false}
     */
    public static boolean isPassport(SessionType sessionType) {
        return Objects.nonNull(sessionType) && PASSPORT == sessionType;
    }

    /**
     * 判断给定会话类型是否为 {@link #APP}（开发者应用身份）。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = SessionType.isApp(SessionType.APP);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param sessionType 待判断的会话类型
     * @return {@code true} 如果是 {@link #APP} 类型，否则 {@code false}
     */
    public static boolean isApp(SessionType sessionType) {
        return Objects.nonNull(sessionType) && APP == sessionType;
    }

    /**
     * 判断给定会话类型是否为 {@link #APP_TENANT}（开发者应用 + 商户身份）。
     * <p>
     * <b>示例：</b>
     * <pre>{@code
     * boolean result = SessionType.isAppTenant(SessionType.APP_TENANT);
     * System.out.println(result); // 输出：true
     * }</pre>
     *
     * @param sessionType 待判断的会话类型
     * @return {@code true} 如果是 {@link #APP_TENANT} 类型，否则 {@code false}
     */
    public static boolean isAppTenant(SessionType sessionType) {
        return Objects.nonNull(sessionType) && APP_TENANT == sessionType;
    }
}
