package com.g2rain.common.web;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 应用作用域值对象（Application Scope）
 * <p>
 * 用于描述 JWT Token 在应用维度上的授权边界，
 * 明确限定当前 Token 可访问的具体应用。
 * </p>
 *
 * <p>
 * 该对象通常作为 {@link TokenJWTPayload} 的组成部分，
 * 会被序列化存入 JWT Payload 中，用于后续鉴权判断。
 * </p>
 *
 * <p>
 * 注意事项：
 * <ul>
 *   <li>该对象不承载权限明细，仅用于限定应用范围</li>
 *   <li>不应在此对象中引入角色、菜单等授权粒度</li>
 * </ul>
 * </p>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationScope {

    /**
     * 应用标识
     */
    private Long applicationId;

    /**
     * 应用编码
     */
    private String applicationCode;

    /**
     * 应用所属机构标识
     */
    private Long applicationOrganId;
}
