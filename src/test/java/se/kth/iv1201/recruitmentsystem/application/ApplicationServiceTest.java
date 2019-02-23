package se.kth.iv1201.recruitmentsystem.application;

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
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.recruitmentsystem.domain.*;
import se.kth.iv1201.recruitmentsystem.repository.*;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"se.kth.iv1201.recruitmentsystem"})
@SpringBootTest
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        ApplicationServiceTest.class,
        TransactionalTestExecutionListener.class})

@Transactional
@Commit
public class ApplicationServiceTest implements TestExecutionListener {
    @Autowired
    private DBUtil dbUtil;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CompetenceRepository competenceRepository;
    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;
    @Autowired
    private AvailabilityRepository availabilityRepository;

    private List<Competence> competences;

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
    void setup() throws Exception {
        dbUtil.resetDB();
    }

    @Test
    void testFindCompetences(){
        competences = applicationService.findCompetences();
        assertThat(competences.get(0).getName(), is("Korvgrillning"));
        assertThat(competences.get(1).getName(), is("Karuselldrift"));
        assertNotNull(competences);
    }

    @Test
    void testCreateApplication() throws UserException, ParseException, ApplicationException {
        String testChosenCompetence = "Korvgrillning";
        String testFromDate = "2019-04-26";
        String testToDate = "2020-02-25";
        String testYearsOfExperience = "1.25";
        String testUsername = "felixTester";
        applicationService.createPerson(
                "testName", "testSurname", "19950417-1252", "FelixTestEmail@test.com", "testPassword", "applicant", testUsername);
        applicationService.createApplication(testChosenCompetence, testFromDate, testToDate, testYearsOfExperience, testUsername);
        startNewTransaction();
        Person person = personRepository.findPersonByUsername(testUsername);
        Availability availability = availabilityRepository.findAvailabilityByPerson(person);
        Competence competence = competenceRepository.findCompetenceByName(testChosenCompetence);
        CompetenceProfile competenceProfile = competenceProfileRepository.findCompetenceProfileByPersonAndCompetence(person, competence);
        assertThat(competenceProfile.getYears_of_experience().toString(), is(testYearsOfExperience));
        assertThat(competenceProfile.getCompetence().getName(), is(testChosenCompetence));
    }

    private Date convertToDate(String startingDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date convertedDate = sdf.parse(startingDate);
        return new java.sql.Date(convertedDate.getTime());
    }

    @Test
    void testCreatePerson() throws UserException {
        String testName = "testUser";
        String testSurname = "testSurname";
        String testEmail = "testEmail@test.com";
        String testPassword = "testPassword";
        String testUsername = "testUsername";
        String testSsn = "19950411-1111";
        applicationService.createPerson(
                testName, testSurname, testSsn, testEmail, testPassword, Role.APPLICANT, testUsername
        );
        startNewTransaction();
        PersonDTO person = personRepository.findPersonByUsername(testUsername);
        assertNotNull(person);
        assertThat(person.getName(), is(testName));
        assertThat(person.getSurname(), is(testSurname));
        assertThat(person.getEmail(), is(testEmail));
        assertThat(person.getPassword(), is(testPassword));
        assertThat(person.getUsername(), is(testUsername));
        assertThat(person.getSsn(), is(testSsn));
    }

    @Test
    void testCreatePersonIncorrectRole() throws UserException {
        UserException userException = assertThrows(UserException.class, () -> {
            applicationService.createPerson(
                    "testName", "testSurname", "19950411-1111", "testEmail@test.com", "testPassword", "incorrect", "testUser"
            );
        });
        assertThat(userException.getMessage().toLowerCase(), containsString("role"));
    }

    @Test
    void testCreatePersonUsernameTaken() throws UserException {
        applicationService.createPerson(
                "testName", "testSurname", "19950411-1111", "testEmail@test.com", "testPassword", Role.APPLICANT, "testUser"
        );
        UserException userException = assertThrows(UserException.class, () -> {
            // Same username, but different email and ssn
            applicationService.createPerson(
                    "testName", "testSurname", "19950411-1112", "testEmail2@test.com", "testPassword", Role.APPLICANT, "testUser"
            );
        });
        assertThat(userException.getMessage().toLowerCase(), containsString("username"));
    }

    @Test
    void testCreatePersonEmailTaken() throws UserException {
        applicationService.createPerson(
                "testName", "testSurname", "19950411-1111", "testEmail@test.com", "testPassword", Role.APPLICANT, "testUser"
        );
        UserException userException = assertThrows(UserException.class, () -> {
            // Same email, but different username and ssn
            applicationService.createPerson(
                    "testName", "testSurname", "19950411-1112", "testEmail@test.com", "testPassword", Role.APPLICANT, "testUser2"
            );
        });
        assertThat(userException.getMessage().toLowerCase(), containsString("email"));
    }

    @Test
    void testCreatePersonSsnTaken() throws UserException {
        applicationService.createPerson(
                "testName", "testSurname", "19950411-1111", "testEmail@test.com", "testPassword", Role.APPLICANT, "testUser"
        );
        UserException userException = assertThrows(UserException.class, () -> {
            // Same ssn, but different username and email
            applicationService.createPerson(
                    "testName", "testSurname", "19950411-1111", "testEmail2@test.com", "testPassword", Role.APPLICANT, "testUser2"
            );
        });
        assertThat(userException.getMessage().toLowerCase(), containsString("ssn"));
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }

}
