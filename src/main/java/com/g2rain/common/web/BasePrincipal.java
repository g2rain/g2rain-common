package com.g2rain.common.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.g2rain.common.enums.OrganType;
import com.g2rain.common.enums.SessionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>{@code BasePrincipal} 表示当前会话的主体信息，包含会话类型、用户信息和组织信息等。</p>
 * <p>
 * 本类主要用于标识当前会话身份，并存储会话相关的账号、用户、组织信息。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * BasePrincipal principal = new BasePrincipal();
 * principal.setSessionType(SessionType.USER);
 * principal.setUserId("user-123");
 * principal.setName("张三");
 * principal.setOrganType(OrganType.COMPANY);
 * principal.setOrganId("org-456");
 * principal.setOrganName("测试公司");
 * principal.setAdminUser(true);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class BasePrincipal {

    /**
     * 客户端标识
     * <p>标识请求的客户端应用。DPoP 的密钥 k(ey)id, 用于标识绑定的 DPoP 密钥，便于后端验证签名</p>
     */
    protected String clientId;

    /**
     * 会话类型
     * <p>标识当前会话类型，例如用户会话、应用会话等。</p>
     */
    @JsonProperty(value = "session_type")
    protected SessionType sessionType;

    /**
     * 账号 ID
     * <p>当 {@link #sessionType} 为 {@link SessionType#PASSPORT} 时有效。</p>
     */
    @JsonProperty(value = "passport_id")
    protected String passportId;

    /**
     * 用户 ID
     * <p>当 {@link #sessionType} 为 {@link SessionType#USER} 时有效。</p>
     */
    @JsonProperty(value = "user_id")
    protected String userId;

    /**
     * 真实姓名
     * <p>用于展示或身份确认。</p>
     */
    @JsonProperty(value = "name")
    protected String name;

    /**
     * 公司内管理员标记位
     * <p>标识当前用户是否为管理员。</p>
     */
    @JsonProperty(value = "admin_user")
    protected boolean adminUser;

    /**
     * 组织类型
     * <p>标识当前用户所属组织类型。</p>
     */
    @JsonProperty(value = "organ_type")
    protected OrganType organType;

    /**
     * 组织标识
     * <p>当前用户所属组织的唯一标识。</p>
     */
    @JsonProperty(value = "organ_id")
    protected String organId;

    /**
     * 组织名称
     * <p>当前用户所属组织的名称。</p>
     */
    @JsonProperty(value = "organ_name")
    protected String organName;

    /**
     * 平台管理组织标记位
     * <p>标识该组织是否为平台管理组织。</p>
     */
    @JsonProperty(value = "admin_company")
    protected boolean adminCompany;
}
