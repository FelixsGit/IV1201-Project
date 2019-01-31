package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private long availability_id;

    //@Column(name = "person")
    //TODO reference? name?
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "from_date")
    private Date from_date;

    @Column(name = "to_date")
    private Date to_date;

    /**
     * Required by JPA, do not use.
     */
    protected Availability() {

    }

    /**
     * Creates new instance belonging to the specified person.
     * @param person Person corresponding to this instance
     * @param from_date Availability start date
     * @param to_date Availability end date
     */
    public Availability(Person person, Date from_date, Date to_date) {
        this.person = person;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    public long getAvailability_id() {
        return availability_id;
    }

    public void setAvailability_id(long availability_id) {
        this.availability_id = availability_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }
}
