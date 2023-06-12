package com.bikkadit.electronicstore.dtos;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "description is must be required")
    private String description;

    @NotNull
    private String coverImage;

}
