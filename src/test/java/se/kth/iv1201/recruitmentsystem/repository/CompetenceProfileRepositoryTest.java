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
import se.kth.iv1201.recruitmentsystem.domain.CompetenceProfile;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.Role;

import java.io.IOException;
import java.math.BigDecimal;
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
public class CompetenceProfileRepositoryTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private CompetenceProfileRepository instance;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompetenceRepository competenceRepository;


    private CompetenceProfile competenceProfile;
    private List<CompetenceProfile> competenceProfiles;
    private Person person;
    private Competence competence;


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
        competence = competenceRepository.findCompetenceByName("Korvgrillning");
        BigDecimal test_years_of_experience = new BigDecimal("1.25");
        competenceProfile = new CompetenceProfile(person, competence, test_years_of_experience);
    }

    @Test
    void testFindCompetenceByPersonAndCompetence() {
        startNewTransaction();
        instance.save(competenceProfile);
        competenceProfile = instance.findCompetenceProfileByPersonAndCompetence(person, competence);
        assertCompetenceProfile(competenceProfile);
    }

    @Test
    void findCompetenceProfileByPersonAndCompetence(){
        startNewTransaction();
        competenceProfiles = instance.findCompetenceProfilesByCompetence(competence);
        instance.save(competenceProfile);
        assertCompetenceProfileList(competenceProfiles);
    }

    @Test
    void testCallRepoWithoutTransaction() {
        TestTransaction.end();
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findCompetenceProfileByPersonAndCompetence(person, competence);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.save(competenceProfile);
        });
        assertThrows(IllegalTransactionStateException.class, () -> {
            instance.findCompetenceProfilesByCompetence(competence);
        });
    }

    private void assertCompetenceProfile(CompetenceProfile competenceProfile) {
        assertThat(competenceProfile, is(this.competenceProfile));
    }

    private void assertCompetenceProfileList(List<CompetenceProfile> competenceProfiles){
        assertThat(competenceProfiles, is(this.competenceProfiles));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}