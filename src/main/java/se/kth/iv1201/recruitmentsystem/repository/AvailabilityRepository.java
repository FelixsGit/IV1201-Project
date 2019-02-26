package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Availability;
import se.kth.iv1201.recruitmentsystem.domain.Person;

import java.util.List;

/**
 * Handles all database access regarding the <code>Availability</code> Entity and it's table.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AvailabilityRepository extends JpaRepository<Availability, Person> {

    /**
     * Finds the availability made by the specified person.
     * @param person The owner of the availability post.
     * @return The matching availability, or null if none exists.
     */
    Availability findAvailabilityByPerson(Person person);

    @Override
    Availability save(Availability availability);

    @Override
    List<Availability> findAll();

    /**
     * Finds all availabilities that the entered person has.(one for each application).
     * @param person The person object.
     * @return a list of availabilities that the person object has.
     */
    List<Availability> findAvailabilitiesByPerson(Person person);
}
