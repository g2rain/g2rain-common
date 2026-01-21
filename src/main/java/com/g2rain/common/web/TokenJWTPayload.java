package com.g2rain.common.web;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * <p>{@code TokenJWTPayload} 表示普通 Token 的 JWT 载荷信息，包含会话信息、签发时间、过期时间、刷新过期时间以及客户端公钥等字段。</p>
 *
 * <p>该类用于封装 Token 的 JWT Payload，便于在鉴权、验证和防重放操作中使用。</p>
 *
 * <p><b>字段说明：</b></p>
 * <ul>
 *     <li>{@link #issuedAt}：签发时间（Unix 时间戳，单位秒）</li>
 *     <li>{@link #expireAt}：Token 过期时间（Unix 时间戳，单位秒）</li>
 *     <li>{@link #refreshExpireAt}：刷新 Token 的过期时间（Unix 时间戳，单位秒）</li>
 *     <li>{@link #clientPublicKey}：客户端 DPoP 公钥，用于绑定 Token 和客户端</li>
 *     <li>{@link #applicationScopes}：应用作用域集合，用于鉴权判断请求所属应用权限</li>
 * </ul>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * TokenJWTPayload payload = new TokenJWTPayload();
 * payload.setIssuedAt(System.currentTimeMillis() / 1000);
 * payload.setExpireAt(System.currentTimeMillis() / 1000 + 3600);
 * payload.setRefreshExpireAt(System.currentTimeMillis() / 1000 + 7200);
 * payload.setClientPublicKey("base64-dpop-key");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class TokenJWTPayload extends BasePrincipal {
    /**
     * 签发时间（Unix 时间戳）
     * <p>表示 Token 的签发时间，单位为秒。</p>
     */
    private Long issuedAt;

    /**
     * Token 过期时间（Unix 时间戳）
     * <p>表示 Token 的过期时间，单位为秒。</p>
     */
    private Long expireAt;

    /**
     * 刷新 Token 过期时间（Unix 时间戳）
     * <p>表示刷新 Token 的过期时间，单位为秒。</p>
     */
    private Long refreshExpireAt;

    /**
     * DPoP 公钥
     * <p>与clientId搭配绑定到Token, 将客户端DPoP和Token进行绑定</p>
     */
    private String clientPublicKey;

    /**
     * 应用作用域集合
     * <p>
     * 用于鉴权场景，描述当前 Token 可访问的应用范围。
     * 每个元素同时包含：
     * <ul>
     *   <li>applicationId：应用标识</li>
     *   <li>applicationCode：应用编码</li>
     *   <li>applicationOrganId：应用所属机构标识</li>
     * </ul>
     * 用于精确限定 Token 的有效应用上下文。
     * </p>
     */
    private List<ApplicationScope> applicationScopes;
}
