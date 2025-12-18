package com.g2rain.common.model;

import com.g2rain.common.exception.SystemErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("结果封装类测试")
class ResultTest {

    @Test
    @DisplayName("测试成功结果创建")
    void testSuccess() {
        Result<String> result = Result.success();
        assertTrue(result.isSuccess());
        assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试带数据的成功结果创建")
    void testSuccessWithData() {
        Result<String> result = Result.success("test data");
        assertTrue(result.isSuccess());
        assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        assertEquals("test data", result.getData());
    }

    @Test
    @DisplayName("测试错误结果创建")
    void testError() {
        Result<String> result = Result.error("ERROR_CODE", "error message");
        assertFalse(result.isSuccess());
        assertEquals(Result.STATUS_ERROR, result.getStatus());
        assertEquals("ERROR_CODE", result.getErrorCode());
        assertEquals("error message", result.getErrorMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试通过错误码创建错误结果")
    void testErrorWithErrorCode() {
        Result<String> result = Result.error(SystemErrorCode.JSON_SERIALIZER_ERROR);
        assertFalse(result.isSuccess());
        assertEquals(Result.STATUS_ERROR, result.getStatus());
        assertEquals(String.valueOf(SystemErrorCode.JSON_SERIALIZER_ERROR.code()), result.getErrorCode());
        assertEquals(SystemErrorCode.JSON_SERIALIZER_ERROR.messageTemplate(), result.getErrorMessage());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试带参数的错误结果创建")
    void testErrorWithArgs() {
        Result<String> result = Result.error("ERROR_CODE", "error message with {0}", "arg");
        assertFalse(result.isSuccess());
        assertEquals(Result.STATUS_ERROR, result.getStatus());
        assertEquals("ERROR_CODE", result.getErrorCode());
        assertEquals("error message with {0}", result.getErrorMessage());
        assertArrayEquals(new Object[]{"arg"}, result.getIndexArgs());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试分页结果创建")
    void testSuccessPage() {
        List<String> records = List.of("record1", "record2");
        Result<PageData<String>> result = Result.successPage(1, 10, 2, records);

        assertTrue(result.isSuccess());
        assertEquals(Result.STATUS_SUCCESS, result.getStatus());
        assertNotNull(result.getData());
        assertEquals(1, result.getData().getPageNum());
        assertEquals(10, result.getData().getPageSize());
        assertEquals(2, result.getData().getTotal());
        assertEquals(2, result.getData().getRecords().size());
    }
}
