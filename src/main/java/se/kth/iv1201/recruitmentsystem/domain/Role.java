package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "role")
public class Role implements RoleDTO {

    public static final String RECRUITER = "recruit";
    public static final String APPLICANT = "applicant";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long role_id;

    @NotNull(message = "{role.name.missing}")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "{general-input.invalid-char}")
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
