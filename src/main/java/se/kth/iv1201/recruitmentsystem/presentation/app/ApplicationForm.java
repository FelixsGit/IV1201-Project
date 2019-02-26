package se.kth.iv1201.recruitmentsystem.presentation.app;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A bean for the apply application form.
 */
public class ApplicationForm {

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "{application.from-date.incorrect}")
    @NotEmpty(message = "{application.from-date.missing}")
    private String fromDate;

    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "{application.to-date.incorrect}")
    @NotEmpty(message = "{application.to-date.missing}")
    private String toDate;

    @Pattern(regexp = "[+-]?([0-9]*[.])?[0-9]+", message = "{application.years-of-exp.incorrect}")
    @Size(max = 10, message = "{application.years-of-exp.length}")
    @NotEmpty(message = "{application.years-of-exp.missing}")
    private String yearsOfExperience;

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    private String competence;

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

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
