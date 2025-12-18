package com.g2rain.common.id;

/**
 * id生成器，具体实现的方式封装在starter中，实际使用时通过引入starter完成该接口的实现
 *
 * @author jagger
 */
public interface IdGenerator {
    /**
     * 生成一个Long类型的唯一ID
     *
     * @return 唯一标识ID（适配雪花算法字符串形式、UUID、分布式发号器等实现）
     */
    Long generateId();
}
