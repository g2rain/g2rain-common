package com.g2rain.common.exception;

import com.g2rain.common.model.Result;
import com.g2rain.common.web.PrincipalContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("异常处理器测试")
class ExceptionProcessorTest {

    @BeforeEach
    void setUp() {
        PrincipalContextHolder.setRequestId("test-request-id");
        PrincipalContextHolder.setRequestTime("2023-10-15 14:30:45");
    }

    @AfterEach
    void tearDown() {
        PrincipalContextHolder.remove();
    }

    @Test
    @DisplayName("测试默认结果转换方法")
    void testToResult() {
        // 创建一个简单的实现来测试接口的默认方法
        ExceptionProcessor processor = new ExceptionProcessor() {
            @Override
            public Result<Void> process(BusinessException ex, String acceptLanguage) {
                return null;
            }
        };

        BusinessException exception = new BusinessException(SystemErrorCode.JSON_SERIALIZER_ERROR);
        Result<Void> result = processor.toResult(exception);

        assertNotNull(result);
        assertEquals("system.50006", result.getErrorCode());
        assertEquals("数据序列化失败", result.getErrorMessage());
        assertEquals("test-request-id", result.getRequestId());
        assertEquals("2023-10-15 14:30:45", result.getRequestTime());
    }
}
