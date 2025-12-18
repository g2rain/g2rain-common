package com.g2rain.common.json;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于条件性地忽略 JSON 序列化中的字段。当此注解应用于字段时，
 * 在默认情况下该字段将被忽略。但当满足特定条件（如 adminCompany=true）时，
 * 该字段将会被包含在 JSON 输出中。
 *
 * @author jagger
 * @since 2025/11/5-23:50
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotation
public @interface ConditionalJsonIgnore {
    /**
     * 当 adminCompany 为 TRUE 时，注解的字段将被包含在 JSON 序列化输出中。
     * 默认值为 UNSPECIFIED，表示该字段无意义（默认情况下该字段将被忽略）。
     */
    AdminCompanyCondition adminCompany() default AdminCompanyCondition.UNSPECIFIED;
}
