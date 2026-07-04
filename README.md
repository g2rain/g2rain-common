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

`g2rain-common` 是 G2rain 平台的 Java 公共基础库，用于沉淀多个 Java 仓库共享的基础能力。它统一提供通用模型、异常规范、JSON 编解码增强、事件同步抽象、主体上下文与 JWT / DPoP 安全模型、校验分组和常用工具能力，为平台服务、增强组件和工程化仓库提供一致的公共底座。

## 3. 平台定位

`g2rain-common` 位于 G2rain 平台公共基础能力层，是多个 Java 仓库的底层共享依赖。  
它主要服务于平台核心后端服务、平台增强组件以及工程化 Starter / 脚手架相关仓库。  
它不是独立运行服务，也不承载具体业务逻辑，而是承载跨仓库统一复用的协议、模型、异常、上下文和工具能力。

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

## 6. 快速开始

### 环境要求

- `JDK 25`
- `Maven 3.6+`

### Maven 依赖

```xml
<dependency>
    <groupId>com.g2rain</groupId>
    <artifactId>g2rain-common</artifactId>
    <version>1.0.6</version>
</dependency>
```

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

### 核心能力结构说明

#### 1. 统一异常与结果协议

- `exception` 包负责异常、错误码、消息模板、本地化消息和异常转换
- `model` 包负责统一返回结构、分页结构和基础 DTO / PO / VO
- 这两部分共同构成平台后端统一接口协议的基础

典型用法：

```java
throw new BusinessException(SystemErrorCode.PARAM_REQUIRED, "tenantId");

Result<String> success = Result.success("ok");
Result<Void> error = Result.error("BIZ_ERROR", "invalid request");
```

#### 2. JSON 编解码增强能力

- `json` 包提供统一 `JsonCodec`
- 支持数字原始格式保留与条件字段序列化
- 适合平台在金额、精度和统一序列化规则场景下复用

典型用法：

```java
JsonCodec codec = JsonCodecFactory.instance();
String json = codec.obj2str(data);
OrderDto dto = codec.str2obj(json, OrderDto.class);
```

#### 3. 事件同步抽象能力

- `syncer` 包提供事件发布中心、事件模型、消息分发器和消息存储注册能力
- 这里提供的是抽象层，不绑定具体业务服务
- 为平台服务间同步、广播和异步处理提供统一接口基础

典型用法：

```java
EventPublisherHub hub = new EventPublisherHub(Map.of("sync", publisher));
hub.sendUpdate("sync", "USER_SERVICE", payload);
```

#### 4. 主体上下文与安全模型能力

- `web` 包定义主体上下文、请求头约定、Token JWT 与 DPoP JWT 基础模型
- `PrincipalContextHolder` 基于作用域上下文封装请求级主体信息
- 为平台身份链路和上下文透传提供基础结构

典型用法：

```java
PrincipalContext context = new PrincipalContext();
PrincipalContextHolder.runWith(context, () -> {
    PrincipalContextHolder.setClientId("client-a");
    PrincipalContextHolder.setTraceId("trace-1");
});
```

#### 5. 接入建议与边界

- 如果目标是统一异常、统一返回结构、统一 JSON 规则，可直接引入 `g2rain-common`
- 如果目标是落地自动配置、Spring 扩展或更强的框架集成，通常应和 `g2rain-spring-boot-starter` 配合使用
- `syncer` 提供的是抽象和协议入口，不等于直接提供完整中间件能力
- `web` 提供的是上下文、头信息和 JWT / DPoP 模型，上层鉴权和验签逻辑仍需由具体服务或 Starter 承载

## 8. 常用命令

```bash
mvn compile
mvn test
mvn checkstyle:check pmd:check spotbugs:check
mvn jacoco:report
mvn package
```

## 9. 质量与测试

- 当前扫描到主源码文件 `70` 个，测试文件 `49` 个
- `exception`、`json`、`model`、`syncer`、`utils`、`web` 等核心包均有对应测试
- 当前 `converter` 包未看到对应测试文件，后续可继续补强
- 构建中启用了 `maven-enforcer-plugin`、`maven-checkstyle-plugin`、`maven-pmd-plugin`、`spotbugs-maven-plugin` 和 `jacoco-maven-plugin`
- 仓库要求 `JDK 25`，建议团队本地环境与 CI 环境保持一致

## 10. 相关仓库

- `g2rain-iam`
- `g2rain-gateway-webmvc`
- `g2rain-gateway-webflux`
- `g2rain-infra`
- `g2rain-department`
- `g2rain-spring-boot-starter`

## 11. 使用建议

- 适合作为平台内 Java 项目的统一公共依赖
- 适合在新服务、Starter、增强组件和脚手架中直接复用
- 不建议把它理解为仅包含零散工具方法的 util 仓库
- 若上层仓库需要统一上下文、统一异常、统一 JSON 规则或统一同步抽象，应优先复用这里的能力

## 12. 贡献指南

欢迎通过文档改进、Issue 反馈、测试补充、代码优化、功能增强等形式参与贡献。

建议流程：
1. Fork 本仓库
2. 创建特性分支
3. 提交修改
4. 推送分支
5. 提交 Pull Request

提交前请尽量确保：
- 遵循现有技术栈与代码规范
- 更新相关文档
- 补充必要测试

## 13. 许可证

本项目基于 [Apache 2.0许可证](LICENSE) 开源。

## 14. 联系我们

- **站点**: https://www.g2rain.com/
- **Issues**: [GitHub Issues](https://github.com/g2rain/g2rain/issues)
- **讨论**: [GitHub Discussions](https://github.com/g2rain/g2rain/discussions)
- **邮箱**: g2rain_developer@163.com

## 15. 致谢

感谢所有为这个项目做出贡献的开发者们。

如果这个项目对您有帮助，欢迎 Star 支持。
