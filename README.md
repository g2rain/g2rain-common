# g2rain-common

[![Maven Central](https://img.shields.io/maven-central/v/com.g2rain/g2rain-common.svg)](https://search.maven.org/artifact/com.g2rain/g2rain-common)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-25+-orange.svg)](https://openjdk.java.net/)
[![Build Status](https://img.shields.io/badge/build-Maven-C71A36?logo=apachemaven&logoColor=white)](https://github.com/g2rain/g2rain-common)

## 1. 徽标与状态标识

- 当前版本通过 `Maven Central` 发布
- 当前运行时要求 `Java 25+`
- 当前构建方式以 `Maven` 为准
- 当前开源许可证为 `Apache 2.0`

## 2. 项目简介

[官网](https://www.g2rain.com) · [完整文档](docs/index.md) · [公共 API 契约](docs/api/public-contracts.md) · [兼容性规则](docs/development/compatibility.md) · [Issues](https://github.com/g2rain/g2rain/issues) · [Discussions](https://github.com/g2rain/g2rain/discussions)

## 3. 平台定位

- 项目简介
- 平台定位
- 业务域说明
- 功能概览
- 使用场景
- 核心流程
- 流程图
- 技术栈
- 环境要求
- 快速开始
- 构建与镜像
- 代码质量与测试
- 依赖引入
- 安全说明
- 与关联仓库的关系
- 模块说明
- 架构与工程文档
- 职责边界
- 常见问题
- 关联仓库
- 参与贡献
- 许可证
- 联系我们
- 致谢

## 4. 核心能力

- 统一异常与错误码体系：`BusinessException`、`ErrorCode`、`SystemErrorCode`、异常转换与本地化消息能力
- 通用结果与分页模型：`Result`、`PageData`、`PageSelectListDto`、基础 DTO / PO / VO
- JSON 编解码增强：`JsonCodec`、条件字段序列化、数字精度保留
- 事件同步抽象：事件发布、消息分发、消息存储注册与事件模型
- 主体上下文与安全模型：`PrincipalContext`、请求头约定、Token / DPoP JWT 模型
- 校验与分组能力：创建/更新分组、统一校验辅助
- 平台通用枚举与接口约定：会话类型、组织类型、ID 生成器、对象转换接口
- 常用工具集合：断言、字符串、集合、时间、数值、媒体类型等

## 5. 技术栈

- 语言与运行时：`Java 25`
- 构建工具：`Maven`
- 打包方式：`jar`
- 核心依赖：`jackson-databind`、`mapstruct`、`swagger-annotations-jakarta`、`jakarta.validation-api`
- 测试框架：`JUnit Jupiter`、`Mockito`
- 质量工具：`Checkstyle`、`PMD`、`SpotBugs`、`JaCoCo`
- 发布目标：`Maven Central / Sonatype Central Portal`

本项目是 Supporting Library，而不是领域服务。中央仓库目前尚未发布适用于公共 Java 库的 `java-library` Profile，因此项目采用本地 `java-common-library 1.0.0-local` 工程基线，不套用 `java-domain-service` 规则。

## 业务域说明

该仓库聚焦于 `后端公共模型、通用规范、基础抽象与跨服务复用能力`。

核心对象包括：
- Token/DPoP JWT 载荷
- 访问令牌
- 分页数据
- 会话
- 同步事件消息
- 统一响应结果
- 业务异常与错误码
- 应用
- 主体
- 主体上下文
- 字段校验错误

主要流程包括：
- 业务异常到统一 Result 响应的转换流程
- 请求主体上下文的绑定、读取、任务包装与作用域释放流程
- 领域事件的发布、通道选择、消息分发与存储处理流程
- DTO 创建/更新分组校验与字段错误聚合流程

### 本地构建

```bash
mvn clean package
```

### 本地测试

```bash
mvn test
```

### 发布说明

- 正式版通过 Git Tag 触发 `release.yml`
- `develop` 分支上的 `-SNAPSHOT` 版本可通过 `snapshot.yml` 发布
- Release 流程包含源码包、Javadoc 包和 GPG 签名

## 7. 项目结构

```text
g2rain-common/
├── .github/workflows/
│   ├── release.yml
│   └── snapshot.yml
├── src/main/java/com/g2rain/common/
│   ├── converter
│   ├── enums
│   ├── exception
│   ├── id
│   ├── json
│   ├── model
│   ├── syncer
│   ├── utils
│   ├── validation
│   └── web
├── src/test/java/com/g2rain/common/
└── pom.xml
```

## 技术栈

| 类别 | 说明 |
| --- | --- |
| 运行时 | Java 25 |
| 其他 | Lombok |

## 环境要求

- JDK 25+
- Maven 3.9+

## 快速开始

| 步骤 | 命令或位置 | 说明 |
| --- | --- | --- |
| 准备构建环境 | JDK 25+、Maven 3.9+ | 工具组件通常只需要 Java 与 Maven 构建环境。 |
| 构建组件 | `mvn clean package` | 执行 Maven 构建，生成可发布或可本地安装的组件产物。 |
| 本地安装 | `mvn clean install` | 安装到本地 Maven 仓库，便于业务工程试用依赖。 |

版本号以项目构建配置为准，当前识别为 `1.0.7`。

## 构建与镜像

| 目标 | 命令 | 产物 | 说明 |
| --- | --- | --- | --- |
| 组件产物 | `mvn clean package` | `g2rain-common-1.0.7.jar` | 执行 Maven 标准构建，生成可发布的公共库组件产物。 |
| 本地 Maven 安装 | `mvn clean install` | `本地 Maven 仓库产物` | 安装到本地 Maven 仓库，便于业务工程本地验证依赖。 |

## 代码质量与测试

| 检查项 | 命令 | 说明 |
| --- | --- | --- |
| 单元测试与 Enforcer | `mvn test` | 执行 JUnit 测试，并检查 JDK 25 与依赖上界规则。 |
| Checkstyle | `mvn checkstyle:check` | 检查 Java 代码风格与组织规范。 |
| PMD | `mvn pmd:check` | 执行静态规则检查，识别潜在代码问题。 |
| SpotBugs | `mvn spotbugs:check` | 识别潜在缺陷和风险代码。 |
| JaCoCo | `mvn clean test jacoco:report` | 配置的覆盖率命令；当前因 Surefire `argLine` 覆盖导致缺少 execution data，尚不能声明覆盖率。 |

2026-09-06 已执行 `mvn test`：编译 70 个主源码文件和 49 个测试源码文件，运行 256 项测试，0 失败、0 错误、0 跳过；Maven Enforcer 的 Java 版本与 `requireUpperBoundDeps` 检查通过。测试仍有 unchecked 编译提示，以及 Maven/JDK 的未来兼容性警告，详见[测试说明](docs/development/testing.md)。

## 接入与使用示例

| 示例 | 方式 | 内容 | 说明 |
| --- | --- | --- | --- |
| Maven 依赖引入 | Maven | `<dependency><groupId>com.g2rain</groupId><artifactId>g2rain-common</artifactId><version>1.0.7</version></dependency>` | 在业务工程 pom.xml 中引入该组件。 |
| 返回成功结果 | Java | `Result.success(data)` | 使用统一 Result 包装业务返回数据。 |
| 返回分页结果 | Java | `Result.successPage(pageNum, pageSize, total, records)` | 使用 PageData 结构返回分页数据。 |
| 读取主体上下文 | Java | `PrincipalContext context = PrincipalContextHolder.require()` | 在已绑定请求作用域中读取当前用户、组织、应用与链路信息。 |
| 统一 JSON 编解码 | Java | `JsonCodec codec = JsonCodecFactory.instance()` | 获取默认编解码器，执行对象、字符串、字节数组与 JsonNode 之间的转换。 |
| 执行创建/更新校验 | Java | `Validations.validateSave(dto)` | 根据 DTO 标识选择 CreateGroup 或 UpdateGroup，并聚合字段错误。 |

## 安全说明

| 主题 | 说明 |
| --- | --- |
| 依赖可信边界 | 作为平台共享组件或构建工具，应通过组织 Maven 仓库、版本锁定和发布流程控制依赖来源。 |

## 与关联仓库的关系

本仓库位于 g2rain 后端研发支撑层，通过 Maven 依赖为平台后端服务提供公共模型、通用工具和基础规范。

## 模块说明

| 模块 | 职责说明 | 代码线索 |
| --- | --- | --- |
| model | 定义 DTO/PO/VO 基类、统一 Result、分页 PageData、下拉选择与排序模型。 | BaseDto、BasePo、BaseVo、Result、PageData、SortItem |
| exception | 定义错误码、业务异常、字段错误、异常转换、默认处理器及消息本地化扩展。 | ErrorCode、BusinessException、ExceptionConverter、DefaultExceptionProcessor、ErrorMessageRegistry |
| json | 封装 Jackson 编解码器、构建器、工厂、条件字段输出和原始数字节点。 | JsonCodec、JsonCodecBuilder、JsonCodecFactory、RawNumberDeserializer、ConditionalPropertyWriter |
| web | 定义主体上下文、标准透传请求头、作用域持有器以及 Token/DPoP JWT Header 与 Payload。 | PrincipalContext、PrincipalContextHolder、PrincipalHeaders、ScopedContextHolder、TokenJWTPayload、DPoPJWTPayload |
| syncer | 定义领域事件发布、通道聚合、消息分发和消息存储注册扩展链路。 | EventPublisherHub、EventPublisher、DefaultMessageDispatcher、AbstractMessageStorage、MessageStorageRegistry |
| validation | 提供 Create/Update 校验分组、DTO 保存场景校验和字段错误聚合。 | Validations、CreateGroup、UpdateGroup、FieldError |
| converter / id / enums | 提供 MapStruct 转换基类、ID 生成接口及组织、会话等公共枚举。 | CommonConverter、IdGenerator、OrganType、SessionType |
| utils | 提供断言、集合、字符串、时间、数值、媒体类型和常量等无框架工具。 | Asserts、Collections、Strings、Moments、Decimals、MediaTypes、Constants |

## 架构与工程文档

| 主题 | 文档 |
| --- | --- |
| 项目机器可读事实 | [docs/project.yaml](docs/project.yaml) |
| 架构与包职责 | [架构总览](docs/architecture/overview.md) · [包与模块](docs/architecture/modules.md) |
| 依赖与已知风险 | [依赖边界](docs/architecture/dependencies.md) · [架构偏差](docs/architecture/deviations.md) |
| 公共契约与版本演进 | [公共 API 契约](docs/api/public-contracts.md) · [兼容性](docs/development/compatibility.md) |
| 开发与发布 | [完成定义](docs/development/definition-of-done.md) · [Maven Central 发布](docs/operations/publishing.md) |
| 安全 | [安全边界](docs/security/security-boundaries.md) |

所有 `public` 类型、方法、序列化字段、枚举值、错误码和请求头都可能成为消费方契约。破坏性变化需要明确迁移说明和版本策略，并在代表性消费项目中完成验证。

## 职责边界

该仓库主要负责：
- 负责提供后端公共模型、通用工具、异常响应和基础抽象
- 负责支撑多个 g2rain 后端服务复用一致的工程基础能力

该仓库默认不负责：
- 不承载具体业务域流程
- 不作为平台主数据或业务数据的权威来源

## 常见问题

| 问题 | 可能原因 | 处理建议 |
| --- | --- | --- |
| 业务工程无法解析依赖 | 组件未发布到当前 Maven 仓库，或 groupId/artifactId/version 配置不一致。 | 检查 Maven 仓库地址、版本号和业务工程 dependencyManagement 配置。 |

## 关联仓库

| 仓库 | 协作关系 |
| --- | --- |
| g2rain-spring-boot-starter | 通常位于公共组件的上层，复用 g2rain-common 的基础模型、工具能力与工程约定，并进一步封装为 Starter。 |

## 参与贡献

我们欢迎所有形式的贡献：Issue 反馈、文档改进、功能建议与代码提交。

推荐流程：

1. Fork 本仓库。
2. 创建特性分支：`git checkout -b feature/your-feature-name`。
3. 提交更改：`git commit -m "Add some feature"`。
4. 推送分支：`git push origin feature/your-feature-name`。
5. 提交 Pull Request。

代码贡献前请尽量补充必要的测试和文档，并确保构建、测试与静态检查通过。

提交前请阅读[代码约定](docs/development/code-conventions.md)、[兼容性规则](docs/development/compatibility.md)和[完成定义](docs/development/definition-of-done.md)。

## 许可证

本项目基于 [Apache License 2.0](LICENSE) 开源。

## 联系我们

- Issues: [GitHub Issues](https://github.com/g2rain/g2rain/issues)
- 讨论: [GitHub Discussions](https://github.com/g2rain/g2rain/discussions)
- 邮箱: g2rain_developer@163.com

## 致谢

感谢所有为 g2rain 项目提交 Issue、代码、文档、建议和使用反馈的开发者们！
