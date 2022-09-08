package com.mc.simply.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(title = "Пользователь", description = "Данные для создания/редактирования пользователя")
public class UserDto {

    @NotBlank
    @Schema(description = "Имя", required = true)
    private String name;
}
