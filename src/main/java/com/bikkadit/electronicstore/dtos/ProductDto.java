package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.customvalidation.ImageNameValid;
import com.bikkadit.electronicstore.entities.Category;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productId;
    @Size(min = 3,max = 27,message = "Invalid name")
    private String title;

    @NotBlank(message = "Description must be required")
    private String description;

    @NotBlank(message = "price will be required")
    private double price;
    @NotBlank(message = "must be required")
    private int discountedPrice;

    private int quantity;

    private Date addedDate;
    @NotBlank
    private boolean live;
    private boolean stock;
    @ImageNameValid
    private String productImageName;
    private CategoryDto category;
}
