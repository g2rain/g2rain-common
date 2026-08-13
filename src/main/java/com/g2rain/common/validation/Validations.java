package com.g2rain.common.validation;

import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.exception.ErrorCode;
import com.g2rain.common.exception.FieldError;
import com.g2rain.common.exception.SystemErrorCode;
import com.g2rain.common.model.BaseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Bean Validation 工具类，供 Service 在 save 等入口统一校验 DTO。
 */
public final class Validations {

    private static final Validator VALIDATOR =
        Validation.buildDefaultValidatorFactory().getValidator();

    private Validations() {
    }

    /**
     * upsert 入口：按 id 选择 CreateGroup / UpdateGroup，并执行 Default 组格式校验。
     */
    public static void validateSave(BaseDto dto) {
        Class<?> group = isCreate(dto.getId()) ? CreateGroup.class : UpdateGroup.class;
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.addAll(collectFieldErrors(dto, group));
        fieldErrors.addAll(collectFieldErrors(dto, jakarta.validation.groups.Default.class));
        throwIfHasFieldErrors(fieldErrors);
    }

    public static void validateCreate(Object target) {
        validate(target, CreateGroup.class);
    }

    /**
     * Default 组：无 groups 的 {@link Size} 等；由 {@link #validateSave(BaseDto)} 内部调用，也可单独使用。
     */
    public static void validateDefault(Object target) {
        validate(target, jakarta.validation.groups.Default.class);
    }

    /**
     * 按单个校验组执行 Bean Validation，一次返回该组下全部字段错误。
     * 业务场景（Create / Update）互斥，每次只传一个 group；格式约束请用 {@link #validateDefault(Object)}。
     */
    public static void validate(Object target, Class<?> group) {
        throwIfHasFieldErrors(collectFieldErrors(target, group));
    }

    private static List<FieldError> collectFieldErrors(Object target, Class<?> group) {
        Set<ConstraintViolation<Object>> violations = VALIDATOR.validate(target, group);
        List<FieldError> fieldErrors = new ArrayList<>(violations.size());
        for (ConstraintViolation<Object> violation : violations) {
            fieldErrors.add(toFieldError(violation));
        }
        return fieldErrors;
    }

    private static void throwIfHasFieldErrors(List<FieldError> fieldErrors) {
        if (!fieldErrors.isEmpty()) {
            throw new BusinessException(SystemErrorCode.PARAM_INVALID, fieldErrors);
        }
    }

    private static boolean isCreate(Long id) {
        return id == null || id == 0L;
    }

    private static FieldError toFieldError(ConstraintViolation<?> violation) {
        String fieldName = leafPropertyName(violation);
        ErrorCode errorCode = resolveErrorCode(violation);
        Object rejectedValue = violation.getInvalidValue();
        if (SystemErrorCode.PARAM_EXCEEDS_SIZE == errorCode) {
            Annotation annotation = violation.getConstraintDescriptor().getAnnotation();
            if (annotation instanceof Size sizeAnnotation) {
                return new FieldError(fieldName, errorCode, rejectedValue, sizeAnnotation.max());
            }
        }
        if (SystemErrorCode.PARAM_REQUIRED == errorCode) {
            return new FieldError(fieldName, errorCode, fieldName);
        }
        return new FieldError(fieldName, errorCode, fieldName);
    }

    private static ErrorCode resolveErrorCode(ConstraintViolation<?> violation) {
        Class<?> annotationType = violation.getConstraintDescriptor().getAnnotation().annotationType();
        if (NotNull.class.equals(annotationType)
            || NotBlank.class.equals(annotationType)
            || NotEmpty.class.equals(annotationType)) {
            return SystemErrorCode.PARAM_REQUIRED;
        }
        if (Size.class.equals(annotationType)) {
            return SystemErrorCode.PARAM_EXCEEDS_SIZE;
        }
        return SystemErrorCode.PARAM_VAL_INVALID;
    }

    private static String leafPropertyName(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        int dot = path.lastIndexOf('.');
        return dot >= 0 ? path.substring(dot + 1) : path;
    }
}
