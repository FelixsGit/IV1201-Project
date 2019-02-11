package se.kth.iv1201.recruitmentsystem.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "person")
public class Person implements PersonDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private long person_id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "ssn")
    private String ssn;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    //@Column(name = "role")
    @NotNull(message = "Role missing")
    // TODO reference?
    //@OneToOne(mappedBy = "person", cascade =  CascadeType.ALL)
    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

    @Column(name = "username")
    private String username;

    /**
     * Required by JPA, do not use.
     */
    protected Person() {

    }

    /**
     * Creates a new person with specified properties.
     * @param name The name of the person
     * @param surname The surname of the person
     * @param ssn The social security number of the person
     * @param email The email belonging to the person
     * @param password The password belonging to the person
     * @param role The role of the person
     * @param username The username belonging to the person
     */
    public Person(String name, String surname, String ssn, String email, String password,
                  @NotNull(message = "Role missing") Role role, String username) {
        this.name = name;
        this.surname = surname;
        this.ssn = ssn;
        this.email = email;
        this.password = password;
        this.role = role;
        this.username = username;
    }

    public Person(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
        this.ssn = person.getSsn();
        this.email = person.getEmail();
        this.password = person.getPassword();
        this.role = person.getRole();
        this.username = person.getUsername();
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
