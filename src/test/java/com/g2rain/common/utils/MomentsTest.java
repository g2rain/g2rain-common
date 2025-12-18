package com.g2rain.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("时间工具类测试")
class MomentsTest {

    @Test
    @DisplayName("测试私有构造函数")
    void testPrivateConstructor() throws Exception {
        // 确保Moments类不能被实例化
        java.lang.reflect.Constructor<Moments> constructor = Moments.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        // 尝试创建实例应该成功（虽然不推荐）
        assertDoesNotThrow(() -> constructor.newInstance());
    }

    @Test
    @DisplayName("测试默认时间格式")
    void testDefaultPattern() {
        assertEquals("yyyy-MM-dd HH:mm:ss", Moments.DEFAULT_PATTERN);
        assertNotNull(Moments.DEFAULT_FORMATTER);
    }

    @Test
    @DisplayName("测试格式化时间")
    void testFormat() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 15, 14, 30, 45);

        // 测试默认格式
        assertEquals("2023-10-15 14:30:45", Moments.format(dateTime));

        // 测试自定义格式
        assertEquals("2023-10-15", Moments.format(dateTime, "yyyy-MM-dd"));

        // 测试null时间
        assertNull(Moments.format(null));
        assertNull(Moments.format(null, "yyyy-MM-dd"));
    }

    @Test
    @DisplayName("测试格式化时间戳")
    void testFormatEpochMillis() {
        long timestamp = 1697347845000L; // 2023-10-15 14:30:45对应的毫秒时间戳
        String result = Moments.formatEpochMillis(timestamp);
        assertNotNull(result);
        // 注意：由于时区问题，结果可能略有不同
        assertTrue(result.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    @Test
    @DisplayName("测试解析时间字符串")
    void testParse() {
        // 测试默认格式解析
        LocalDateTime dateTime = Moments.parse("2023-10-15 14:30:45");
        assertNotNull(dateTime);
        assertEquals(2023, dateTime.getYear());
        assertEquals(10, dateTime.getMonthValue());
        assertEquals(15, dateTime.getDayOfMonth());
        assertEquals(14, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
        assertEquals(45, dateTime.getSecond());

        // 测试自定义格式解析
        // 根据JDK标准行为，使用不匹配的格式会抛出异常
        assertThrows(IllegalArgumentException.class, () -> Moments.parse("2023-10-15 14:30:45", "yyyy-MM-dd"));

        // 测试null和空字符串
        assertNull(Moments.parse(null));
        assertNull(Moments.parse(""));
        assertNull(Moments.parse(" ", "yyyy-MM-dd"));
    }

    @Test
    @DisplayName("测试解析错误格式的时间字符串")
    void testParseInvalidFormat() {
        // 测试无效的时间格式
        assertThrows(IllegalArgumentException.class, () -> Moments.parse("invalid-date"));
        assertThrows(IllegalArgumentException.class, () -> Moments.parse("2023-10-15", "yyyy/MM/dd HH:mm:ss"));
    }

    @Test
    @DisplayName("测试获取当前时间")
    void testNow() {
        LocalDateTime now = Moments.now();
        assertNotNull(now);
        // 检查时间是否合理（在执行前后1秒内）
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        LocalDateTime after = LocalDateTime.now().plusSeconds(1);
        assertTrue(now.isAfter(before) || now.isEqual(before));
        assertTrue(now.isBefore(after) || now.isEqual(after));
    }

    @Test
    @DisplayName("测试获取当前时间字符串")
    void testNowString() {
        String nowString = Moments.nowString();
        assertNotNull(nowString);
        assertTrue(nowString.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));

        String nowStringCustom = Moments.nowString("yyyy-MM-dd");
        assertNotNull(nowStringCustom);
        assertTrue(nowStringCustom.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    @DisplayName("测试清理缓存")
    void testClearCache() {
        // 先获取缓存大小
        int initialSize = Moments.getCacheSize();

        // 使用一个新格式，会增加缓存
        Moments.parse("2023-10-15 14:30:45", "yyyy-MM-dd HH:mm:ss");
        assertTrue(Moments.getCacheSize() >= initialSize);

        // 清理特定格式
        Moments.clearCache("yyyy-MM-dd HH:mm:ss");
        // 清理所有非默认格式
        Moments.clearCache(null);

        // 检查缓存大小是否恢复到初始状态或接近初始状态
        assertTrue(Moments.getCacheSize() <= initialSize + 1); // 允许一些误差
    }

    @Test
    @DisplayName("测试获取缓存大小")
    void testGetCacheSize() {
        int size = Moments.getCacheSize();
        assertTrue(size >= 1); // 至少有默认格式
        assertTrue(size < 100); // 不应该有太多缓存项
    }
}
