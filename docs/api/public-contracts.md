# 公共 API 契约

本库的所有公开类型都会进入消费项目的编译、序列化或运行契约。修改前必须按[兼容性规则](../development/compatibility.md)评估。

## 核心契约

- 模型：`Result<T>`、`PageData<T>`、`BaseDto/Po/Vo`、查询与排序 DTO。
- 错误：`ErrorCode`、`BusinessException`、字段错误、本地化消息和异常转换。
- JSON：默认 `JsonCodecFactory`、可配置 `JsonCodecBuilder`、原始数字节点与条件输出注解。
- 上下文：Principal/JWT/DPoP 数据结构、`PrincipalHeaders` 和基于 ScopedValue 的 holder。
- 事件：`EventPublisher`、`MessageDispatcher`、`AbstractMessageStorage`、`EventType` 与 `EventMessage`。
- 扩展：`IdGenerator`、Create/Update Validation Group、MapStruct 时间转换器。

## 契约变化检查

以下变化通常需要兼容性评审和版本策略：删除或重命名 public API；改变泛型、空值或异常语义；修改 JSON 字段、枚举常量、请求头、错误码；改变分页默认值；修改事件 dataSource/type 解析；提高 Java 基线或新增传递依赖。

实现细节不能被文档承诺为稳定 API。发布前应以 Javadoc、编译测试和消费方验证确认契约。

