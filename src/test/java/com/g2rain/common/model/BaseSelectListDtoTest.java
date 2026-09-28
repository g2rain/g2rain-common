package com.g2rain.common.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("基础查询列表DTO测试")
class BaseSelectListDtoTest {

    @Test
    @DisplayName("测试基础查询列表DTO属性")
    void testBaseSelectListDtoProperties() {
        BaseSelectListDto dto = new BaseSelectListDto();

        // 测试默认值
        assertNull(dto.getId());
        assertNull(dto.getIds());
        assertNull(dto.getUpdateTime());
        assertNull(dto.getCreateTime());

        // 测试设置和获取属性
        dto.setId(1L);
        dto.setIds(Set.of(1L, 2L, 3L));
        dto.setUpdateTime(List.of("2023-10-15 14:30:45", "2023-10-15 15:30:45"));
        dto.setCreateTime(List.of("2023-10-15 14:30:45", "2023-10-15 15:30:45"));

        assertEquals(1L, dto.getId());
        assertEquals(3, dto.getIds().size());
        assertEquals(2, dto.getUpdateTime().size());
        assertEquals(2, dto.getCreateTime().size());
    }

    @Test
    @DisplayName("测试添加ID方法")
    void testAddId() {
        BaseSelectListDto dto = new BaseSelectListDto();

        // 测试添加null ID
        dto.addId(null);
        assertNull(dto.getIds());

        // 测试添加第一个ID
        dto.addId(1L);
        assertNotNull(dto.getIds());
        assertEquals(1, dto.getIds().size());
        assertTrue(dto.getIds().contains(1L));

        // 测试添加更多ID
        dto.addId(2L);
        dto.addId(3L);
        assertEquals(3, dto.getIds().size());
        assertTrue(dto.getIds().contains(2L));
        assertTrue(dto.getIds().contains(3L));
    }

    @Test
    @DisplayName("sorts 为空时默认按 id 降序")
    void testGetSafeSortsDefaultWhenEmpty() {
        BaseSelectListDto dto = new BaseSelectListDto();

        List<SortItem> nullSorts = dto.getSafeSorts();
        assertEquals(1, nullSorts.size());
        assertEquals("id", nullSorts.getFirst().getColumn());
        assertEquals(SortItem.Direction.DESC.name(), nullSorts.getFirst().getDirection());

        dto.setSorts(List.of());
        List<SortItem> emptySorts = dto.getSafeSorts();
        assertEquals(1, emptySorts.size());
        assertEquals("id", emptySorts.getFirst().getColumn());
        assertEquals(SortItem.Direction.DESC.name(), emptySorts.getFirst().getDirection());
    }

    @Test
    @DisplayName("sorts 有值时按传入内容解析")
    void testGetSafeSortsParsesInput() {
        BaseSelectListDto dto = new BaseSelectListDto();
        dto.setSorts(List.of("createTime,asc", "id,desc"));

        List<SortItem> sorts = dto.getSafeSorts();
        assertEquals(2, sorts.size());
        assertEquals("createTime", sorts.getFirst().getColumn());
        assertEquals(SortItem.Direction.ASC.name(), sorts.getFirst().getDirection());
        assertEquals("id", sorts.get(1).getColumn());
        assertEquals(SortItem.Direction.DESC.name(), sorts.get(1).getDirection());
    }
}
