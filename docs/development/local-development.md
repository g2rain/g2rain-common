# 本地开发

## 环境

- JDK 25 或更高版本；Maven Enforcer 会阻止较低版本。
- Maven 3.9 或兼容版本。
- 能访问 Maven Central 的依赖缓存或网络。

```bash
mvn test
```

常用检查：

```bash
mvn clean package
mvn checkstyle:check
mvn pmd:check
mvn spotbugs:check
```

项目是公共 JAR，没有服务启动入口、端口或运行时配置。开发时优先为公共行为补充单元测试，避免依赖具体业务项目才能验证。

Maven `process-resources` 会更新 `.flattened-pom.xml`，`target/` 为构建产物。提交前检查状态，不把本地缓存、报告或凭据纳入版本控制。

