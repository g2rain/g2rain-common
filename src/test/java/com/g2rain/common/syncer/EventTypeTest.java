package com.g2rain.common.syncer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("事件类型枚举测试")
class EventTypeTest {

    @Test
    @DisplayName("测试事件类型枚举值")
    void testEventTypeValues() {
        assertEquals(4, EventType.values().length);
        assertNotNull(EventType.CREATE);
        assertNotNull(EventType.DELETE);
        assertNotNull(EventType.UPDATE);
        assertNotNull(EventType.REFRESH);
    }

    @Test
    @DisplayName("测试事件类型枚举名称查找")
    void testEventTypeValueOf() {
        assertEquals(EventType.CREATE, EventType.valueOf("CREATE"));
        assertEquals(EventType.DELETE, EventType.valueOf("DELETE"));
        assertEquals(EventType.UPDATE, EventType.valueOf("UPDATE"));
        assertEquals(EventType.REFRESH, EventType.valueOf("REFRESH"));
    }
}
