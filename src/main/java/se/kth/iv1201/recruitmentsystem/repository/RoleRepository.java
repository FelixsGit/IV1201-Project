package se.kth.iv1201.recruitmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.kth.iv1201.recruitmentsystem.domain.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * Returns the role with the specified name.
     * @param name The name of the role
     * @return the specified role
     */
    Role findRoleByName(String name);

}
