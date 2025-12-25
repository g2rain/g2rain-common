package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("基础分页查询DTO测试")
class BasePageSelectListDtoTest {

    @Test
    @DisplayName("测试默认页面大小")
    void testDefaultPageSize() {
        assertEquals(10, BasePageSelectListDto.DEFAULT_PAGE_SIZE);
    }

    @Test
    @DisplayName("测试分页属性")
    void testPageProperties() {
        BasePageSelectListDto dto = new BasePageSelectListDto();

        // 测试设置和获取属性
        dto.setPageNum(1);
        dto.setPageSize(20);

        assertEquals(1, dto.getPageNum());
        assertEquals(20, dto.getPageSize());
    }

    @Test
    @DisplayName("测试获取偏移量")
    void testGetOffset() {
        BasePageSelectListDto dto = new BasePageSelectListDto();

        // 测试小于1的页码
        dto.setPageNum(0);
        assertEquals(0, dto.getOffset());

        // 测试正常页码
        dto.setPageNum(1);
        assertEquals(0, dto.getOffset());

        dto.setPageNum(2);
        assertEquals(10, dto.getOffset());

        dto.setPageNum(3);
        dto.setPageSize(20);
        assertEquals(40, dto.getOffset());
    }

    @Test
    @DisplayName("测试获取限制数")
    void testGetLimit() {
        BasePageSelectListDto dto = new BasePageSelectListDto();

        // 测试小于等于0的页面大小
        dto.setPageSize(0);
        assertEquals(BasePageSelectListDto.DEFAULT_PAGE_SIZE, dto.getLimit());

        dto.setPageSize(-1);
        assertEquals(BasePageSelectListDto.DEFAULT_PAGE_SIZE, dto.getLimit());

        // 测试正常页面大小
        dto.setPageSize(20);
        assertEquals(20, dto.getLimit());
    }
}
