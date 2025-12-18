package com.g2rain.common.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Token JWT头部测试")
class TokenJWTHeaderTest {

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        TokenJWTHeader header = new TokenJWTHeader();

        assertEquals("JWT", header.getType());
        assertEquals("ES256", header.getAlg());
        assertNull(header.getKid());
    }

    @Test
    @DisplayName("测试设置和获取值")
    void testSetAndGetValues() {
        TokenJWTHeader header = new TokenJWTHeader();

        header.setType("CUSTOM");
        header.setAlg("RS256");
        header.setKid("key123");

        assertEquals("CUSTOM", header.getType());
        assertEquals("RS256", header.getAlg());
        assertEquals("key123", header.getKid());
    }
}
