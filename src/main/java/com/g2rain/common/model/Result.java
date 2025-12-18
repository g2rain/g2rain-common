package com.g2rain.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.g2rain.common.exception.ErrorCode;
import com.g2rain.common.exception.FieldError;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 通用API响应结果封装类
 * 采用泛型动态指定data类型，支持存储消息参数用于日志输出
 */
@Data
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -5815787587704294331L;

    /**
     * 成功状态码
     */
    public static final int STATUS_SUCCESS = 200;

    /**
     * 失败状态码
     */
    public static final int STATUS_ERROR = 500;

    /**
     * 请求标识
     */
    private String requestId;

    /**
     * 请求时间
     */
    private String requestTime;

    /**
     * 状态码，200表示成功，500表示失败
     */
    private int status;

    /**
     * 具体错误码，当status=500时有效
     */
    private String errorCode;

    /**
     * 错误信息描述
     */
    private String errorMessage;

    /**
     * 变量的占位符
     */
    private transient Map<String, Object> keyArgs;

    /**
     * 错误消息的参数列表，用于日志输出和问题排查
     */
    private transient Object[] indexArgs;

    /**
     * 二级错误信息
     */
    private transient List<FieldError> fieldErrors;

    /**
     * 响应数据，类型由泛型T指定
     */
    private transient T data;

    /**
     * 私有化构造方法，防止直接实例化
     */
    private Result() {

    }

    /**
     * 构建成功响应（无数据）
     *
     * @param <T> 数据类型
     * @return 成功响应对象
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.status = STATUS_SUCCESS;
        return result;
    }

    /**
     * 构建成功响应（带数据）
     *
     * @param data 响应数据
     * @param <T>  数据类型
     * @return 成功响应对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.status = STATUS_SUCCESS;
        result.data = data;
        return result;
    }

    /**
     * 构建成功的分页响应
     *
     * @param pageNum  当前页码
     * @param pageSize 每页条数
     * @param total    总记录数
     * @param records  数据列表
     * @param <T>      数据类型
     * @return 分页响应对象
     */
    public static <T> Result<PageData<T>> successPage(int pageNum, int pageSize, long total, List<T> records) {
        PageData<T> pageResult = PageData.of(pageNum, pageSize, total, records);
        return success(pageResult);
    }

    /**
     * 构建成功的分页响应
     *
     * @param pageResult 分页数据对象
     * @param <T>        数据类型
     * @return 分页响应对象
     */
    public static <T> Result<PageData<T>> successPage(PageData<T> pageResult) {
        return success(pageResult);
    }

    /**
     * 构建错误响应（指定错误码和描述）
     *
     * @param errorCode    错误码
     * @param errorMessage 错误描述
     * @param args         错误消息参数
     * @param <T>          数据类型
     * @return 错误响应对象
     */
    public static <T> Result<T> error(String errorCode, String errorMessage, Object... args) {
        Result<T> result = new Result<>();
        result.status = STATUS_ERROR;
        result.errorCode = errorCode;
        result.errorMessage = errorMessage;
        result.indexArgs = Objects.nonNull(args) ? args.clone() : null;
        return result;
    }

    /**
     * 构建错误响应（通过错误码接口）
     *
     * @param errorCode 错误码接口实现
     * @param args      错误消息参数
     * @param <T>       数据类型
     * @return 错误响应对象
     */
    public static <T> Result<T> error(ErrorCode errorCode, Object... args) {
        Result<T> result = new Result<>();
        result.status = STATUS_ERROR;
        result.errorCode = errorCode.code();
        result.errorMessage = errorCode.getMessage(args);
        result.indexArgs = Objects.nonNull(args) ? args.clone() : null;
        return result;
    }

    /**
     * 判断响应是否成功
     *
     * @return 是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == STATUS_SUCCESS;
    }
}
