# 发布

## Snapshot

`snapshot.yml` 在推送 `develop` 或手工触发时运行。仅当项目版本以 `-SNAPSHOT` 结尾才执行：

```bash
mvn -B clean deploy -DskipTests
```

凭据来自 `CENTRAL_PORTAL_USERNAME` 和 `CENTRAL_PORTAL_PASSWORD`。由于部署命令跳过测试，发布前必须有独立且可追溯的测试成功结果。

## Release

`release.yml` 在 `v*.*.*` Tag 或手工触发时配置 JDK 25、Central Portal 和 GPG，然后运行：

```bash
mvn -B -P release clean deploy
```

release Profile 对制品签名，并由 Central Publishing Maven Plugin 自动发布且等待完成。

## 发布检查

确认 POM 版本与 Tag 对齐、测试和兼容性验证通过、CHANGELOG 更新、sources/Javadoc JAR 可生成、签名与 Portal 凭据仅来自 Secrets，并检查最终扁平 POM 的坐标、依赖和许可证。

