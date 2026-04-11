package com.g2rain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO类的基础类，提供通用字段和转换方法
 *
 * <p>该类包含实体的基本属性，包括主键和时间戳字段。
 *
 * <p>主要功能：
 * <ul>
 *   <li>提供统一的 ID 字段</li>
 *   <li>提供创建时间和更新时间的时间戳字段</li>
 * </ul>
 *
 * @author jagger
 */
@Data
@Schema(description = "DTO 基础类")
public class BaseDto {
    /**
     * 主键
     */
    @Schema(description = "主键标识")
    private Long id;

    /**
     * 更新时间（出参格式：yyyy-MM-dd HH:mm:ss）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间(出参格式:yyyy-MM-dd HH:mm:ss)", example = "2026-01-07 12:00:00")
    private String updateTime;

    /**
     * 创建时间（出参格式：yyyy-MM-dd HH:mm:ss）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间(出参格式:yyyy-MM-dd HH:mm:ss)", example = "2026-01-07 12:00:00")
    private String createTime;
}
