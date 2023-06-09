package com.bikkadit.electronicstore.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 3,max = 25,message = "Invalid Name!!")
    private String name;
    @Email(message = "Invalid User Email!!")
    private String email;
    @NotBlank(message = "Password is Required!!")
    private String password;
    @Size(min = 4,max = 6,message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Writing something!!")
    private String about;

    private String imagename;

}
