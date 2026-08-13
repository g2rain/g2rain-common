package com.g2rain.common.validation;

/**
 * 新增场景的 Bean Validation 分组标记。
 *
 * <p>用法示例：</p>
 * <pre>{@code
 * public class ExampleDto {
 *     @Null(groups = CreateGroup.class)
 *     @NotNull(groups = UpdateGroup.class)
 *     private Long id;
 *
 *     @NotBlank(groups = CreateGroup.class)
 *     private String code;
 * }
 *
 * @PostMapping
 * public Result<Void> create(@Validated(CreateGroup.class) @RequestBody ExampleDto dto) { ... }
 * }</pre>
 *
 * @author alpha
 * @since 2026/06/09
 */
public interface CreateGroup {
}
