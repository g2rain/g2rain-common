package com.g2rain.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * SortItem 表示单个排序条件, 用于多字段排序
 * <p>
 * 主要功能：
 * <ul>
 *     <li>column：排序列名, 对应 DTO 属性或数据库字段</li>
 *     <li>direction：排序方向, 限定 "ASC" 或 "DESC"</li>
 *     <li>提供 {@link Direction#safeOf(String)} 方法对非法 direction 值进行安全转换</li>
 * </ul>
 * <p>
 * 注意：direction 字段建议通过 {@link Direction#safeOf(String)} 校验, 确保传入合法值
 *
 * @author alpha
 * @since 2026/01/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortItem {

    /**
     * 排序列名, 对应 DTO 属性或数据库字段
     */
    private String column;

    /**
     * 排序方向, "ASC" 或 "DESC"
     * <p>
     * 非法值可以通过 {@link Direction#safeOf(String)} 转换为默认值 ASC
     */
    private String direction;

    /**
     * 排序方向枚举, 用于方向校验和安全转换
     */
    public enum Direction {
        /**
         * 升序
         */
        ASC,

        /**
         * 降序
         */
        DESC;

        /**
         * 安全转换：前端传入非法值返回默认 ASC
         * <p>
         * 示例：
         * <pre>{@code
         * SortItem.Direction.safeOf("DESC"); // 返回 "DESC"
         * SortItem.Direction.safeOf("asc");  // 返回 "ASC"
         * SortItem.Direction.safeOf("xxx");  // 非法值, 返回 "ASC"
         * SortItem.Direction.safeOf(null);   // null, 返回 "ASC"
         * }</pre>
         *
         * @param value 前端传入的方向字符串
         * @return 校验后的方向字符串, 保证只返回 "ASC" 或 "DESC"
         */
        public static String safeOf(String value) {
            return DESC.name().equalsIgnoreCase(value) ? DESC.name() : ASC.name();
        }
    }
}
