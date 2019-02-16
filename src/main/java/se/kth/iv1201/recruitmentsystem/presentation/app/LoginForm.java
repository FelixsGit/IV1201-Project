package se.kth.iv1201.recruitmentsystem.presentation.app;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginForm {
    private final String msgL = "Please choose a name between 2 and 30 characters";

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "Please enter your username")
    private String username;

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "Please enter your password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

