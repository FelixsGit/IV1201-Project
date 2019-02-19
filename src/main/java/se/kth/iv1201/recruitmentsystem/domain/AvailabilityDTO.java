package se.kth.iv1201.recruitmentsystem.domain;

import java.util.Date;

/**
 * Defines all the operations that can be performed on a {@link Availability} in
 * the application and domain layers.
 */
public interface AvailabilityDTO {

    /**
     * @return The person of this availability post.
     */
    Person getPerson();

    /**
     * @return The starting date of the post.
     */
    Date getFrom_date();

    /**
     * @return The ending date of the post.
     */
    Date getTo_date();

}
