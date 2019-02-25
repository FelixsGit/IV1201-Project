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
import se.kth.iv1201.recruitmentsystem.repository.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
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
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, RoleTest.class, TransactionalTestExecutionListener.class})

@NotThreadSafe
@Transactional
@Commit
class AvailabilityTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;

    private Availability availabilityInstance;
    private Person personInstance;
    private Role roleInstance;

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
        createPerson();
        availabilityInstance = new Availability(personInstance, new Date(), new Date());
    }

    private void createPerson() {
        roleInstance = new Role(Role.APPLICANT);
        roleRepository.save(roleInstance);
        personInstance = new Person("Adrian", "Zander", "19970215-1625", "adrian.t.zander@gmail.com", "123",
                roleInstance, "Acander5");
        personRepository.save(personInstance);
    }

    @Rollback
    @Test
    void testMissingPerson() {
        availabilityInstance.setPerson(null);
        testInvalidAvailability(availabilityInstance, "{availability.person.missing}");
    }

    @Rollback
    @Test
    void testMissingToDate() {
        availabilityInstance.setTo_date(null);
        testInvalidAvailability(availabilityInstance, "{availability.to-date.missing}");
    }

    @Rollback
    @Test
    void testMissingFromDate() {
        availabilityInstance.setFrom_date(null);
        testInvalidAvailability(availabilityInstance, "{availability.from-date.missing}");
    }

    private void testInvalidAvailability(Availability availability, String... expectedMsgs) {
        try {
            startNewTransaction();
            availabilityRepository.save(availability);
        } catch (ConstraintViolationException exc) {
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
