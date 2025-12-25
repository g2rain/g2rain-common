package com.g2rain.common.model;

import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 查询列表的基础入参，主要包括id，创建时间段和结束时间端
 *
 * @author jagger
 */
@Data
public class BaseSelectListDto {

    /**
     * 主键
     */
    private Long id;

    /**
     * 主键集合
     */
    private Set<Long> ids;

    /**
     * 更新时间段，最多前两个元素有意义，第一个为开始时间，第二个为结束时间
     */
    private List<String> updateTime;

    /**
     * 创建时间段，最多前两个元素有意义，第一个为开始时间，第二个为结束时间
     */
    private List<String> createTime;

    /**
     * 像ids集合中添加元素
     */
    public void addId(Long id) {
        if (Objects.isNull(id)) {
            return;
        }

        if (Objects.isNull(ids)) {
            ids = new HashSet<>();
        }

        this.ids.add(id);
    }
}
