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
import se.kth.iv1201.recruitmentsystem.repository.CompetenceRepository;
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
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, RoleTest.class, TransactionalTestExecutionListener.class})

@NotThreadSafe
@Transactional
@Commit
class CompetenceTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private CompetenceRepository competenceRepository;

    private Competence instance;

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
        instance = new Competence("recruiter");
    }

    @Test
    void testCompetenceProfileIdIsGenerated() {
        competenceRepository.save(instance);
        assertThat(instance.getCompetence_id(), is(not(0L)));
    }

    @Test
    @Rollback
    void testMissingName() {
        instance.setName(null);
        testInvalidCompetence(instance, "{competence.name.missing}");
    }

    @Test
    @Rollback
    void testIncorrectName() {
        instance.setName("7472628");
        testInvalidCompetence(instance, "{competence.name.invalid-char}");
    }

    private void testInvalidCompetence(Competence competence, String... expectedMsgs) {
        try {
            startNewTransaction();
            competenceRepository.save(competence);
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
