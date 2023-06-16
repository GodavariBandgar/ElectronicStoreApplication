package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.customvalidation.ImageNameValid;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "description is must be required")
    private String description;

    @ImageNameValid
    private String coverImage;

}
