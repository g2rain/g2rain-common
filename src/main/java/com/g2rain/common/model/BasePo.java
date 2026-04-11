package com.g2rain.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Po 类的基础类
 *
 * @author jagger
 */
@Data
@Schema(description = "Po 基础类")
public class BasePo {
    /**
     * 主键
     */
    @Schema(description = "主键标识")
    private Long id;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", type = "string", format = "date-time", example = "2026-01-07 12:00:00")
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", type = "string", format = "date-time", example = "2026-01-07 12:00:00")
    private LocalDateTime createTime;

    /**
     * 版本号，支持乐观锁机制，数据更新时自增1
     */
    @Schema(description = "版本号")
    private Integer version;
}
