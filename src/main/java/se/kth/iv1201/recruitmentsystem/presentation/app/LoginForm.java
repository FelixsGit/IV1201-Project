package se.kth.iv1201.recruitmentsystem.presentation.app;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A bean for the Login form.
 */
public class LoginForm {
    private final String msgL = "{general-input.msg-length}";

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{login.username.missing}")
    private String username;

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{login.password.missing}")
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

