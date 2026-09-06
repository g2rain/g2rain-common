# 架构偏差与待决事项

由于中央尚未发布 `java-library` Profile，本项目没有可声明的中央 Profile 偏差；以下记录相对本地基线的已知工程风险。

| 项目 | 证据 | 影响 | 状态 |
| --- | --- | --- | --- |
| 中央公共库 Profile 缺失 | 中央 profiles 仅有领域服务和前端类型 | 跨公共库的强制规则尚无中央事实来源 | 待治理 |
| Java 25 基线较新 | Maven Enforcer 要求 `[25,)` | 消费方和构建环境必须同步升级 | 已接受 |
| JaCoCo 报告未在 `mvn test` 产生 | 2026-09-06 测试输出提示缺少 execution data | 当前无法据此声明覆盖率 | 待修正或明确命令 |
| MapStruct processor 为普通依赖且又配置 annotationProcessorPath | `pom.xml` 同时声明两处 | 可能向消费方暴露不必要依赖 | 待评估 |
| 发布工作流跳过测试 | snapshot 使用 `-DskipTests` | 快照发布依赖其他验证环节 | 待评估 |
| Maven 运行时兼容警告 | Jansi 原生访问与 Guava Unsafe 警告 | 未来 JDK/Maven 版本可能阻断 | 待跟踪 |

