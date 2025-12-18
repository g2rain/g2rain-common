package com.g2rain.common.exception;

import com.g2rain.common.model.Result;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("微服务异常处理器测试")
class DefaultExceptionProcessorTest {

    private DefaultExceptionProcessor exceptionProcessor;

    @BeforeEach
    void setUp() {
        exceptionProcessor = new DefaultExceptionProcessor(new ErrorMessageRegistry() {
            @Override
            public String getMessage(String errorCode, String locale) {
                return "";
            }

            @Override
            protected @NonNull String dataSource() {
                return "";
            }

            @Override
            protected @NonNull Class<LocalizedErrorMessage> getValueType() {
                return LocalizedErrorMessage.class;
            }

            @Override
            protected @NonNull String getKey(@NonNull LocalizedErrorMessage value) {
                return "";
            }

            @Override
            protected void create(@NonNull String key, LocalizedErrorMessage value) {

            }

            @Override
            protected void delete(@NonNull String key) {

            }

            @Override
            protected void update(@NonNull String key, LocalizedErrorMessage value) {

            }

            @Override
            protected String get(@NonNull String key) {
                return "";
            }
        });
    }

    @Test
    @DisplayName("测试处理业务异常")
    void testProcessBusinessException() {
        BusinessException exception = new BusinessException(SystemErrorCode.SYSTEM_INTERNAL_ERROR);

        Result<Void> result = exceptionProcessor.process(exception, Locale.getDefault().toString());

        assertNotNull(result);
        // 使用error方法检查结果
        assertFalse(result.isSuccess());
        assertEquals(String.valueOf(SystemErrorCode.SYSTEM_INTERNAL_ERROR.code()), result.getErrorCode());
    }

    @Test
    @DisplayName("测试处理带自定义消息的业务异常")
    void testProcessBusinessExceptionWithCustomMessage() {
        BusinessException exception = new BusinessException(SystemErrorCode.UNAUTHENTICATED, "自定义错误信息");

        Result<Void> result = exceptionProcessor.process(exception, Locale.getDefault().toString());

        assertNotNull(result);
        assertFalse(result.isSuccess());
        assertEquals(String.valueOf(SystemErrorCode.UNAUTHENTICATED.code()), result.getErrorCode());
        assertTrue(result.getErrorMessage().contains("自定义错误信息"));
    }
}
