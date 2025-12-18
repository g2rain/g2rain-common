package com.g2rain.common.exception;

/**
 * 系统模块专属错误码
 * 仅包含系统级别的错误定义
 */
public enum SystemErrorCode implements ErrorCode {
    // ===================== 客户端错误（40000-49999）=====================
    /**
     * 参数无效
     */
    PARAM_INVALID("system.40000", "参数无效"),

    /**
     * 参数不存在（如必填参数缺失）
     * 示例：{0:paramName} -> "用户ID" → "参数用户ID不能为空"
     */
    PARAM_REQUIRED("system.40001", "参数{0:paramName}不能为空"),

    /**
     * 参数超出范围（如年龄不能为负数）
     * 示例：{0:paramValue}, {1:paramLimit} -> "20", "30" → "段值:20 超过允许范围:30"
     */
    PARAM_EXCEEDS_RANGE("system.40002", "参数: {0:paramValue} 不符合要求, 有效范围: {1:paramRange}"),

    /**
     * 参数超出范围（如年龄不能为负数）
     * 示例：{0:paramValue}, {1:paramLimit} -> "20", "30" → "段值:20 超过允许范围:30"
     */
    PARAM_EXCEEDS_SIZE("system.40003", "参数: {0:paramValue} 不符合要求, 有效长度: {1:paramRange}"),

    /**
     * 参数格式无效（如手机号格式错误）
     * 示例：{0:paramValue}, {1:rule} → "手机号", "11位数字" → "参数手机号格式无效，需满足：11位数字"
     */
    PARAM_INVALID_FORMAT("system.40004", "参数: {0:paramValue}格式无效，需满足：{1:rule}"),

    /**
     * 参数类型不匹配
     * 字段 '{0:paramValue}' 类型错误, 期望类型: {1:requiredType}
     */
    PARAM_TYPE_MISMATCH("system.40005", "参数: {0:paramValue} 类型错误, 期望类型：{1:requiredType}"),

    /**
     * 请求的Header｜Cookie｜PathVariable
     */
    REQUEST_BINDING_ERROR("system.40006", "请求 [{0:uri}] 的 {1:type} 缺失或绑定异常: {2:name}"),

    /**
     * 请求的Header｜Cookie｜PathVariable
     */
    AUTH_CODE_INVALID("system.40007", "授权码: [{0:uri}] 无效"),

    /**
     * 参数：{0:param} 无效
     */
    PARAM_VAL_INVALID("system.40008", "参数：{0:param} 无效"),

    /**
     * 请求方法不支持（如POST接口用GET请求）
     * 示例：{0:method}, {1:uri} → "GET", "/api/user" → "不支持GET方法请求/api/user"
     */
    METHOD_NOT_SUPPORTED("system.40501", "请求 [{0:uri}] 的方法 '{1:method}' 不被支持, 可用方法: {2:supported}"),

    /**
     * 请求方法不支持（如POST接口用GET请求）
     * 示例：{0:method}, {1:uri} → "GET", "/api/user" → "不支持GET方法请求/api/user"
     */
    MEDIA_TYPE_NOT_ACCEPTABLE("system.40601", "请求 [{0:uri}] 的响应类型与客户端接受响应类型请求头不匹配, 可接受类型: {1:supported}"),

    /**
     * 请求体类型不支持（如POST接口用JSON请求）
     */
    MEDIA_TYPE_NOT_SUPPORTED("system.41501", "请求 [{0:uri}] 的请求体类型 '{1:contentType}' 不被支持, 可用类型: {2:supported}"),

    /**
     * 资源未找到（如查询ID不存在的用户）
     * 示例：{0:resource}, {1:id} → "用户", "10086" → "资源用户（ID:10086）不存在"
     */
    RESOURCE_NOT_FOUND("system.40401", "资源{0:resource}（ID:{1:id}）不存在"),

    /**
     * 未认证（如未登录访问需授权接口）
     * 示例：{0:message} → "请先登录" → "未认证：请先登录"
     */
    UNAUTHENTICATED("system.40101", "未认证：{0:message}"),

    /**
     * 权限不足（如普通用户访问管理员接口）
     * 示例：{0:role}, {1:resource} → "普通用户", "用户管理" → "权限不足：普通用户无法访问用户管理资源"
     */
    UNAUTHORIZED("system.40301", "权限不足：{0:role}无法访问{1:resource}资源"),

    // ===================== 服务器错误（50000-59999）=====================
    /**
     * 系统内部错误（如未知异常）
     * 示例：{0:errorDetail} → "数据库连接超时" → "系统内部错误：数据库连接超时"
     */
    SYSTEM_INTERNAL_ERROR("system.50001", "系统内部错误：{0:errorDetail}"),

    /**
     * 数据库操作异常（如SQL执行失败）
     * 示例：{0:operation}, {1:reason} → "查询", "表不存在" → "数据库操作异常：查询失败，原因：表不存在"
     */
    DATABASE_ERROR("system.50002", "数据库操作异常：{0:operation}失败，原因：{1:reason}"),

    /**
     * 远程服务调用失败（如调用第三方API超时）
     * 示例：{0:serviceName}, {1:error} → "支付服务", "连接超时" → "远程服务调用失败：支付服务（错误：连接超时）"
     */
    REMOTE_SERVICE_ERROR("system.50003", "远程服务调用失败：{0:serviceName}（错误：{1:error}）"),

    /**
     * 缓存操作异常（如Redis连接失败）
     * 示例：{0:operation}, {1:key} → "设置", "user:10086" → "缓存操作异常：设置user:10086失败"
     */
    CACHE_ERROR("system.50004", "缓存操作异常：{0:operation}{1:key}失败"),

    /**
     * JSON解析令牌不可用
     */
    DESERIALIZER_NOT_AVAILABLE("system.50005", "JSON解析令牌不可用"),

    /**
     * JSON序列化失败
     */
    JSON_SERIALIZER_ERROR("system.50006", "数据序列化失败"),

    /**
     * JWT 密钥对不存在
     */
    JWT_KEY_PAIR_NON_EXIST("system.50007", "创建JWT得密钥对不存在"),

    /**
     * 生成JWT失败
     */
    GENERATE_JWT_ERROR("system.50008", "生成JWT失败"),

    /**
     * 解析JWT失败
     */
    PARSE_JWT_ERROR("system.50009", "解析JWT失败：{0:jwt}"),

    /**
     * 添加失败
     */
    CREATE_DATA_ERROR("system.50010", "数据添加失败"),

    /**
     * 修改失败
     */
    UPDATE_DATA_ERROR("system.50011", "ID：{0:id} 修改失败");

    private final String code;

    private final String messageTemplate;

    /**
     * 构造系统错误码
     *
     * @param code            错误码（遵循4xxx客户端错误，5xxx服务器错误）
     * @param messageTemplate 消息模板（支持{0:param}顺序占位符或{key}键值对占位符）
     */
    SystemErrorCode(String code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String messageTemplate() {
        return messageTemplate;
    }

    // 以下为便捷方法，简化参数填充（可选）

    /**
     * 快速生成参数不存在的错误消息
     *
     * @param paramName 参数名
     * @return 格式化消息
     */
    public static String paramRequired(String paramName) {
        return PARAM_REQUIRED.getMessage(paramName);
    }

    /**
     * 快速生成资源未找到的错误消息
     *
     * @param resource 资源名称
     * @param id       资源ID
     * @return 格式化消息
     */
    public static String resourceNotFound(String resource, Object id) {
        return RESOURCE_NOT_FOUND.getMessage(resource, id);
    }
}
