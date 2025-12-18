package com.g2rain.common.web;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>{@code DPoPJWTHeader} 表示 DPoP proof 的头部信息，包含类型、签名算法、公钥等信息。</p>
 * <p>
 * DPoP（Demonstration of Proof-of-Possession）是 OAuth 2.0 中的一种机制，
 * 用于绑定访问令牌到特定的公私钥对以防止重放攻击。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * DPoPJWTHeader header = new DPoPJWTHeader();
 * header.setKid("key-id-123");
 * DPoPJWTHeader.JWK jwk = new DPoPJWTHeader.JWK();
 * jwk.setKty("EC");
 * jwk.setCrv("P-256");
 * jwk.setX("Base64UrlEncodedX");
 * jwk.setY("Base64UrlEncodedY");
 * header.setJwk(jwk);
 * header.setPhAlg("SHA-256");
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
@Setter
@Getter
@NoArgsConstructor
public class DPoPJWTHeader {

    /**
     * JWT 类型
     * <p>DPoP 固定值为 "dpop+jwt"，区分普通 JWT 和 DPoP proof。</p>
     */
    @JsonProperty(value = "typ")
    private String type = "dpop+jwt";

    /**
     * 签名算法
     * <p>指定 DPoP proof 使用的签名算法，如 ES256。</p>
     */
    @JsonProperty(value = "alg")
    private String alg = "ES256";

    /**
     * Key ID（密钥标识）
     * <p>唯一标识一个公私钥对，方便网关查找对应的公钥进行验签。</p>
     */
    @JsonProperty(value = "kid")
    private String kid;

    /**
     * 公钥（JWK 格式）
     * <p>包含用于验证 DPoP proof 签名的公钥信息。</p>
     */
    @JsonProperty(value = "jwk")
    private JWK jwk;

    /**
     * 请求参数 Hash 算法
     * <p>用于对请求参数生成哈希值，提升安全性。</p>
     */
    @JsonProperty(value = "ph_alg")
    private String phAlg;

    /**
     * 公钥 JWK 数据模型类
     *
     * <p>JWK（JSON Web Key）是用于描述公钥的 JSON 数据结构，
     * 包含密钥类型、曲线类型及坐标值等信息，供网关验签使用。</p>
     */
    @Setter
    @Getter
    @NoArgsConstructor
    static class JWK {

        /**
         * 密钥类型
         * <p>例如 "EC" 表示椭圆曲线密钥。</p>
         */
        @JsonProperty(value = "kty")
        private String kty;

        /**
         * 曲线类型
         * <p>指定椭圆曲线的名称，如 "P-256"。</p>
         */
        @JsonProperty(value = "crv")
        private String crv;

        /**
         * 公钥 X 坐标
         * <p>Base64Url 编码的公钥 X 坐标。</p>
         */
        @JsonProperty(value = "x")
        private String x;

        /**
         * 公钥 Y 坐标
         * <p>Base64Url 编码的公钥 Y 坐标。</p>
         */
        @JsonProperty(value = "y")
        private String y;
    }
}
