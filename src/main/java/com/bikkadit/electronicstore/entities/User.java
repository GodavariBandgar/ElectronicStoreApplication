package com.bikkadit.electronicstore.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")

public class User {

        @Id
        private String userId;

        @Column(name = "user_name", nullable = false)
        private String name;

        @Column(name = "user_email", unique = true, nullable = false)
        private String email;

        @Column(name = "user_password", length=10, nullable = false)
        private String password;

        private String gender;

        @Column(length = 1000)
        private String about;

        @Column(name = "user_image_name")
        private String imagename;
}
