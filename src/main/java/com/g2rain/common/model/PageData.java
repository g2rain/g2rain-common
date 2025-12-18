package com.g2rain.common.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 分页数据封装类
 */
@Data
public class PageData<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -9078642928025518913L;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 数据列表
     */
    private transient List<T> records;

    /**
     * 私有化构造方法
     */
    private PageData() {
    }

    /**
     * 创建分页结果对象
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param total    总记录数
     * @param records  数据列表
     * @param <T>      数据类型
     * @return 分页结果对象
     */
    public static <T> PageData<T> of(int pageNum, int pageSize, long total, List<T> records) {
        PageData<T> result = new PageData<>();
        result.pageNum = pageNum;
        result.pageSize = pageSize;
        result.total = total;
        result.records = Objects.nonNull(records) ? records : Collections.emptyList();

        // 计算总页数
        result.totalPages = pageSize > 0 ? (int) Math.ceil((double) total / pageSize) : 0;

        return result;
    }
}
