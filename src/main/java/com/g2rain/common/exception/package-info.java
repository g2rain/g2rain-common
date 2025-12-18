/**
 * <p>
 * {@code exception} 包包含了系统中所有与业务异常处理相关的类和接口，
 * 提供统一的异常定义、处理、转换、国际化以及封装功能。
 * </p>
 *
 * <h2>主要功能</h2>
 * <ul>
 *     <li>定义业务异常：{@link com.g2rain.common.exception.BusinessException}、{@link com.g2rain.common.exception.BaseError}、{@link com.g2rain.common.exception.FieldError}</li>
 *     <li>错误码规范：{@link com.g2rain.common.exception.ErrorCode} 接口定义统一的错误码与消息模板</li>
 *     <li>错误消息本地化：{@link com.g2rain.common.exception.LocalizedErrorMessage}、{@link com.g2rain.common.exception.ErrorMessageRegistry}</li>
 *     <li>异常转换工具：{@link com.g2rain.common.exception.ExceptionConverter} 将异常和结果对象互转</li>
 *     <li>异常处理接口：{@link com.g2rain.common.exception.ExceptionProcessor} 定义异常处理流程</li>
 *     <li>异常处理实现：{@link com.g2rain.common.exception.DefaultExceptionProcessor} - 将业务异常转换为统一响应结构。</li>
 *     <li>消息解析工具：{@link com.g2rain.common.exception.MessageResolver} 支持顺序参数和键值参数的占位符替换</li>
 * </ul>
 *
 * <h2>使用方式</h2>
 * <p>
 * 1. 定义错误码枚举实现 {@link com.g2rain.common.exception.ErrorCode} 接口，例如：
 * </p>
 * <pre>{@code
 * public enum MyErrorCode implements ErrorCode {
 *     INVALID_INPUT(400, "Invalid input: {0}");
 *     ...
 * }
 * }</pre>
 *
 * <p>
 * 2. 抛出业务异常：
 * </p>
 * <pre>{@code
 * throw new BusinessException(MyErrorCode.INVALID_INPUT, "username");
 * }</pre>
 *
 * <p>
 * 3. 在异常处理器中使用 {@link com.g2rain.common.exception.ExceptionProcessor} 处理业务异常：
 * </p>
 * <pre>{@code
 * public class MyExceptionProcessor implements ExceptionProcessor {
 *     @Override
 *     public Result<Void> process(BusinessException ex, Locale locale) {
 *         return toResult(ex);
 *     }
 * }
 * }</pre>
 *
 * <p>
 * 4. 本地化错误消息：
 * </p>
 * <pre>{@code
 * LocalizedErrorMessage msg = errorMessageRegistry.getMessage("INVALID_INPUT", "zh_CN");
 * }</pre>
 *
 * <h2>设计理念</h2>
 * <p>
 * 本包设计目标是提供一致、可扩展的异常处理机制，包括异常定义、封装、转换、
 * 本地化以及统一的处理入口，方便在大型分布式系统中统一管理异常信息。
 * </p>
 *
 * @author jagger
 * @since 2025/10/5
 */
package com.g2rain.common.exception;
