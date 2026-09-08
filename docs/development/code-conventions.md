# 代码约定

- 包名保持在 `com.g2rain.common` 下，按明确职责划分，不建立笼统的业务包。
- 公共类型和方法提供 Javadoc，说明空值、异常、线程、序列化和兼容性语义。
- 工具类保持无状态；共享可变注册表必须明确生命周期、并发与清理策略。
- 公共 DTO/VO/PO 不嵌入单一领域规则；领域字段和枚举留在领域仓库。
- 错误类型实现 `ErrorCode` 契约，避免把内部异常堆栈作为公共响应。
- JSON 修改必须验证大数精度、字段条件、泛型反序列化和稳定输出。
- ScopedValue 上下文 API 必须保持词法作用域，异步执行通过 wrap 或显式传递。
- 事件抽象不得硬编码具体 broker、binding 或服务地址。
- 新依赖遵守[依赖边界](../architecture/dependencies.md)，并通过 Enforcer 检查。

代码格式当前以 Maven Checkstyle 的 Google 规则为准。不要在功能修改中进行无关全库格式化。

