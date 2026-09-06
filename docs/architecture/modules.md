# 包与模块

| 包 | 职责 | 代表类型 |
| --- | --- | --- |
| `model` | DTO/PO/VO 基类、查询、分页、排序和统一结果 | `BaseDto`、`PageSelectListDto`、`PageData`、`Result` |
| `exception` | 错误码、业务异常、本地化消息、转换与处理 | `ErrorCode`、`BusinessException`、`ExceptionConverter` |
| `json` | Jackson 3 编解码、精度保留和条件字段输出 | `JsonCodec`、`RawNumberNode`、`ConditionalJsonIgnore` |
| `validation` | Create/Update 分组与校验入口 | `CreateGroup`、`UpdateGroup`、`Validations` |
| `web` | Principal、请求头、JWT/DPoP 数据和 ScopedValue 上下文 | `PrincipalContext`、`ScopedContextHolder` |
| `syncer` | 事件消息、发布、分发与存储扩展抽象 | `EventMessage`、`EventPublisherHub`、`MessageDispatcher` |
| `id` | ID 生成 SPI | `IdGenerator` |
| `utils` | 字符串、时间、数值、集合、断言与媒体类型 | `Strings`、`Moments`、`Decimals`、`Asserts` |
| `converter` | MapStruct 公共时间转换 | `CommonConverter` |
| `enums` | 跨服务共享的机构与会话枚举 | `OrganType`、`SessionType` |

新增包必须证明是多个服务共同需要的稳定抽象。只被单一领域使用的代码应留在对应领域仓库。

