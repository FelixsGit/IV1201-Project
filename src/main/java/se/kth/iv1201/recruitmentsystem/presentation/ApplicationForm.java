package se.kth.iv1201.recruitmentsystem.presentation;

import javax.validation.constraints.NotEmpty;

public class ApplicationForm {

    @NotEmpty(message = "Please choose an area of expertise")
    private String expertise;

    @NotEmpty(message = "Please select a from-date")
    private String fromDate;

    @NotEmpty(message = "Please select a to-date")
    private String toDate;

    @NotEmpty(message = "Please fill in how many years of experience you have")
    private String yearsOfExperience;

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

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
