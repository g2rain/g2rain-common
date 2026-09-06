# 完成定义

- 修改属于跨服务稳定能力，而不是单一领域实现。
- public API、序列化、错误、枚举、请求头、事件和 Java 基线的兼容影响已评估。
- 新增或改变行为有单元测试，`mvn test` 通过。
- 按风险运行 Checkstyle、PMD、SpotBugs、package 和代表性消费方验证。
- 新依赖的传递范围、版本、许可证和消费方影响已审查。
- Javadoc、项目文档和 CHANGELOG 与公共行为同步。
- 没有凭据、Token、DPoP Proof、私钥或主体敏感数据进入代码、日志和测试夹具。
- 发布版本符合语义变化，工作流和 Maven 元数据一致。
- `git diff --check` 通过，修改范围不含无关构建产物。

