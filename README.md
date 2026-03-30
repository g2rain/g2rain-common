# g2rain-common

[![Maven Central](https://img.shields.io/maven-central/v/com.g2rain/g2rain-common.svg)](https://search.maven.org/artifact/com.g2rain/g2rain-common)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Java Version](https://img.shields.io/badge/Java-25+-orange.svg)](https://openjdk.java.net/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/g2rain/g2rain-common)

> 企业级Java基础工具库，提供分布式系统开发的核心组件和实用工具

## 📋 项目简介

g2rain-common 是一个设计精良的企业级基础工具库，为Java开发者提供了一套完整的通用组件和工具类。该库专注于解决分布式系统开发中的常见问题，提供高性能、线程安全、易于使用的API接口。

## ✨ 核心特性

### 🔄 事件同步与消息分发
- **分布式事件处理**：完整的事件发布、订阅、存储和分发机制
- **消息存储管理**：支持多种数据源的消息存储注册与访问
- **事件类型定义**：标准化的CREATE、UPDATE、DELETE事件类型
- **线程安全设计**：基于 ScopedValue 的虚拟线程上下文传递机制实现

### 📄 JSON处理增强
- **高精度序列化**：基于Jackson 3.0.2，保留数字原始格式
- **自定义反序列化**：RawNumberDeserializer避免精度丢失
- **灵活配置**：JsonCodecBuilder支持多种Jackson配置
- **工厂模式访问**：JsonCodecFactory提供默认配置实例

### ⚠️ 统一异常处理
- **业务异常定义**：BusinessException、BaseError、FieldError
- **错误码规范**：ErrorCode接口统一错误码与消息模板
- **国际化支持**：LocalizedErrorMessage、ErrorMessageRegistry
- **异常转换器**：ExceptionConverter异常和结果对象互转

### 🌐 Web会话管理
- **多身份类型**：支持USER、PASSPORT、APP、APP_TENANT等会话类型
- **JWT处理**：TokenJWT和DPoP JWT的Header/Payload封装
- **线程安全上下文**：基于 ScopedValue 的虚拟线程上下文传递机制的会话上下文管理
- **标准请求头**：PrincipalHeaders定义标准化请求头字段

### 📊 通用数据模型
- **统一响应**：Result泛型响应封装，支持成功/失败状态
- **分页支持**：PageData分页数据结构
- **基础实体**：BasePo持久化对象基类，包含乐观锁支持
- **DTO/VO基类**：BaseDto、BaseVo等数据传输对象基类

### 🛠️ 实用工具集
- **字符串处理**：Strings工具类，支持空值检查、大小写转换等
- **集合操作**：Collections工具类，支持Collection、Map、数组判空
- **常量定义**：Constants常量工具类
- **ID生成**：IdGenerator ID生成器接口

## 🚀 快速开始

### 环境要求

- Java 25+
- Maven 3.6+

### Maven 依赖

```xml
<dependency>
    <groupId>com.g2rain</groupId>
    <artifactId>g2rain-common</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle 依赖

```groovy
dependencies {
    implementation "com.g2rain:g2rain-common:1.0.1"
}
```

### 基本使用

#### JSON处理示例

```java
// 获取默认JsonCodec
JsonCodec codec = JsonCodecFactory.instance();

// 对象转JSON字符串
String json = codec.obj2str(myObject);

// JSON字符串转对象
MyObject obj = codec.str2obj(json, MyObject.class);

// 查找JSON节点
JsonNode subNode = codec.lookupNode(node, "data.items[0]");
```

#### 事件发布示例

```java
// 创建事件发布中心
EventPublisherHub hub = new EventPublisherHub(Set.of(new MyEventPublisher()));

// 发送事件
hub.send("USER_SERVICE", EventType.CREATE, new UserData("张三", 25));

// 注册消息存储
MessageStorageRegistry.register(new MyMessageStorage());
```

#### 统一响应示例

```java
// 成功响应
Result<User> result = Result.success(user);

// 分页响应
Result<PageData<User>> pageResult = Result.successPage(1, 10, 100, users);

// 错误响应
Result<Void> errorResult = Result.error("USER_NOT_FOUND", "用户不存在", userId);
```

#### 会话上下文示例

```java
// 设置会话上下文
PrincipalContextHolder.setClientId("client123");
PrincipalContextHolder.setSessionType(SessionType.USER);
PrincipalContextHolder.setUserId("user123");

// 获取会话上下文
String clientId = PrincipalContextHolder.getClientId();
SessionType type = PrincipalContextHolder.getSessionType();
```

## 📚 模块文档

| 模块 | 功能描述 | 文档链接 |
|------|----------|----------|
| `syncer` | 事件同步与消息分发 | [package-info.java](src/main/java/com/g2rain/common/syncer/package-info.java) |
| `json` | JSON处理增强 | [package-info.java](src/main/java/com/g2rain/common/json/package-info.java) |
| `exception` | 统一异常处理 | [package-info.java](src/main/java/com/g2rain/common/exception/package-info.java) |
| `web` | Web会话管理 | [package-info.java](src/main/java/com/g2rain/common/web/package-info.java) |
| `model` | 通用数据模型 | [package-info.java](src/main/java/com/g2rain/common/model/package-info.java) |
| `utils` | 实用工具集 | 各工具类源码 |

## 🏗️ 项目结构

```
g2rain-common/
├── src/main/java/com/g2rain/common/
│   ├── enums/           # 枚举类型定义
│   ├── exception/       # 异常处理框架
│   ├── id/             # ID生成器
│   ├── json/           # JSON处理工具
│   ├── model/          # 通用数据模型
│   ├── syncer/         # 事件同步系统
│   ├── utils/          # 实用工具类
│   └── web/            # Web会话管理
├── src/test/java/      # 单元测试
├── pom.xml            # Maven配置
└── README.md          # 项目文档
```

## 🔧 开发指南

### 代码规范

项目遵循Google Java代码规范，使用以下工具确保代码质量：

- **Checkstyle**：代码风格检查
- **PMD**：代码潜在问题检测
- **SpotBugs**：静态代码分析
- **JaCoCo**：代码覆盖率检查

### 构建命令

```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 代码质量检查
mvn checkstyle:check pmd:check spotbugs:check

# 生成代码覆盖率报告
mvn jacoco:report

# 打包
mvn package
```

## 🤝 贡献指南

我们欢迎所有形式的贡献！

### 贡献流程

1. **Fork** 本仓库
2. **创建特性分支**：`git checkout -b feature/your-feature-name`
3. **提交更改**：`git commit -m "Add some feature"`
4. **推送分支**：`git push origin feature/your-feature-name`
5. **提交Pull Request**

### 代码贡献要求

- 遵循Google Java代码规范
- 添加适当的单元测试
- 更新相关文档
- 确保所有测试通过
- 代码覆盖率不低于80%

## 📄 许可证

本项目基于 [Apache 2.0许可证](LICENSE) 开源。

## 📞 联系我们

- **Issues**: [GitHub Issues](https://github.com/g2rain/g2rain/issues)
- **讨论**: [GitHub Discussions](https://github.com/g2rain/g2rain/discussions)
- **邮箱**: g2rain_developer@163.com

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者们！

---

⭐ 如果这个项目对您有帮助，请给我们一个Star！
