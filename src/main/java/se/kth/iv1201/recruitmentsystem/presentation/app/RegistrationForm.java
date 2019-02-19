package se.kth.iv1201.recruitmentsystem.presentation.app;

import javax.validation.GroupSequence;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A bean for the Registration form.
 */
public class RegistrationForm {
    private final String msgL = "{general-input.msg-length}";

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{reg.name.missing}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{general-input.invalid-char}")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "{general-input.invalid-char}")
    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{reg.surname.missing}")
    private String surname;

    //@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",
      //      message = "Please choose an email with a correct format")
    @Size(min = 2, max = 30, message = msgL)
    @Email(message = "{reg.email.incorrect}")
    @NotEmpty(message = "{reg.email.missing}")
    private String email;

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{reg.password.missing}")
    private String password;

    @Size(min = 2, max = 30, message = msgL)
    @NotEmpty(message = "{reg.username.missing}")
    private String username;

    @Size(min = 2, max = 30, message = msgL)
    @Pattern(regexp = "^(19|20)?[0-9]{8}[- ]?[0-9]{4}$", message = "{reg.ssn.incorrect}")
    @NotEmpty(message = "{reg.ssn.missing}")
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
