package com.bikkadit.electronicstore.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name = "category_title",length = 60,nullable = false)
    private String title;

    @Column(name = "category_desc",length = 55)
    private String description;

    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)                   //jab category fetch karenge to product na fetch ho or product fetch ho on demand per isliye lazy ghetlay
    private List<Product> products=new ArrayList<>();

}
