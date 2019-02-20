package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Role;

import java.util.List;

/**
 * Handles all database access regarding the <code>Role</code> Entity and it's table.
 */
@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Returns the role with the specified name.
     * @param name The name of the role
     * @return the specified role
     */
    Role findRoleByName(String name);

    @Override
    List<Role> findAll();

    @Override
    Role save(Role role);

}
