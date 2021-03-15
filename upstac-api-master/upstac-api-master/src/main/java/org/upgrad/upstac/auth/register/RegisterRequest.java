package org.upgrad.upstac.auth.register;


import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.upgrad.upstac.users.models.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegisterRequest {

    @ApiModelProperty(example = "newuser")
    @NotNull
    private String userName;

    @ApiModelProperty(example = "password")
    @NotNull
    @Size(min = 8,max = 255)
    private String password;

    @ApiModelProperty(example = "MK")
    private String firstName;


    @ApiModelProperty(example = "newuser@upgrad.com")
    @NotNull
    private String email="";

    @ApiModelProperty(example = "988989232")
    @NotNull
    private String phoneNumber="";


    @ApiModelProperty(example = "Gandhi")
    private String lastName;

    @ApiModelProperty(example = "Some Where in India ")
    private String address;

    @ApiModelProperty(example = "602102")
    @NotNull
    private Integer pinCode;


    @ApiModelProperty(example = "1981-11-21")
    @NotNull
    private String dateOfBirth;


    @ApiModelProperty(example = "FEMALE")
    @NotNull
    private Gender gender;



}
