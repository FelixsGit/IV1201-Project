package se.kth.iv1201.recruitmentsystem.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.PersonDTO;
import se.kth.iv1201.recruitmentsystem.domain.Role;
import se.kth.iv1201.recruitmentsystem.domain.UserException;
import se.kth.iv1201.recruitmentsystem.repository.PersonRepository;
import se.kth.iv1201.recruitmentsystem.repository.RoleRepository;

/**
 * This class defines tasks that can be performed by the domain layer.
 *
 * Each method in this class corresponds to a transaction. The transaction starts
 * when the presentation layer calls a method and ends when a method returns.
 * The transaction ends with a commit if everything is well, or with a rollback
 * if an exception was thrown.
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class ApplicationService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    public PersonDTO findPerson(String username){
        return personRepository.findPersonByUsername(username);
    }

    /**
     * Attempts to create a person with specified i18n.
     * @param name The name of the person.
     * @param surname The surname of the person.
     * @param ssn The social security number of the person.
     * @param email The email of the person.
     * @param password The password of the person.
     * @param roleName The name of the role of the person.
     * @param username The username of the person.
     * @return The DTO corresponding to the created person
     * @throws UserException Thrown if Person could not be created.
     */
    public PersonDTO createPerson(String name, String surname, String ssn, String email,
                                  String password, String roleName, String username) throws UserException {

        Role role = roleRepository.findRoleByName(roleName);
        if(role == null)
            throw new UserException("Role name " + roleName + " does not exist in database.");

        if(personRepository.findPersonByUsername(username) != null)
            throw new UserException("Username " + username + " is already taken.");

        if(personRepository.findPersonByEmail(email) != null)
            throw new UserException("Email " + email + " is already in use.");

        if(personRepository.findPersonBySsn(ssn) != null)
            throw new UserException("SSN " + ssn + " is already registered.");

        Person person = new Person(name, surname, ssn, email, password, role, username);
        personRepository.save(person);
        return person;
    }
}
