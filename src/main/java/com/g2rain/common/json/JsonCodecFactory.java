package com.g2rain.common.json;


/**
 * <p>{@code JsonCodecFactory} 是 {@link JsonCodec} 的工厂类。</p>
 * <p>
 * 提供获取默认配置的 {@link JsonCodec} 实例的方法。
 * 默认实例基于 {@link JsonCodecBuilder} 的默认配置创建。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * JsonCodec codec = JsonCodecFactory.instance();
 * String json = codec.obj2str(myObject);
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public final class JsonCodecFactory {

    /**
     * 私有构造，防止外部实例化。
     */
    private JsonCodecFactory() {
        // 私有构造，防止实例化
    }

    /**
     * 默认的 {@link JsonCodec} 实例，使用默认配置构建
     */
    private static final JsonCodec DEFAULT_CODEC = JsonCodecBuilder.builder()
        .withDefaults()
        .build();

    /**
     * 获取默认配置的 {@link JsonCodec} 实例。
     *
     * @return 默认的 {@link JsonCodec} 实例
     */
    public static JsonCodec instance() {
        return DEFAULT_CODEC;
    }
}
