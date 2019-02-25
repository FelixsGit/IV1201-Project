package se.kth.iv1201.recruitmentsystem.domain;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;
import se.kth.iv1201.recruitmentsystem.repository.PersonRepository;
import se.kth.iv1201.recruitmentsystem.repository.RoleRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, PersonTest.class, TransactionalTestExecutionListener.class})

@NotThreadSafe
@Transactional
@Commit
class PersonTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Person personInstance;
    //private Role roleInstance;

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
    void setup() throws SQLException, IOException, ClassNotFoundException {
        dbUtil.resetDB();
        Role roleInstance = new Role(Role.APPLICANT);
        roleRepository.save(roleInstance);
        personInstance = new Person("Adrian", "Zander", "19970215-1625", "adrian.t.zander@gmail.com", "123",
                roleInstance, "Acander5");
        //dbUtil.resetDB();
    }

    @Test
    void testCompetenceProfileIdIsGenerated() {
        personRepository.save(personInstance);
        assertThat(personInstance.getPerson_id(), is(not(0L)));
    }

    @Test
    @Rollback
    void testMissingName() {
        personInstance.setName(null);
        testInvalidPerson(personInstance, "{person.name.missing}");
    }

    private void testInvalidPerson(Person person, String... expectedMsgs) {
        try {
            personRepository.save(person);
            startNewTransaction();
        } catch (ConstraintViolationException exc) {
            System.out.println(exc.getConstraintViolations());
            Set<ConstraintViolation<?>> result = exc.getConstraintViolations();
            assertThat(result.size(), is(expectedMsgs.length));
            for (String expectedMsg : expectedMsgs) {
                assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
            }
        }
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }


}
