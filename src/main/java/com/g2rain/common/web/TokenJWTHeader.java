package com.g2rain.common.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>{@code TokenJWTHeader} 表示普通 Token JWT 的头部信息，用于描述 JWT 类型、签名算法等元数据。</p>
 *
 * <p>该类用于封装 JWT 头部字段，便于生成和验证 JWT。</p>
 *
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * TokenJWTHeader header = new TokenJWTHeader();
 * header.setKid("key-id-123");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class TokenJWTHeader {
    /**
     * JWT 类型
     * <p>固定值为 "JWT"，用于标识这是普通 Token JWT。</p>
     */
    @JsonProperty(value = "typ")
    private String type = "JWT";

    /**
     * 签名算法
     * <p>指定 Token JWT 使用的签名算法, 例如 ES256。</p>
     */
    @JsonProperty(value = "alg")
    private String alg = "ES256";

    /**
     * Key ID（可选）
     * <p>签名的密钥标识, 适用于多密钥场景, 方便后端快速查找对应的公钥进行验证。</p>
     */
    @JsonProperty(value = "kid")
    private String kid;
}
