package com.g2rain.common.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Po类的基础类
 *
 * @author jagger
 */
@Data
public class BasePo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 版本号，支持乐观锁机制，数据更新时自增1
     */
    private Integer version;
}
