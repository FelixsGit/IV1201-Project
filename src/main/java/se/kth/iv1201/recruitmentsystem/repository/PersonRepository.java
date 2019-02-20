package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Person;

import java.util.List;

/**
 * Handles all database access regarding the <code>Person</code> Entity and it's table.
 */
@Repository
// It is required that a transaction is ongoing when arriving here
@Transactional(propagation = Propagation.MANDATORY)
public interface PersonRepository extends JpaRepository<Person, String> {

    /**
     * Returns the person with the specified username, or null if no person was found.
     * @param username The username of the person
     * @return The specified person
     */
    Person findPersonByUsername(String username);

    /**
     * Returns the person with the specified email, or null if no person was found.
     * @param email The email of the person
     * @return The specified person
     */
    Person findPersonByEmail(String email);

    /**
     * Returns the person with the specified email, or null if no person was found.
     * @param ssn The social security number of the person
     * @return The specified person
     */
    Person findPersonBySsn(String ssn);

    @Override
    List<Person> findAll();

    @Override
    Person save(Person person);
}
