package com.g2rain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Vo 类的基础类
 *
 * @author jagger
 */
@Data
@Schema(description = "Vo 基础类")
public class BaseVo {
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

    /**
     * 默认构造函数
     */
    public BaseVo() {
        // no-op
    }
}
