package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role implements RoleDTO {

    public static final String RECRUITER = "recruit";
    public static final String APPLICANT = "applicant";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
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
