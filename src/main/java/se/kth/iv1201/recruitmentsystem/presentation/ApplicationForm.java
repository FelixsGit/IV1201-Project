package se.kth.iv1201.recruitmentsystem.presentation;

import javax.validation.constraints.NotEmpty;

public class ApplicationForm {

    @NotEmpty(message = "Please choose a are of expertise")
    String expertise;

    @NotEmpty(message = "Please select a from-date")
    String fromDate;

    @NotEmpty(message = "Please select a to-date")
    String toDate;

    @NotEmpty(message = "Please fill in how many years of experience you have")
    String yearsOfExperience;

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
