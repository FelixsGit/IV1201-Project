package se.kth.iv1201.recruitmentsystem.presentation;


import javax.validation.constraints.NotEmpty;

public class ApplicationForm {

    @NotEmpty(message = "please enter a from date")
    private String fromDate;

    @NotEmpty(message = "please enter a to date")
    private String toDate;

    private String competence;

    @NotEmpty(message = "please enter your experience")
    private String yearsOfExperience;

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
