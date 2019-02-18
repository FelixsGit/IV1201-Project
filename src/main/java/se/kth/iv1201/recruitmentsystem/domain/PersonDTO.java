package se.kth.iv1201.recruitmentsystem.domain;

/**
 * Defines all the operations that can be performed on a {@link Person} in
 * the application and domain layers.
 */
public interface PersonDTO {

    /**
     * @return The name of the person
     */
    String getName();

    /**
     * @return The surname of the person
     */
    String getSurname();

    /**
     * @return The social security number of the person
     */
    String getSsn();

    /**
     * @return The email of the person
     */
    String getEmail();

    /**
     * @return The password of the person
     */
    String getPassword();

    /**
     * @return The role of the person
     */
    Role getRole();

    /**
     * @return The username of the person
     */
    String getUsername();

}
