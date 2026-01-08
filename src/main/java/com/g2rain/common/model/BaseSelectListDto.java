package com.g2rain.common.model;

import com.g2rain.common.utils.Collections;
import com.g2rain.common.utils.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 查询列表的基础入参 DTO, 主要用于分页、过滤和多字段排序
 * <p>
 * 主要功能：
 * <ul>
 *     <li>支持按主键单个或集合查询</li>
 *     <li>支持创建时间段和更新时间段的区间过滤</li>
 *     <li>支持多字段排序, 自动校验排序方向</li>
 * </ul>
 * <p>
 * 所有集合在为空时返回不可变空集合, 保证调用安全
 *
 * @author jagger
 * @since 2026/01/07
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseSelectListDto {

    /**
     * 主键, 用于精确查询单条记录
     */
    @Setter
    @Getter
    private Long id;

    /**
     * 主键集合, 用于批量查询
     * <p>
     * 调用 {@link #addId(Long)} 可向集合添加元素
     */
    @Setter
    @Getter
    private Set<Long> ids;

    /**
     * 更新时间段, 最多两个元素有意义
     * <ul>
     *     <li>第一个元素：开始时间</li>
     *     <li>第二个元素：结束时间</li>
     * </ul>
     * 格式按前端约定, 通常为 ISO 字符串
     */
    @Setter
    @Getter
    private List<String> updateTime;

    /**
     * 创建时间段, 最多两个元素有意义
     * <ul>
     *     <li>第一个元素：开始时间</li>
     *     <li>第二个元素：结束时间</li>
     * </ul>
     * 格式按前端约定, 通常为 ISO 字符串
     */
    @Setter
    @Getter
    private List<String> createTime;

    /**
     * 多字段排序参数列表（前端传入, JPA 风格）
     * <p>
     * 每个元素格式为 {@code column,direction}, 例如：
     * <ul>
     *     <li>{@code id,desc}</li>
     *     <li>{@code createTime,asc}</li>
     * </ul>
     * 多字段排序可以传多个元素, 顺序即排序优先级。
     * <p>
     * 注意：
     * <ul>
     *     <li>column 对应 DTO 属性或数据库字段</li>
     *     <li>direction 可为 "asc" 或 "desc", 不区分大小写</li>
     * </ul>
     */
    @Setter
    private List<String> sorts;

    /**
     * 内部使用：将前端传入的排序字符串列表解析为 SortItem 列表
     * <p>
     * 前端传入 sorts, 每个元素格式为 "column,direction", 例如：
     * <ul>
     *     <li>"id,desc"</li>
     *     <li>"createTime,asc"</li>
     * </ul>
     * <p>
     * 返回的列表只包含 column 非空的排序项, direction 不合法自动转为 ASC
     *
     * @return 解析后的安全排序列表, 如果前端未传或列表为空, 则返回空列表
     */
    public List<SortItem> getSafeSorts() {
        // 如果前端未传或者列表为空, 直接返回不可变空列表
        if (Collections.isEmpty(this.sorts)) {
            return List.of();
        }

        // 初始化结果列表, 容量与前端传入列表一致, 避免多次扩容
        List<SortItem> result = new ArrayList<>(this.sorts.size());

        // 遍历前端传入的每个排序字符串
        for (String sort : this.sorts) {
            // 不处理空字符串
            if (Strings.isBlank(sort)) {
                continue;
            }

            // 找到逗号位置, 逗号之前是 column, 逗号之后是 direction
            int commaIndex = sort.indexOf(',');
            String column = commaIndex >= 0 ? sort.substring(0, commaIndex).trim() : sort.trim();

            // column 非空才加入结果, 空 column 跳过
            if (Strings.isNotBlank(column)) {
                // 如果 direction 未传或空, 后续安全处理会自动转为 ASC
                String direction = commaIndex >= 0 ? sort.substring(commaIndex + 1).trim() : null;

                // 创建 SortItem 并加入结果列表
                result.add(new SortItem(column, SortItem.Direction.safeOf(direction)));
            }
        }

        return result;
    }

    /**
     * 向 {@link #ids} 集合中添加元素
     * <p>
     * 如果集合为空则自动初始化。
     *
     * @param id 要添加的主键值, null 值会被忽略
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
