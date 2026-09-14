# SessionType=MEMBER 公共契约

本文记录 `g2rain-common` 为客服会员会话新增的跨服务契约。消费方：IAM（签发）、Gateway（验签与透传）、Member/业务服务（读主体）。

## 1. 枚举

`com.g2rain.common.enums.SessionType` 增加：

| 常量 | 含义 |
| --- | --- |
| `MEMBER` | 会员会话（如企微客服外部联系人）；无 Passport/员工 User |

辅助方法：`SessionType.isMember(sessionType)`。

与员工登录意图（IAM `loginRole=USER`）无关；员工最终 Token 仍为 `SessionType=USER`。

## 2. 主体字段与请求头

| 字段 | 类型 | 请求头 | 说明 |
| --- | --- | --- | --- |
| `sessionType` | `SessionType` | `X-SESSION-TYPE` | 可为 `MEMBER` |
| `memberId` | `Long` | `X-MEMBER-ID` | MEMBER 会话主体；**不得**写入 `userId` / `X-USER-ID` |
| `organId` | `Long` | `X-ORGAN-ID` | 会员所属租户，必填语义由签发与网关共同保证 |

涉及类型：

- `BasePrincipal` / `PrincipalContext`：`memberId` 读写与 `toHeaders`
- `PrincipalContextHolder`：`setMemberId` / `getMemberId`
- `PrincipalHeaders.MEMBER_ID`：`X-MEMBER-ID` / `x-member-id`
- `TokenJWTPayload`：继承 `BasePrincipal`，JWT claim 携带 `memberId`（与 `userId` 分轨）

## 3. 兼容性

- 新增枚举常量与请求头属于公共契约扩展；消费方需升级 `g2rain-common` 后方可编译/反序列化 MEMBER。
- 不得删除或改名既有 `USER`/`PASSPORT`/`ANONYMOUS` 语义。
- 日志、异常、`toString` 不得打印完整 JWT 或敏感主体快照。

## 4. 相关文档

- [公共 API 契约](../api/public-contracts.md)
- [兼容性](../development/compatibility.md)
- [安全边界](../security/security-boundaries.md)
- Gateway：`docs/design/member-session-gateway.md`（webflux / webmvc）
