# g2rain-common Agent Instructions

本文件是 AI Coding 在本项目中的执行入口。项目事实位于 `docs/project.yaml`，文档入口位于 `docs/index.md`。

## 项目定位

- 类型：G2rain 公共 Java 库（Supporting Library）
- Maven 坐标：`com.g2rain:g2rain-common`
- 当前版本：`1.0.7`
- Java 基线：25
- 架构基线：项目本地 `java-common-library 1.0.0-local`
- 中央 Profile：尚未发布适用的 `java-library` Profile，不得套用 `java-domain-service`

本库提供跨服务共享的数据模型、错误契约、JSON、校验、请求主体上下文、事件同步抽象和基础工具。它不实现具体领域业务、认证流程、消息中间件适配或 ID 算法。

## 开始前

读取 `docs/project.yaml`、`docs/architecture/overview.md`、`docs/architecture/dependencies.md`、`docs/architecture/deviations.md`、`docs/api/public-contracts.md`、`docs/development/compatibility.md`、`docs/development/testing.md` 和任务对应需求。

## 执行规则

- 将所有 `public` 类型、方法、序列化字段、枚举值、错误码和请求头视为跨仓库兼容契约。
- 不把 Spring、数据库驱动、消息中间件客户端或具体服务实现引入公共库。
- 新依赖必须证明具有跨服务通用性，并评估传递依赖、Maven Central 发布和 Java 版本影响。
- 修改 `model`、`json`、`web` 或 `syncer` 时补充序列化/反序列化与兼容测试。
- ScopedValue 上下文只能在明确作用域中访问；异步任务必须显式包装或传递上下文。
- 不在日志、异常、`toString` 或测试夹具中泄露 Token、DPoP Proof 和主体敏感字段。
- 发布版本、坐标、依赖或公共 API 变化必须同步项目文档和 CHANGELOG。
- 不修改 README；README 更新使用独立的 `generate` 命令。

## 完成前

运行 `mvn test`；按变更需要运行 `mvn checkstyle:check`、`mvn pmd:check`、`mvn spotbugs:check` 和 `mvn package`。检查 `git diff --check`、公共 API 兼容性、发布元数据和敏感信息，并按 `docs/development/definition-of-done.md` 报告结果。

