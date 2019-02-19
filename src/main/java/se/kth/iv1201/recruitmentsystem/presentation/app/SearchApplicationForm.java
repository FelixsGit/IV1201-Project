package se.kth.iv1201.recruitmentsystem.presentation.app;

/**
 * A bean for the search application form.
 */
public class SearchApplicationForm {

    private String email;
    private String ssn;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
