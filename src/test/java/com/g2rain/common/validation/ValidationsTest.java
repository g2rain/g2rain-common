package com.g2rain.common.validation;

import com.g2rain.common.exception.BusinessException;
import com.g2rain.common.exception.SystemErrorCode;
import com.g2rain.common.model.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidationsTest {

    @Test
    void validateSaveCreateRequiresCreateGroupFields() {
        CreateSampleDto dto = new CreateSampleDto();

        BusinessException ex = assertThrows(BusinessException.class, () -> Validations.validateSave(dto));

        assertEquals(SystemErrorCode.PARAM_INVALID.code(), ex.getErrorCode());
        assertTrue(ex.hasFieldErrors());
    }

    @Test
    void validateSaveCreateReturnsAllFieldErrors() {
        CreateSampleDto dto = new CreateSampleDto();

        BusinessException ex = assertThrows(BusinessException.class, () -> Validations.validateSave(dto));

        assertEquals(2, ex.getFieldErrors().size());
    }

    @Test
    void validateSaveCreatePassesWhenRequiredFieldsPresent() {
        CreateSampleDto dto = new CreateSampleDto();
        dto.setOrganId(1L);
        dto.setRoleName("admin");

        assertDoesNotThrow(() -> Validations.validateSave(dto));
    }

    @Test
    void validateSaveUpdateRunsUpdateGroupConstraints() {
        UpdateSampleDto dto = new UpdateSampleDto();
        dto.setId(10L);

        BusinessException ex = assertThrows(BusinessException.class, () -> Validations.validateSave(dto));

        assertEquals(SystemErrorCode.PARAM_INVALID.code(), ex.getErrorCode());
        assertEquals(SystemErrorCode.PARAM_REQUIRED.code(), ex.getFieldErrors().getFirst().getErrorCode());
    }

    @Test
    void validateSaveUpdateAllowsPartialUpdateWithoutUpdateGroupConstraints() {
        PartialUpdateSampleDto dto = new PartialUpdateSampleDto();
        dto.setId(10L);
        dto.setRoleName("new-name");

        assertDoesNotThrow(() -> Validations.validateSave(dto));
    }

    @Test
    void validateSaveRunsDefaultGroupSizeConstraints() {
        CreateSampleDto dto = new CreateSampleDto();
        dto.setOrganId(1L);
        dto.setRoleName("x".repeat(65));

        BusinessException ex = assertThrows(BusinessException.class, () -> Validations.validateSave(dto));

        assertEquals(SystemErrorCode.PARAM_INVALID.code(), ex.getErrorCode());
        assertEquals(SystemErrorCode.PARAM_EXCEEDS_SIZE.code(), ex.getFieldErrors().getFirst().getErrorCode());
        assertEquals("roleName", ex.getFieldErrors().getFirst().getField());
    }

    @Getter
    @Setter
    private static class CreateSampleDto extends BaseDto {
        @NotNull(groups = CreateGroup.class)
        private Long organId;

        @NotBlank(groups = CreateGroup.class)
        @Size(max = 64)
        private String roleName;
    }

    @Getter
    @Setter
    private static class UpdateSampleDto extends BaseDto {
        @NotNull(groups = UpdateGroup.class)
        private Long organId;
    }

    @Getter
    @Setter
    private static class PartialUpdateSampleDto extends BaseDto {
        @NotNull(groups = CreateGroup.class)
        private Long organId;

        @Size(max = 64)
        private String roleName;
    }
}
