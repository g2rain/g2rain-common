package com.g2rain.common.exception;

import com.g2rain.common.syncer.AbstractMessageStorage;
import lombok.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("错误信息注册表测试")
class ErrorMessageRegistryTest {

    @Test
    @DisplayName("测试抽象类定义")
    void testAbstractClassDefinition() {
        // 创建一个具体的实现来测试抽象类
        ErrorMessageRegistry registry = new ErrorMessageRegistry() {
            @Override
            public String getMessage(String errorCode, String locale) {
                return "test message";
            }

            @Override
            protected @NonNull String dataSource() {
                return "test";
            }

            @Override
            protected @NonNull Class<LocalizedErrorMessage> getValueType() {
                return LocalizedErrorMessage.class;
            }

            @Override
            protected @NonNull String getKey(@NonNull LocalizedErrorMessage value) {
                return "key";
            }

            @Override
            protected void create(@NonNull String key, LocalizedErrorMessage value) {
                // 空实现
            }

            @Override
            protected void delete(@NonNull String key) {
                // 空实现
            }

            @Override
            protected void update(@NonNull String key, LocalizedErrorMessage value) {
                // 空实现
            }

            @Override
            protected String get(@NonNull String key) {
                return "value";
            }
        };

        assertNotNull(registry);
        assertTrue(registry instanceof AbstractMessageStorage);
    }
}
