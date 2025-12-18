/**
 * <h1>Web 相关功能封装</h1>
 *
 * <p>本包提供与 Web 会话上下文、JWT Header/Payload、Principal 信息及会话类型相关的核心类，主要包括：</p>
 *
 * <ul>
 *     <li>{@link com.g2rain.common.web.PrincipalContext} — 会话上下文信息，封装了当前请求的用户、组织、会话等信息。</li>
 *     <li>{@link com.g2rain.common.web.PrincipalContextHolder} — 会话上下文持有者，基于 {@link com.alibaba.ttl.TransmittableThreadLocal} 提供线程安全的上下文访问。</li>
 *     <li>{@link com.g2rain.common.web.PrincipalHeaders} — 定义请求头字段常量，用于会话上下文传递。</li>
 *     <li>{@link com.g2rain.common.web.TokenJWTHeader} / {@link com.g2rain.common.web.TokenJWTPayload} — 普通 Token JWT 的 Header 和 Payload 封装，用于鉴权和防重放。</li>
 *     <li>{@link com.g2rain.common.web.DPoPJWTHeader} / {@link com.g2rain.common.web.DPoPJWTPayload} — DPoP Proof JWT 的 Header 和 Payload 封装，用于增强安全性。</li>
 *     <li>{@link com.g2rain.common.web.BasePrincipal} — 会话基类，包含基础会话信息。</li>
 * </ul>
 *
 * <h2>使用示例</h2>
 *
 * <pre>{@code
 * // 设置会话上下文
 * PrincipalContextHolder.setClientId("client123");
 * PrincipalContextHolder.setSessionType(SessionType.USER);
 * PrincipalContextHolder.setUserId("user123");
 *
 * // 获取会话上下文
 * String clientId = PrincipalContextHolder.getClientId();
 * SessionType type = PrincipalContextHolder.getSessionType();
 *
 * // 构建普通 Token JWT Payload
 * TokenJWTPayload payload = new TokenJWTPayload();
 * payload.setIat(System.currentTimeMillis() / 1000);
 * payload.setExp(System.currentTimeMillis() / 1000 + 3600);
 * payload.setJti(UUID.randomUUID().toString());
 *
 * // 构建 DPoP Proof JWT Payload
 * DPoPJWTPayload dpopPayload = new DPoPJWTPayload();
 * dpopPayload.setHtu("https://api.example.com/resource");
 * dpopPayload.setHtm("GET");
 * dpopPayload.setIat(System.currentTimeMillis() / 1000);
 * dpopPayload.setJti(UUID.randomUUID().toString());
 * }</pre>
 *
 * <h2>功能说明</h2>
 * <p>
 * 本包主要用于 Web 会话管理与 JWT 处理，提供了：
 * </p>
 * <ul>
 *     <li>统一的会话上下文存取机制（{@link com.g2rain.common.web.PrincipalContextHolder}）</li>
 *     <li>标准化的请求头字段定义（{@link com.g2rain.common.web.PrincipalHeaders}）</li>
 *     <li>JWT Header 与 Payload 封装（{@link com.g2rain.common.web.TokenJWTHeader}、{@link com.g2rain.common.web.TokenJWTPayload}、{@link com.g2rain.common.web.DPoPJWTHeader}、{@link com.g2rain.common.web.DPoPJWTPayload}）</li>
 * </ul>
 *
 * <h2>注意事项</h2>
 * <ul>
 *     <li>会话上下文依赖 {@link com.alibaba.ttl.TransmittableThreadLocal}，确保跨线程传递会话信息。</li>
 *     <li>JWT Header/Payload 封装类建议结合实际业务进行扩展。</li>
 * </ul>
 */
package com.g2rain.common.web;
