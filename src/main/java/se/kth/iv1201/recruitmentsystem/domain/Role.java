package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "role_id")
    //@JoinColumn(name = "role_id")
    private long role_id;

    @Column(name = "name")
    private String name;


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
