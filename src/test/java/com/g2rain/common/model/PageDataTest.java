package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("分页数据类测试")
class PageDataTest {

    @Test
    @DisplayName("测试分页数据创建")
    void testPageDataCreation() {
        List<String> records = List.of("record1", "record2", "record3");
        PageData<String> pageData = PageData.of(1, 10, 100, records);

        assertEquals(1, pageData.getPageNum());
        assertEquals(10, pageData.getPageSize());
        assertEquals(100, pageData.getTotal());
        assertEquals(3, pageData.getRecords().size());
        assertEquals("record1", pageData.getRecords().get(0));
        assertEquals("record2", pageData.getRecords().get(1));
        assertEquals("record3", pageData.getRecords().get(2));
    }

    @Test
    @DisplayName("测试分页数据创建方法")
    void testPageDataOf() {
        List<String> records = List.of("record1", "record2");
        PageData<String> pageData = PageData.of(2, 5, 20, records);

        assertEquals(2, pageData.getPageNum());
        assertEquals(5, pageData.getPageSize());
        assertEquals(20, pageData.getTotal());
        assertEquals(2, pageData.getRecords().size());
    }

    @Test
    @DisplayName("测试分页数据计算")
    void testPageDataCalculations() {
        List<String> records = List.of("record1", "record2");
        PageData<String> pageData = PageData.of(2, 5, 20, records);

        assertEquals(4, pageData.getTotalPages()); // ceil(20/5)
    }

    @Test
    @DisplayName("测试空记录情况")
    void testEmptyRecords() {
        PageData<String> pageData = PageData.of(1, 10, 0, List.of());

        assertEquals(1, pageData.getPageNum());
        assertEquals(10, pageData.getPageSize());
        assertEquals(0, pageData.getTotal());
        assertEquals(0, pageData.getRecords().size());
        assertEquals(0, pageData.getTotalPages());
    }
}
