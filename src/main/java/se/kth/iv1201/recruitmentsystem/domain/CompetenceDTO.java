package se.kth.iv1201.recruitmentsystem.domain;

/**
 * Defines all the operations that can be performed on a {@link Competence} in
 * the application and domain layers.
 */
public interface CompetenceDTO {

    /**
     * @return The name of the competence, in swedish.
     */
    String getName();

    /**
     * @return The name of the competence, in english.
     */
    String getNameEn();

}
