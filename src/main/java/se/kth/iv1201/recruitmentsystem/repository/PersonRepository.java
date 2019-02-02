package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Person;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface PersonRepository extends JpaRepository<Person, String> {

    /**
     * Returns the person with the specified username, or null if no person was found.
     * @param username The username of the person
     * @return The specified person
     */
    Person findPersonByUsername(String username);

    //@Override
    //Person save(Person person);
}
