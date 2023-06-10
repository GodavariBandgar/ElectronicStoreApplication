package com.bikkadit.electronicstore.dtos;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
@MappedSuperclass
@Setter
@Getter
public class BaseDto {

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    public String createdBy;

    @Column(name = "created_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;

    @Column(name = "updated_by", nullable = false)
    @LastModifiedBy
    private String lastModifiedBy;

    @Column(name = "is_active")
    private String isActive;



}
