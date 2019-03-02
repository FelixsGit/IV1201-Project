package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Competence;
import se.kth.iv1201.recruitmentsystem.domain.Person;

import java.util.List;

/**
 * Handles all database access regarding the <code>Competence</code> Entity and it's table.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface CompetenceRepository extends JpaRepository<Competence, String> {

    /**
     * Fetches the Competence with the specified name.
     * @param name The name of the competence.
     * @return The Competence with the name, or null if none is found.
     */
    Competence findCompetenceByName(String name);

    /**
     * Fetches a list of all the different competence.
     * @return the list of all competences.
     */
    @Override
    List<Competence> findAll();

}
