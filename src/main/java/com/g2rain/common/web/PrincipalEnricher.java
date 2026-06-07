package com.g2rain.common.web;


/**
 * Principal 扩展信息填充器。
 *
 * <p>该接口用于在登录态基础信息构建完成后，按需补充跨模块或跨服务获取的主体信息。
 * 典型场景包括通过 starter 调用具体微服务查询部门路径、岗位、租户扩展属性等，再写入
 * {@link BasePrincipal} 或其子类实例。</p>
 *
 * <p>实现类通常以 Spring Bean 的形式注册。调用方可以注入 {@code List<PrincipalEnricher>}
 * 并依次执行所有实现；当应用没有注册任何实现时，调用方应保持原有登录态构建逻辑不变。</p>
 *
 * @author alpha
 * @since 2026/5/30
 */
public interface PrincipalEnricher {

    /**
     * 补充当前会话主体的扩展信息。
     *
     * <p>实现类应根据 {@code principal} 中已有的会话类型、用户 ID、组织 ID 等信息判断是否需要处理；
     * 对不支持的会话类型或缺少必要上下文的情况，应直接返回。</p>
     *
     * @param principal 当前会话主体，调用方会在基础登录态字段填充完成后传入
     */
    void enrich(BasePrincipal principal);
}
