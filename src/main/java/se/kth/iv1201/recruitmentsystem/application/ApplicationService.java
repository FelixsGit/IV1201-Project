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
import javax.annotation.PostConstruct;

// Operations should be transactions and should be rolled back when exceptions occur
// Every method call should result in a new transaction
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class ApplicationService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        /* Test code which works but should not be executed every time :)
        try {
            PersonDTO pdto = createPerson("Adrian", "Felixsson", "960403-1100", "addefelle@kth.se",
                    "hemligthemligt", "recruit", "DragonSlayer1337");
            System.out.println("Person created: " + pdto.getUsername());
        } catch (UserException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * Attempts to create a person with specified properties.
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
        if(personRepository.findPersonByEmail(email) != null) {
            throw new UserException("Email " + email + " is already in use.");
        }
        Person person = new Person(name, surname, ssn, email, password, role, username);
        // TODO: Handle if error on save?
        personRepository.save(person);
        return person;
    }
}
