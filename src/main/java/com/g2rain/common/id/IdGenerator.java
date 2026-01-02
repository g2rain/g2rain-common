package com.g2rain.common.id;

/**
 * id生成器，具体实现的方式封装在starter中，实际使用时通过引入starter完成该接口的实现
 *
 * @author jagger
 */
public interface IdGenerator {
    /**
     * 根据默认标签, 生成一个 Long 类型的唯一ID
     *
     * @return 唯一标识 ID
     */
    Long generateId();

    /**
     * 根据业务标签, 生成一个 Long 类型的唯一ID
     *
     * @return 唯一标识 ID
     */
    Long generateId(String bizTag);

    /**
     * 实现雪花算法, 生成一个 Long 类型的唯一ID
     *
     * @return 唯一标识 ID
     */
    Long generateSnowflakeId();
}
