package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.customvalidation.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends BaseDto {

    private String userId;

    @Size(min = 3,max = 25,message = "Invalid Name!!")
    private String name;
   // @Email(message = "Invalid User Email!!")
    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",message = "Invalid User Email!!!")
    @NotBlank(message = "email is Required!!!")
    private String email;
    @NotBlank(message = "Password is Required!!")
    private String password;
    @Size(min = 4,max = 6,message = "Invalid gender!!")
    private String gender;

    @NotBlank(message = "Writing something!!")
    private String about;

    @ImageNameValid
    private String imagename;

}
