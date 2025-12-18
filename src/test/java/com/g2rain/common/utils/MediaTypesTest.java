package com.g2rain.common.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("媒体类型工具类测试")
class MediaTypesTest {

    @Test
    @DisplayName("测试私有构造函数")
    void testPrivateConstructor() throws Exception {
        // 确保MediaTypes类不能被实例化
        java.lang.reflect.Constructor<MediaTypes> constructor = MediaTypes.class.getDeclaredConstructor();
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);

        // 尝试创建实例应该成功（虽然不推荐）
        assertDoesNotThrow(() -> constructor.newInstance());
    }

    @Test
    @DisplayName("测试是否为octet-stream类型")
    void testIsOctetStream() {
        assertTrue(MediaTypes.isOctetStream("application/octet-stream"));
        assertTrue(MediaTypes.isOctetStream("APPLICATION/OCTET-STREAM"));
        assertTrue(MediaTypes.isOctetStream("application/octet-stream; charset=utf-8"));
        assertFalse(MediaTypes.isOctetStream("application/json"));
        assertFalse(MediaTypes.isOctetStream(null));
        assertFalse(MediaTypes.isOctetStream(""));
    }

    @Test
    @DisplayName("测试是否为multipart/form-data类型")
    void testIsMultipartFormData() {
        assertTrue(MediaTypes.isMultipartFormData("multipart/form-data"));
        assertTrue(MediaTypes.isMultipartFormData("MULTIPART/FORM-DATA"));
        assertTrue(MediaTypes.isMultipartFormData("multipart/form-data; boundary=something"));
        assertFalse(MediaTypes.isMultipartFormData("application/json"));
        assertFalse(MediaTypes.isMultipartFormData(null));
        assertFalse(MediaTypes.isMultipartFormData(""));
    }

    @Test
    @DisplayName("测试是否为form urlencoded类型")
    void testIsFormUrlEncoded() {
        assertTrue(MediaTypes.isFormUrlEncoded("application/x-www-form-urlencoded"));
        assertTrue(MediaTypes.isFormUrlEncoded("APPLICATION/X-WWW-FORM-URLENCODED"));
        assertTrue(MediaTypes.isFormUrlEncoded("application/x-www-form-urlencoded; charset=utf-8"));
        assertFalse(MediaTypes.isFormUrlEncoded("application/json"));
        assertFalse(MediaTypes.isFormUrlEncoded(null));
        assertFalse(MediaTypes.isFormUrlEncoded(""));
    }

    @Test
    @DisplayName("测试是否为JSON类型")
    void testIsJson() {
        assertTrue(MediaTypes.isJson("application/json"));
        assertTrue(MediaTypes.isJson("APPLICATION/JSON"));
        assertTrue(MediaTypes.isJson("application/json; charset=utf-8"));
        assertFalse(MediaTypes.isJson("application/xml"));
        assertFalse(MediaTypes.isJson(null));
        assertFalse(MediaTypes.isJson(""));
    }
}
