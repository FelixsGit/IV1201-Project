package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "competence")
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "competence_id")
    private long competence_id;

    @Column(name = "name")
    private String name;

    /**
     * Required by JPA, do not use.
     */
    protected Competence() {

    }

    /**
     * Creates new instance of Competence, reuse existing in database when possible!
     * @param name The name of the Competence.
     */
    public Competence(String name) {
        this.name = name;
    }

    public long getCompetence_id() {
        return competence_id;
    }

    public void setCompetence_id(long competence_id) {
        this.competence_id = competence_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
