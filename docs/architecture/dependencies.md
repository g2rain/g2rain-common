# 依赖边界

## 允许的依赖

- Jackson：公共 JSON 和跨服务序列化契约。
- Jakarta Validation API：DTO 校验契约，运行实现由消费方或测试提供。
- Lombok、MapStruct：编译期代码生成；Lombok 为 provided。
- Swagger annotations：公共模型的 API 描述注解。

JUnit、Mockito、Hibernate Validator 和 Expressly 仅用于测试。

## 禁止扩张

公共库不应依赖 Spring Boot、Web 服务器、ORM、数据库驱动、服务发现、配置中心、消息中间件客户端或具体业务模块。这些依赖会把实现选择传递给所有消费方。

`syncer` 和 `id` 只定义抽象；消息可靠性、binding、序列化策略和 ID 算法由 starter 或服务实现。`web` 提供载荷与上下文结构，但不签发或验证 Token。

引入或升级依赖前检查 Maven 传递依赖、Java 25 兼容性、许可证、序列化影响和 `requireUpperBoundDeps`。

