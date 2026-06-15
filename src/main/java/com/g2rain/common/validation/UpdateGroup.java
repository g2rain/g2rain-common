package com.g2rain.common.validation;

/**
 * 更新场景的 Bean Validation 分组标记。
 *
 * <p>用法示例：</p>
 * <pre>{@code
 * public class ExampleDto {
 *     @Null(groups = CreateGroup.class)
 *     @NotNull(groups = UpdateGroup.class)
 *     private Long id;
 *
 *     @NotBlank(groups = UpdateGroup.class)
 *     private String name;
 * }
 *
 * @PutMapping
 * public Result<Void> update(@Validated(UpdateGroup.class) @RequestBody ExampleDto dto) { ... }
 * }</pre>
 *
 * @author alpha
 * @since 2026/06/09
 */
public interface UpdateGroup {
}
