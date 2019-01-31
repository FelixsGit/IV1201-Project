package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Role;

@Repository
//@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Returns the role with the specified name.
     * @param name The name of the role
     * @return the specified role
     */
    Role findRoleByName(String name);

}
