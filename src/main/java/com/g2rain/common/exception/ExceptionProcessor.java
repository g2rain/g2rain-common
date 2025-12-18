package com.g2rain.common.exception;


import com.g2rain.common.model.Result;
import com.g2rain.common.web.PrincipalContextHolder;

/**
 * <p>{@code ExceptionProcessor} 接口用于定义业务异常的处理逻辑。</p>
 * <p>
 * 实现类需实现 {@link #process(BusinessException, String)} 方法，
 * 用于将 {@link BusinessException} 转换为 {@link Result}。
 * </p>
 * <p><b>使用示例：</b></p>
 * <pre>{@code
 * public class MyExceptionProcessor implements ExceptionProcessor {
 *     @Override
 *     public Result<Void> process(BusinessException ex, String locale) {
 *         // 自定义异常处理逻辑
 *         return toResult(ex);
 *     }
 * }
 * }</pre>
 *
 * @author alpha
 * @since 2025/10/5
 */
public interface ExceptionProcessor {

    /**
     * 处理业务异常并生成 {@link Result} 对象。
     *
     * @param ex     待处理的业务异常
     * @param locale 当前请求的区域信息
     * @return 处理后的 {@link Result} 对象
     */
    Result<Void> process(BusinessException ex, String locale);

    /**
     * 默认实现，将 {@link BusinessException} 转换为 {@link Result}。
     *
     * @param ex 待转换的业务异常
     * @return 转换后的 {@link Result} 对象
     */
    default Result<Void> toResult(BusinessException ex) {
        Result<Void> result = Result.error(ex.getErrorCode(), ex.getErrorMessage());
        result.setKeyArgs(ex.getKeyArgs());
        result.setIndexArgs(ex.getIndexArgs());
        result.setRequestId(PrincipalContextHolder.getRequestId());
        result.setRequestTime(PrincipalContextHolder.getRequestTime());
        result.setFieldErrors(ex.getFieldErrors());
        return result;
    }
}
