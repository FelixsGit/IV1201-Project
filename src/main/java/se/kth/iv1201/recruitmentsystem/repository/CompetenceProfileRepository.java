package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Competence;
import se.kth.iv1201.recruitmentsystem.domain.CompetenceProfile;
import se.kth.iv1201.recruitmentsystem.domain.Person;

import java.util.List;

/**
 * Handles all database access regarding the <code>CompetenceProfile</code> Entity and it's table.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfile, String> {

    /**
     * Fetches a competence profiles identified by the competence specified.
     * @param competence The competence in the competence profile.
     * @return The competence profiles found, or null if none.
     */
    List<CompetenceProfile> findCompetenceProfilesByCompetence(Competence competence);


    /**
     * Fetches all competence profiles in the db from the entered person.
     * @param person The person object.
     * @return a list of competence profiles matching the entered person.
     */
    List<CompetenceProfile> findCompetenceProfilesByPerson(Person person);

    /**
     * Fetches a competence profile identified uniquely by person and competence.
     * @param person The person owning the competence profile.
     * @param competence The competence related to the competence profile.
     * @return Returns the competence profile found, or null if none found.
     */
    CompetenceProfile findCompetenceProfileByPersonAndCompetence(Person person, Competence competence);


    @Override
    CompetenceProfile save(CompetenceProfile competenceProfile);

}
