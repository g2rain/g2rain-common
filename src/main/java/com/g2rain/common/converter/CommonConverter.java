package com.g2rain.common.converter;


import com.g2rain.common.utils.Moments;
import org.mapstruct.Named;

import java.time.LocalDateTime;

/**
 * 通用类型转换器。
 * <p>
 * 提供通用的类型转换方法，供其他 MapStruct 转换器复用。
 * 例如 LocalDateTime ↔ String 的转换。
 * </p>
 *
 * @author alpha
 * @since 2025/10/27
 */
public class CommonConverter {

    /**
     * 将 {@link LocalDateTime} 转换为字符串。
     *
     * @param time LocalDateTime 对象
     * @return 格式化后的字符串
     */
    @Named("localDateTimeToString")
    public String localDateTimeToString(LocalDateTime time) {
        return Moments.format(time);
    }

    /**
     * 将字符串转换为 {@link LocalDateTime}。
     *
     * @param time 时间字符串
     * @return LocalDateTime 对象
     */
    @Named("stringToLocalDateTime")
    public LocalDateTime stringToLocalDateTime(String time) {
        return Moments.parse(time);
    }
}
