/**
 * 虚拟线程 / 线程池上下文传播：Principal（及可选链路/MDC）跨线程携带。
 * <p>再起 VT 或提交到其它线程且依赖身份、日志关联时，须经本包 {@link com.g2rain.common.concurrent.Contexts}
 * / {@link com.g2rain.common.concurrent.ContextExecutors} wrap。
 * Principal 本体仍由 {@link com.g2rain.common.web.PrincipalContextHolder} 管理；
 * OTel/MDC 由 tracing starter 注册的 {@link com.g2rain.common.concurrent.ContextPropagator} 补齐。
 */
package com.g2rain.common.concurrent;
