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
import se.kth.iv1201.recruitmentsystem.domain.Role;

import java.io.IOException;

import static junit.framework.TestCase.assertNull;
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
public class RoleRepositoryTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private RoleRepository instance;

    private Role recruit, applicant;


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
        applicant = instance.findRoleByName(Role.APPLICANT);
        recruit = instance.findRoleByName(Role.RECRUITER);
    }

    @Test
    void testFindApplicantRole() {
        startNewTransaction();
        Role role = instance.findRoleByName(Role.APPLICANT);
        assertApplicant(role);
    }

    @Test
    void testFindRecruitRole() {
        startNewTransaction();
        Role role = instance.findRoleByName(Role.RECRUITER);
        assertRecruit(role);
    }

    @Test
    void testFindNonExistingRoleByName() {
        startNewTransaction();
        Role role = instance.findRoleByName("nonExistingName");
        assertNull(role);
    }

    @Test
    void testCallRepoWithoutTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.save(applicant);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findRoleByName(applicant.getName());
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findAll();
        });
    }

    private void assertApplicant(Role role) {
        assertThat(role.getName(), is(applicant.getName()));
        assertThat(role.getRole_id(), is(applicant.getRole_id()));
    }

    private void assertRecruit(Role role) {
        assertThat(role.getName(), is(recruit.getName()));
        assertThat(role.getRole_id(), is(recruit.getRole_id()));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
