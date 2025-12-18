package com.g2rain.common.json;


import com.g2rain.common.web.PrincipalContextHolder;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.introspect.AnnotatedMember;
import tools.jackson.databind.ser.BeanPropertyWriter;

import java.util.Objects;

/**
 * ConditionalPropertyWriter 是 BeanPropertyWriter 的包装器类，用于在序列化
 * Java 对象为 JSON 时，根据特定条件动态地决定是否序列化某个字段。
 * <p>
 * 该类主要用于配合 {@link ConditionalJsonIgnore} 注解，当字段被该注解标记时，
 * 可以在序列化时根据上下文（如当前用户是否为管理员）决定是否输出该字段。
 * <p>
 * 核心逻辑：
 * <ul>
 *     <li>通过委托模式持有原始的 BeanPropertyWriter（delegate），</li>
 *     <li>在序列化字段前获取字段或 getter 方法的注解信息，</li>
 *     <li>根据注解中定义的条件和上下文判断是否调用 delegate 来序列化字段。</li>
 * </ul>
 * <p>
 * 该类可以用于 Jackson 的 BeanSerializerModifier 中，通过替换默认的 BeanPropertyWriter
 * 来实现动态字段过滤。
 *
 * @author alpha
 * @since 2025/12/14
 */
public class ConditionalPropertyWriter extends BeanPropertyWriter {

    /**
     * 委托的原始 BeanPropertyWriter 对象，负责实际的字段序列化操作。
     */
    private final BeanPropertyWriter delegate;

    /**
     * 构造方法，用于创建 ConditionalPropertyWriter 包装器。
     *
     * @param delegate 原始的 BeanPropertyWriter，对其进行包装以添加条件逻辑
     */
    public ConditionalPropertyWriter(BeanPropertyWriter delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    /**
     * 根据条件动态序列化字段。
     * <p>
     * 序列化逻辑如下：
     * <ol>
     *     <li>通过 delegate 获取字段或 getter 方法的 AnnotatedMember。</li>
     *     <li>如果字段没有 AnnotatedMember 或者没有 {@link ConditionalJsonIgnore} 注解，直接调用 delegate 序列化。</li>
     *     <li>如果字段被 {@link ConditionalJsonIgnore} 注解标记，根据注解的 {@link AdminCompanyCondition} 和
     *     {@link PrincipalContextHolder} 的上下文判断是否序列化字段。</li>
     * </ol>
     *
     * @param bean 序列化的对象
     * @param gen  Jackson JsonGenerator，用于输出 JSON 内容
     * @param ctx  Jackson SerializationContext，序列化上下文
     * @throws Exception 序列化过程中可能抛出的异常
     */
    @Override
    public void serializeAsProperty(Object bean, JsonGenerator gen, SerializationContext ctx) throws Exception {
        // 获取字段或 getter 方法对应的注解信息
        AnnotatedMember member = delegate.getMember();

        // 如果 member 为 null，则直接调用原始序列化逻辑
        if (Objects.isNull(member)) {
            // 满足条件时，调用 delegate 执行实际序列化
            delegate.serializeAsProperty(bean, gen, ctx);
            return;
        }

        // 获取字段上可能存在的 ConditionalJsonIgnore 注解
        ConditionalJsonIgnore ann = member.getAnnotation(ConditionalJsonIgnore.class);

        // 如果注解不存在，则直接调用原始序列化逻辑
        if (Objects.isNull(ann)) {
            // 满足条件时，调用 delegate 执行实际序列化
            delegate.serializeAsProperty(bean, gen, ctx);
            return;
        }

        // 根据注解条件和上下文动态判断是否序列化字段
        if (AdminCompanyCondition.TRUE.equals(ann.adminCompany()) && PrincipalContextHolder.isAdminCompany()) {
            // 满足条件时，调用 delegate 执行实际序列化
            delegate.serializeAsProperty(bean, gen, ctx);
        }

        // 如果条件不满足，则跳过该字段，不输出到 JSON
    }
}
