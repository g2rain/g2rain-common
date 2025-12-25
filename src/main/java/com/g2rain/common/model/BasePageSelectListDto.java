package com.g2rain.common.model;

import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 分页查询参数的基础类
 *
 * <p>该类继承自BaseSelectListDto，增加了分页相关的属性和方法。
 * 主要用于支持分页查询功能，包含页码、页面大小以及计算偏移量和限制数的方法。
 *
 * <p>主要功能：
 * <ul>
 *   <li>提供当前页码和每页条数的属性</li>
 *   <li>提供默认页面大小（10条）</li>
 *   <li>提供计算数据库查询偏移量的方法</li>
 *   <li>提供获取查询限制数的方法</li>
 * </ul>
 *
 * @author jagger
 */
@EqualsAndHashCode(callSuper = true)
@Setter
public class BasePageSelectListDto extends BaseSelectListDto {
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 当前页码，最小页码为
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    public int getPageNum() {
        return Math.max(pageNum, 1);
    }

    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 计算分页查询的偏移量
     *
     * <p>偏移量计算公式：(页码 - 1) * 每页条数
     * <ul>
     *   <li>如果pageNum为null或小于1，则返回0</li>
     *   <li>否则返回(pageNum - 1) * limit</li>
     * </ul>
     *
     * <p>使用示例：
     * <pre>{@code
     * BasePageSelectListDto dto = new BasePageSelectListDto();
     * dto.setPageNum(3);
     * dto.setPageSize(10);
     * int offset = dto.getOffset(); // 返回20
     * }</pre>
     *
     * @return 分页查询的偏移量
     */
    public int getOffset() {
        return (getPageNum() - 1) * getLimit();
    }

    /**
     * 获取分页查询的限制数（每页条数）
     *
     * <p>限制数逻辑：
     * <ul>
     *   <li>如果pageSize为null或小于等于0，则使用默认页面大小（10）</li>
     *   <li>否则返回pageSize的值</li>
     * </ul>
     *
     * <p>使用示例：
     * <pre>{@code
     * BasePageSelectListDto dto = new BasePageSelectListDto();
     * dto.setPageSize(20);
     * int limit = dto.getLimit(); // 返回20
     *
     * BasePageSelectListDto dto2 = new BasePageSelectListDto();
     * int limit2 = dto2.getLimit(); // 返回默认值10
     * }</pre>
     *
     * @return 分页查询的限制数
     */
    public int getLimit() {
        return getPageSize();
    }
}
