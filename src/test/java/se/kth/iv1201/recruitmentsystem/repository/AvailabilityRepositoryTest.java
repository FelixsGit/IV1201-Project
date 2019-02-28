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
import se.kth.iv1201.recruitmentsystem.domain.Availability;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.Role;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        RoleRepositoryTest.class,
        TransactionalTestExecutionListener.class})

@Transactional
@Commit
public class AvailabilityRepositoryTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private AvailabilityRepository instance;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonRepository personRepository;

    private Availability availability;
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
        startNewTransaction();
        Role role = new Role(Role.APPLICANT);
        roleRepository.save(role);
        person = new Person("testName", "testSurname", "19950411-1111",
                "test@te.se", "123", role, "testUsername");
        personRepository.save(person);
        try {
            availability = new Availability(person, convertToDate("2016-02-24"), convertToDate("2018-03-23"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        instance.save(availability);
    }

    private Date convertToDate(String startingDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date convertedDate = sdf.parse(startingDate);
        return new java.sql.Date(convertedDate.getTime());
    }

    @Test
    void testFindCompetenceList() {
        startNewTransaction();
        availability = instance.findAvailabilityByPerson(person);
        assertAvailability(availability);
    }

    @Test
    void testCallRepoWithoutTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findAvailabilityByPerson(person);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findAll();
        });
    }

    private void assertAvailability(Availability availability) {
        assertThat(availability, is(this.availability));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
