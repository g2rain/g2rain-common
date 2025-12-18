package com.g2rain.common.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>{@code DPoPJWTPayload} 表示  DPoP proof 的载荷信息，包含请求绑定信息、防重放标识等字段。</p>
 * <p>
 * DPoP（Demonstration of Proof-of-Possession）机制要求将请求信息绑定到 JWT 中，
 * 以防止重放攻击。此类存储  DPoP proof 中的核心载荷数据。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * DPoPJWTPayload payload = new DPoPJWTPayload();
 * payload.setHtu("https://api.example.com/resource");
 * payload.setHtm("POST");
 * payload.setIat(System.currentTimeMillis() / 1000L);
 * payload.setJti(UUID.randomUUID().toString());
 * payload.setAcd("app-code-123");
 * payload.setPha("Base64UrlEncodedHash");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class DPoPJWTPayload {

    /**
     * 请求的 HTTP URI
     * <p>绑定当前  DPoP proof 的请求 URL，防止重放到不同 URI。</p>
     */
    @JsonProperty(value = "htu")
    private String htu;

    /**
     * 请求的 HTTP 方法
     * <p>绑定当前  DPoP proof 的请求方法（GET、POST 等），防止重放到不同方法。</p>
     */
    @JsonProperty(value = "htm")
    private String htm;

    /**
     * 签发时间（Unix 时间戳）
     * <p>表示  DPoP proof 的签发时间，结合有效期用于防重放。</p>
     */
    @JsonProperty(value = "iat")
    private Long iat;

    /**
     * DPoP proof ID（防重放标识）
     * <p>唯一标识该  DPoP proof，后端可存储以防止重放攻击。</p>
     */
    @JsonProperty(value = "jti")
    private String jti;

    /**
     * 应用编码
     * <p>标识当前请求所属的应用，用于鉴权或业务区分。</p>
     */
    @JsonProperty(value = "acd")
    private String acd;

    /**
     * 参数哈希值
     * <p>对请求参数做哈希，用于验证请求内容完整性，防止篡改。</p>
     */
    @JsonProperty(value = "pha")
    private String pha;
}
