package se.kth.iv1201.recruitmentsystem.presentation.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, ApplicationFormTest.class})
public class ApplicationFormTest implements TestExecutionListener {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private Validator validator;

    @Override
    public void beforeTestClass(TestContext testContext) throws IOException{
        dbUtil = testContext.getApplicationContext().getBean(DBUtil.class);
        dbUtil.resetDB();
    }

    private final String INVALID_YEARS_OF_EXPERIENCE = "abc";
    private final String INVALID_TO_FROM_DATE = "abc";
    private final String INVALID_YEARS_OF_EXPERIENCE_LENGTH = "12345678901";
    private final String VALID_YEARS_OF_EXPERIENCE= "1";
    private final String VALID_TO_DATE = "2020-02-13";
    private final String VALID_FROM_DATE = "2021-02-14";

    private final String INVALID_FROM_DATE_MSG = "{application.from-date.incorrect}";
    private final String FROM_DATE_MISSING_MSG = "{application.from-date.missing}";
    private final String INVALID_TO_DATE_MSG = "{application.to-date.incorrect}";
    private final String TO_DATE_MISSING_MSG = "{application.to-date.missing}";
    private final String INVALID_YEARS_OF_EXPERIENCE_MSG = "{application.years-of-exp.incorrect}";
    private final String INVALID_YEARS_OF_EXPERIENCE_LENGTH_MSG = "{application.years-of-exp.length}";
    private final String YEARS_OF_EXPERIENCE_MISSING_MSG = "{application.years-of-exp.missing}";

    @Test
    void testNullNo() {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setFromDate("");
        applicationForm.setToDate("");
        applicationForm.setYearsOfExperience("");
        List<String> expectedMsg = new ArrayList<String>();
        expectedMsg.add(FROM_DATE_MISSING_MSG);
        expectedMsg.add(TO_DATE_MISSING_MSG);
        expectedMsg.add(YEARS_OF_EXPERIENCE_MISSING_MSG);
        expectedMsg.add(INVALID_TO_DATE_MSG);
        expectedMsg.add(INVALID_FROM_DATE_MSG);
        expectedMsg.add(INVALID_YEARS_OF_EXPERIENCE_MSG);
        assertInput(validator.validate(applicationForm), expectedMsg);
    }

    private ApplicationForm createValidApplicationForm() {
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setYearsOfExperience(VALID_YEARS_OF_EXPERIENCE);
        applicationForm.setFromDate(VALID_FROM_DATE);
        applicationForm.setToDate(VALID_TO_DATE);
        return applicationForm;
    }

    @Test
    public void testInvalidYearsOfExperience(){
        ApplicationForm applicationForm = createValidApplicationForm();
        applicationForm.setYearsOfExperience(INVALID_YEARS_OF_EXPERIENCE);
        List<String> expectedMsg = new ArrayList<>();
        expectedMsg.add(INVALID_YEARS_OF_EXPERIENCE_MSG);
        assertInput(validator.validate(applicationForm), expectedMsg);
    }

    @Test
    public void testInvalidYearsOfExperienceLength(){
        ApplicationForm applicationForm = createValidApplicationForm();
        applicationForm.setYearsOfExperience(INVALID_YEARS_OF_EXPERIENCE_LENGTH);
        List<String> expectedMsg = new ArrayList<>();
        expectedMsg.add(INVALID_YEARS_OF_EXPERIENCE_LENGTH_MSG);
        assertInput(validator.validate(applicationForm), expectedMsg);
    }

    @Test
    public void testInvalidToFromDate() {
        ApplicationForm applicationForm = createValidApplicationForm();
        applicationForm.setFromDate(INVALID_TO_FROM_DATE);
        applicationForm.setToDate(INVALID_TO_FROM_DATE);
        List<String> expectedMsg = new ArrayList<>();
        expectedMsg.add(INVALID_FROM_DATE_MSG);
        expectedMsg.add(INVALID_TO_DATE_MSG);
        assertInput(validator.validate(applicationForm), expectedMsg);
    }

    private void assertInput(Set<ConstraintViolation<ApplicationForm>> result, List<String> correctValidationMsg) {
        assertThat(result.size(), is(correctValidationMsg.size()));
        for(String msg : correctValidationMsg) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(msg))));
        }
    }


}