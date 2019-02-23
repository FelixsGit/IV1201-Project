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
import se.kth.iv1201.recruitmentsystem.domain.Competence;
import java.io.IOException;
import java.util.List;

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
public class CompetenceRepositoryTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private CompetenceRepository instance;

    private List<Competence> competenceList;


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
        competenceList = instance.findAll();
    }

    @Test
    void testFindCompetenceList() {
        startNewTransaction();
        List<Competence> competenceList = instance.findAll();
        assertCompetenceList(competenceList);
    }

    @Test
    void testCallRepoWithoutTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findAll();
        });
    }

    private void assertCompetenceList(List<Competence> competences) {
        assertThat(competences, is(this.competenceList));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
