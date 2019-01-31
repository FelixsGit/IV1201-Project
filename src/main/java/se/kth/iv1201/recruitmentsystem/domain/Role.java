package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;

import static se.kth.iv1201.recruitmentsystem.util.Constants.SEQUENCE_NAME;
import static se.kth.iv1201.recruitmentsystem.util.Constants.SEQUENCE_NAME_KEY;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_gen")
    @SequenceGenerator(name = "role_gen", sequenceName = "role_seq")
    @Column(name = "role_id", updatable = false, nullable = false)
    private long role_id;

    @Column(name = "name")
    private String name;

    /**
     * Required by JPA, do not use.
     */
    protected Role() {

    }

    /**
     * Creates a new Role, reuse from database when possible!
     * @param name The role name
     */
    public Role(String name) {
        this.name = name;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
