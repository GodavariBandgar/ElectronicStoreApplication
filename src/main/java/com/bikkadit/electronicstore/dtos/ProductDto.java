package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.entities.Category;
import lombok.*;

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

    @NotBlank
    private String description;

    private double price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CategoryDto category;
}
