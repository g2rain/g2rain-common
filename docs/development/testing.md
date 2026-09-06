# 测试

## 当前验证

2026-09-06 执行 `mvn test`：

- 编译 70 个主源码文件、49 个测试源码文件。
- 执行 256 个测试，Failures 0、Errors 0、Skipped 0。
- Maven Enforcer 的 JDK 版本和 `requireUpperBoundDeps` 通过。
- JaCoCo report 因缺少 execution data 被跳过，不能声明覆盖率。
- `EventPublisherHubTest` 编译出现 unchecked 提示；Maven/Jansi 有未来原生访问与 Unsafe 警告。

## 测试策略

| 区域 | 核心覆盖 |
| --- | --- |
| model | 默认值、分页边界、排序、成功/失败结果与泛型 |
| exception | 参数替换、本地化、转换、未知异常与字段错误 |
| json | 大数精度、null、泛型、字段排序、条件输出与错误输入 |
| web | Principal 作用域、嵌套/缺失上下文、JWT/DPoP 数据 |
| syncer | binding、dataSource、事件类型、注册、分发和失败隔离 |
| validation/utils | 分组、边界、null、空集合、数值和时间格式 |

公共契约变化除单元测试外，还应在代表性消费项目中执行编译或集成测试。

