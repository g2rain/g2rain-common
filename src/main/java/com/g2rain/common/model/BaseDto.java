package com.g2rain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * DTO类的基础类，提供通用字段和转换方法
 *
 * <p>该类包含实体的基本属性，包括主键和时间戳字段。
 * 同时提供将DTO转换为PO对象的方法。
 *
 * <p>主要功能：
 * <ul>
 *   <li>提供统一的ID字段</li>
 *   <li>提供创建时间和更新时间的时间戳字段</li>
 *   <li>提供将DTO对象转换为PO对象的方法</li>
 * </ul>
 *
 * @author jagger
 */
@Data
public class BaseDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 更新时间（出参格式：yyyy-MM-dd HH:mm:ss）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

    /**
     * 创建时间（出参格式：yyyy-MM-dd HH:mm:ss）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
}
