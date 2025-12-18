package com.g2rain.common.id;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("ID生成器接口测试")
class IdGeneratorTest {

    @Test
    @DisplayName("测试ID生成器接口定义")
    void testIdGeneratorInterface() {
        // 创建一个简单的实现来测试接口
        IdGenerator idGenerator = new IdGenerator() {
            @Override
            public Long generateId() {
                return 123456789L;
            }
        };

        Long id = idGenerator.generateId();
        assertNotNull(id);
        assertEquals(123456789L, id);
    }
}
