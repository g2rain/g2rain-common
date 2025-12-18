package com.g2rain.common.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("DPoP JWT载荷测试")
class DPoPJWTPayloadTest {

    @Test
    @DisplayName("测试默认值")
    void testDefaultValues() {
        DPoPJWTPayload payload = new DPoPJWTPayload();

        assertNull(payload.getHtu());
        assertNull(payload.getHtm());
        assertNull(payload.getIat());
        assertNull(payload.getJti());
        assertNull(payload.getAcd());
        assertNull(payload.getPha());
    }

    @Test
    @DisplayName("测试设置和获取值")
    void testSetAndGetValues() {
        DPoPJWTPayload payload = new DPoPJWTPayload();

        payload.setHtu("/api/test");
        payload.setHtm("POST");
        payload.setIat(1234567890L);
        payload.setJti("dpop123");
        payload.setAcd("app123");
        payload.setPha("hash123");

        assertEquals("/api/test", payload.getHtu());
        assertEquals("POST", payload.getHtm());
        assertEquals(1234567890L, payload.getIat());
        assertEquals("dpop123", payload.getJti());
        assertEquals("app123", payload.getAcd());
        assertEquals("hash123", payload.getPha());
    }
}
