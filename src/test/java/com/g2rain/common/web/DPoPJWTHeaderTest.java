package com.g2rain.common.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("DPoP JWT头部测试")
class DPoPJWTHeaderTest {

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        DPoPJWTHeader header = new DPoPJWTHeader();

        assertEquals("dpop+jwt", header.getType());
        assertEquals("ES256", header.getAlg());
        assertNull(header.getKid());
        assertNull(header.getJwk());
        assertNull(header.getPhAlg());
    }

    @Test
    @DisplayName("测试设置和获取值")
    void testSetAndGetValues() {
        DPoPJWTHeader header = new DPoPJWTHeader();

        header.setType("CUSTOM");
        header.setAlg("RS256");
        header.setKid("key123");
        header.setPhAlg("SHA-256");

        // 创建并设置JWK
        DPoPJWTHeader.JWK jwk = new DPoPJWTHeader.JWK();
        jwk.setKty("EC");
        jwk.setCrv("P-256");
        jwk.setX("x-coordinate");
        jwk.setY("y-coordinate");

        header.setJwk(jwk);

        assertEquals("CUSTOM", header.getType());
        assertEquals("RS256", header.getAlg());
        assertEquals("key123", header.getKid());
        assertEquals("SHA-256", header.getPhAlg());
        assertNotNull(header.getJwk());
        assertEquals("EC", header.getJwk().getKty());
        assertEquals("P-256", header.getJwk().getCrv());
        assertEquals("x-coordinate", header.getJwk().getX());
        assertEquals("y-coordinate", header.getJwk().getY());
    }

    @Test
    @DisplayName("测试JWK内部类")
    void testJWKInnerClass() {
        DPoPJWTHeader.JWK jwk = new DPoPJWTHeader.JWK();

        jwk.setKty("EC");
        jwk.setCrv("P-256");
        jwk.setX("x-coordinate");
        jwk.setY("y-coordinate");

        assertEquals("EC", jwk.getKty());
        assertEquals("P-256", jwk.getCrv());
        assertEquals("x-coordinate", jwk.getX());
        assertEquals("y-coordinate", jwk.getY());
    }
}
