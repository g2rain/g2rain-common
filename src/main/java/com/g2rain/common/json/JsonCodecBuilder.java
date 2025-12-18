package com.g2rain.common.json;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.g2rain.common.utils.Moments;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.node.JsonNodeFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.function.Consumer;

/**
 * <p>{@code JsonCodecBuilder} 是 {@link JsonCodec} 的构建器类。</p>
 * <p>
 * 提供链式配置 {@link JsonMapper} 的方法，方便自定义序列化与反序列化规则，
 * 同时提供默认配置以满足大多数场景。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * JsonCodec codec = JsonCodecBuilder.builder()
 *     .withDefaults()
 *     .withConfig(mapper -> {
 *         mapper.enable(SerializationFeature.INDENT_OUTPUT);
 *     })
 *     .build();
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class JsonCodecBuilder {

    /**
     * Jackson JSON 处理对象
     */
    private final JsonMapper.Builder builder;

    /**
     * 私有构造，防止外部直接实例化。
     */
    private JsonCodecBuilder() {
        this.builder = JsonMapper.builder();
    }

    /**
     * 创建 {@code JsonCodecBuilder} 实例。
     *
     * @return 新的 {@code JsonCodecBuilder} 实例
     */
    public static JsonCodecBuilder builder() {
        return new JsonCodecBuilder();
    }

    /**
     * 对 {@link JsonMapper} 进行自定义配置。
     *
     * @param configurer {@link Consumer} 接口，接收 {@link JsonMapper} 进行配置
     * @return 当前 {@code JsonCodecBuilder} 实例，支持链式调用
     */
    public JsonCodecBuilder withConfig(Consumer<JsonMapper.Builder> configurer) {
        configurer.accept(this.builder);
        return this;
    }

    /**
     * 应用默认配置到 {@link JsonMapper}。
     * <p>
     * 默认配置包括：
     * <ol>
     *     <li>全局包含所有字段（包括 null 值）。</li>
     *     <li>禁止空 Bean 报错。</li>
     *     <li>忽略未知字段。</li>
     *     <li>禁止默认类型反序列化（安全性）。</li>
     *     <li>允许 JSON 含有注释。</li>
     *     <li>设置默认区域和时区。</li>
     *     <li>BigDecimal 输出为普通格式。</li>
     *     <li>大数字优先作为 BigDecimal 处理。</li>
     *     <li>JsonNode 精度配置。</li>
     *     <li>空字符串视为 null 对象。</li>
     *     <li>默认日期格式。</li>
     *     <li>性能优化：禁用循环引用检测。</li>
     *     <li>禁止缩进，输出紧凑 JSON。</li>
     * </ol>
     *
     * @return 当前 {@code JsonCodecBuilder} 实例，支持链式调用
     */
    public JsonCodecBuilder withDefaults() {
        // 1. 全局包含所有字段（包括 null 值）
        // 类型：序列化
        // 覆盖规则：字段级 @JsonInclude 会覆盖此配置
        this.builder.changeDefaultPropertyInclusion(old -> JsonInclude.Value.construct(
            JsonInclude.Include.ALWAYS, JsonInclude.Include.ALWAYS)
        );

        // 2. 禁止空 Bean 报错
        // 类型：序列化
        this.builder.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // 3. 忽略未知字段
        // 类型：反序列化
        // 示例：JSON {"x":1} 反序列化到对象没有 x 字段时不会报错
        this.builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 4. 安全：禁止默认类型反序列化
        // 类型：反序列化
        // 功能：防止反序列化漏洞
        // 示例：禁止反序列化未显式声明的类型
        this.builder.deactivateDefaultTyping();

        // 5. 允许 JSON 含有注释
        // 类型：反序列化
        // 示例：{"name":"张三" /*注释*/} 正常反序列化
        this.builder.enable(JsonReadFeature.ALLOW_JAVA_COMMENTS);

        // 6. 设置默认区域
        // 类型：序列化 + 反序列化
        // 示例：日期、数字格式化遵循系统默认区域
        this.builder.defaultLocale(Locale.getDefault());

        // 7. 设置默认时区
        // 类型：序列化 + 反序列化
        // 示例：日期序列化使用系统默认时区
        this.builder.defaultTimeZone(TimeZone.getDefault());

        // 8. BigDecimal 输出为普通格式（不使用科学计数法）
        // 类型：序列化
        // 示例：123456789.123456 → "123456789.123456"
        this.builder.addModule(new SimpleModule("BigDecimalPlainModule").addSerializer(BigDecimal.class, new ValueSerializer<>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializationContext ctx) throws JacksonException {
                if (Objects.isNull(value)) {
                    gen.writeNull();
                    return;
                }

                gen.writeRawValue(value.toPlainString());
            }
        }));

        // 9. 大数字优先作为 BigDecimal 处理
        // 类型：反序列化
        // 示例：浮点数 0.1 精确为 0.1
        this.builder.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        // 10. JsonNode 精度配置
        // 类型：反序列化
        // 示例：保留精度
        this.builder.nodeFactory(JsonNodeFactory.instance);

        // 11. 空字符串视为 null 对象
        // 类型：反序列化
        // 示例："" → null
        this.builder.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // 12. 默认日期格式, 旧版本的Date, 希望开发者不要使用这样的类, 改用高版本的日期时间系列
        // 类型：序列化 + 反序列化
        // 覆盖规则：字段级 @JsonFormat 会覆盖
        // 示例：Date date = new Date() → "2025-09-26 15:30:45"
        this.builder.defaultDateFormat(new SimpleDateFormat(Moments.DEFAULT_PATTERN));

        // 13. 性能优化：禁用循环引用检测（视场景使用）
        // 类型：序列化
        // 示例：对象自引用 → 正常序列化（不抛异常）
        this.builder.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);

        // 14. 禁止缩进，输出紧凑 JSON
        // 类型：序列化
        // 示例：保证 JSON 输出内容紧凑
        this.builder.configure(SerializationFeature.INDENT_OUTPUT, false);

        // 15. 允许单引号
        this.builder.enable(JsonReadFeature.ALLOW_SINGLE_QUOTES);
        return this;
    }

    /**
     * 构建 {@link JsonCodec} 实例。
     *
     * @return 配置好的 {@code JsonCodec} 实例
     */
    public JsonCodec build() {
        return new JsonCodec(this.builder.build());
    }
}
