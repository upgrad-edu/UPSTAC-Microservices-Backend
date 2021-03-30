package org.upgrad.upstac.auth.register;


import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.upgrad.upstac.models.Gender;


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

    public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Integer getPinCode() {
		return pinCode;
	}


	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


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
