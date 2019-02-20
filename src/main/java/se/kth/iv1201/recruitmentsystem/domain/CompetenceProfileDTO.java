package se.kth.iv1201.recruitmentsystem.domain;

import java.math.BigDecimal;

/**
 * Defines all the operations that can be performed on a {@link CompetenceProfile} in
 * the application and domain layers.
 */
public interface CompetenceProfileDTO {

    /**
     * @return The person of the CompetenceProfile.
     */
    Person getPerson();

    /**
     * @return The competence for the profile.
     */
    Competence getCompetence();

    /**
     * @return The number of years of experience regarding the competence.
     */
    BigDecimal getYears_of_experience();

}
