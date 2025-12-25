package com.g2rain.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * Vo类的基础类
 *
 * @author jagger
 */
@Data
public class BaseVo {
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

    /**
     * 默认构造函数
     */
    public BaseVo() {
    }
}
