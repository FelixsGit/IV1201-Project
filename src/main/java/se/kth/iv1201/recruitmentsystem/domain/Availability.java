package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "availability_id")
    private long person_id;

    //TODO reference? name?
    @Column(name = "person")
    private Person person;

    @Column(name = "from_date")
    private String from_date;

    @Column(name = "to_date")
    private String to_date;

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }
}
