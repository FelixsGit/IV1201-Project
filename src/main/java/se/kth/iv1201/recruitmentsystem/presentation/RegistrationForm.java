package se.kth.iv1201.recruitmentsystem.presentation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RegistrationForm {
    //private final String errMsg = "Needs to be filled in";

    @NotEmpty(message = "please enter a name")
    private String name;

    @NotEmpty(message = "please enter a surname")
    private String surname;

    @NotEmpty(message = "please enter a email")
    private String email;

    @NotEmpty(message = "please enter a password")
    private String password;

    @NotEmpty(message = "please enter a username")
    private String username;

    @NotEmpty(message = "please enter date of birth")
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
