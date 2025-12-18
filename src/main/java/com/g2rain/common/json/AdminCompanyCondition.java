package com.g2rain.common.json;

/**
 * 管理员公司条件枚举
 * 用于 {@link ConditionalJsonIgnore} 注解中指定条件状态
 *
 * @author jagger
 */
public enum AdminCompanyCondition {
    /**
     * 未指定（默认值，表示该字段无意义）
     */
    UNSPECIFIED,
    
    /**
     * 当 adminCompany 为 true 时，注解的字段将被包含在 JSON 序列化输出中
     */
    TRUE,
    
    /**
     * 当 adminCompany 为 false 时，该字段将被忽略
     */
    FALSE
}

