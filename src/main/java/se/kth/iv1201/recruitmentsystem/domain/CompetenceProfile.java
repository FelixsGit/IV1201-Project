package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "competence_profile")
public class CompetenceProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_profile_id")
    private long competence_profile_id;

    //@Column(name = "person")
    // TODO reference? name?
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    //@Column(name = "competence")
    // TODO reference? name?
    @ManyToOne
    @JoinColumn(name = "competence_id")
    private Competence competence;

    @Column(name = "years_of_experience")
    private BigDecimal years_of_experience;

    /**
     * Required by JPA, do not use.
     */
    protected CompetenceProfile() {

    }

    /**
     * Creates new CompetenceProfile about specified competence belonging
     * to the specified person.
     * @param person
     * @param competence
     * @param years_of_experience
     */
    public CompetenceProfile(Person person, Competence competence, BigDecimal years_of_experience) {
        this.person = person;
        this.competence = competence;
        this.years_of_experience = years_of_experience;
    }

    public long getCompetence_profile_id() {
        return competence_profile_id;
    }

    public void setCompetence_profile_id(long competence_profile_id) {
        this.competence_profile_id = competence_profile_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
    }

    public BigDecimal getYears_of_experience() {
        return years_of_experience;
    }

    public void setYears_of_experience(BigDecimal years_of_experience) {
        this.years_of_experience = years_of_experience;
    }
}
