package se.kth.iv1201.recruitmentsystem.domain;

/**
 * This class is a applicationDTO object that holds information from one single application.
 */

public class ApplicationDTO {

    private String author;
    private String competence;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
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

    private String fromDate;
    private String toDate;
    private String yearsOfExperience;

    public ApplicationDTO(String author, String competence, String fromDate, String toDate, String yearsOfExperience){
        this.author = author;
        this.competence = competence;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.yearsOfExperience = yearsOfExperience;
    }

}
