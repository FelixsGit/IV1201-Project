package se.kth.iv1201.recruitmentsystem.presentation;

import javax.validation.constraints.NotBlank;

public class RegistrationForm {
    private final String errMsg = "Needs to be filled in";

    @NotBlank(message = errMsg)
    private String name;

    @NotBlank(message = errMsg)
    private String surname;

    @NotBlank(message = errMsg)
    private String email;

    @NotBlank(message = errMsg)
    private String password;

    @NotBlank(message = errMsg)
    private String username;

    @NotBlank(message = errMsg)
    private String ssn;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }



}
