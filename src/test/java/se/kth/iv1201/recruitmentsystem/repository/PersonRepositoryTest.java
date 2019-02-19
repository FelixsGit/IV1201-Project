package se.kth.iv1201.recruitmentsystem.repository;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.Role;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        PersonRepositoryTest.class,
        TransactionalTestExecutionListener.class})

@Transactional
@Commit
public class PersonRepositoryTest implements TestExecutionListener {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private PersonRepository instance;
    @Autowired
    private RoleRepository roleRepository;

    private Person person;

    @Override
    public void beforeTestClass(TestContext testContext) throws IOException {
        dbUtil = testContext.getApplicationContext().getBean(DBUtil.class);
        dbUtil.resetDB();
    }

    @Override
    public void afterTestClass(TestContext testContext) throws IOException {
        dbUtil.resetDB();
    }

    @BeforeEach
    void setup() throws IOException {
        dbUtil.resetDB();
        Role role = new Role(Role.APPLICANT);
        roleRepository.save(role);
        person = new Person("testName", "testSurname", "19950411-1111",
                "test@te.se", "123", role, "testUsername");
    }

    @Test
    void testCreatePerson() {
        instance.save(person);
        startNewTransaction();
        List<Person> persons = instance.findAll();
        assertPerson(persons.get(0));
    }

    @Test
    void testFindExistingPersonByUsername() {
        instance.save(person);
        startNewTransaction();
        Person person = instance.findPersonByUsername(this.person.getUsername());
        assertPerson(person);
    }

    @Test
    void testFindExistingPersonByEmail() {
        instance.save(person);
        startNewTransaction();
        Person person = instance.findPersonByEmail(this.person.getEmail());
        assertPerson(person);
    }

    @Test
    void testFindExistingPersonBySsn() {
        instance.save(person);
        startNewTransaction();
        Person person = instance.findPersonBySsn(this.person.getSsn());
        assertPerson(person);
    }

    @Test
    void testFindNonExistingPersonByUsername() {
        instance.save(person);
        startNewTransaction();
        Person person = instance.findPersonByUsername("nonExistingName");
        assertNull(person);
    }

    @Test
    void testCallRepoWithoutTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.save(person);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findPersonByUsername(person.getUsername());
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findAll();
        });
    }

    void assertPerson(Person person) {
        assertThat(person.getUsername(), is(this.person.getUsername()));
        assertThat(person.getEmail(), is(this.person.getEmail()));
        assertThat(person.getSsn(), is(this.person.getSsn()));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }

}
