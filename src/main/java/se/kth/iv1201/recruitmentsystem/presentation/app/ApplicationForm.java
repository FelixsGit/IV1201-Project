package se.kth.iv1201.recruitmentsystem.presentation.app;


import se.kth.iv1201.recruitmentsystem.domain.Competence;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * A bean for the apply application form.
 */
public class ApplicationForm {

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Please enter a correct date format")
    @NotEmpty(message = "please enter a from date")
    private String fromDate;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Please enter a correct date format")
    @NotEmpty(message = "please enter a to date")
    private String toDate;

    private String competence;

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    private List<Competence> competences;

    @Pattern(regexp = "^[0-9]*$", message = "numbers only")
    @Size(max = 10, message = "Please write no more than 10 characters")
    @NotEmpty(message = "please enter your experience")
    private String yearsOfExperience;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    private String email;
    private String ssn;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
