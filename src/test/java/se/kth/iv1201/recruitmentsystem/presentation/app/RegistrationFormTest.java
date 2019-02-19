package se.kth.iv1201.recruitmentsystem.presentation.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;


@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, RegistrationFormTest.class})
class RegistrationFormTest implements TestExecutionListener {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private Validator validator;

    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DBUtil.class);
        dbUtil.resetDB();
    }

    private final String VALID_NAME = "Adrian";
    private final String VALID_SURNAME = "Zander";
    private final String VALID_EMAIL = "adrian.t.zander@gmail.com";
    private final String VALID_SSN = "19970215-7695";
    private final String VALID_USERNAME = "Acander5";
    private final String VALID_PASSWORD = "123";

    private final String INVALID_NAME = "Adrian4";
    private final String INVALID_SURNAME = "Zander1";
    private final String INVALID_EMAIL = "adrian.t.zander@@gmail.com";
    private final String INVALID_SSN = "19970215";
    private final String INVALID_USERNAME = "A";
    private final String INVALID_PASSWORD = "1";

    private final String INVALID_CHAR_MSG = "{general-input.invalid-char}";
    private final String INVALID_LENGTH_MSG = "{general-input.msg-length}";
    private final String INVALID_EMAIL_MSG = "{reg.email.incorrect}";
    private final String INVALID_SSN_MSG = "{reg.ssn.incorrect}";

    @Test
    void testNullNo() {
        RegistrationForm regForm = new RegistrationForm();
        regForm.setEmail("");
        regForm.setName("");
        regForm.setPassword("");
        regForm.setSsn("");
        regForm.setSurname("");
        regForm.setUsername("");
        List<String> expectedMsg = new ArrayList<String>();
        expectedMsg.add("{reg.email.missing}");
        expectedMsg.add("{reg.name.missing}");
        expectedMsg.add("{reg.password.missing}");
        expectedMsg.add("{reg.ssn.missing}");
        expectedMsg.add("{reg.surname.missing}");
        expectedMsg.add("{reg.username.missing}");
        expectedMsg.add("{general-input.invalid-char}");
        expectedMsg.add("{general-input.invalid-char}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{general-input.msg-length}");
        expectedMsg.add("{reg.ssn.incorrect}");
        Set<ConstraintViolation<RegistrationForm>> result = validator.validate(regForm);
        assertThat(result.size(), is(expectedMsg.size()));
        for(String msg : expectedMsg) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(msg))));
        }
    }

    private RegistrationForm createValidRegForm() {
        RegistrationForm regForm = new RegistrationForm();
        regForm.setName(VALID_NAME);
        regForm.setSurname(VALID_SURNAME);
        regForm.setEmail(VALID_EMAIL);
        regForm.setSsn(VALID_SSN);
        regForm.setUsername(VALID_USERNAME);
        regForm.setPassword(VALID_PASSWORD);
        return regForm;
    }

    @Test
    public void testInvalidInputName() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setName(INVALID_NAME);
        assertInput(validator.validate(regForm), INVALID_CHAR_MSG);
    }

    @Test
    public void testInvalidInputSurname() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setSurname(INVALID_SURNAME);
        assertInput(validator.validate(regForm), INVALID_CHAR_MSG);
    }

    @Test
    public void testInvalidInputEmail() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setEmail(INVALID_EMAIL);
        assertInput(validator.validate(regForm), INVALID_EMAIL_MSG);
    }

    @Test
    public void testInvalidInputSsn() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setSsn(INVALID_SSN);
        assertInput(validator.validate(regForm), INVALID_SSN_MSG);
    }

    @Test
    public void testInvalidInputUsername() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setUsername(INVALID_USERNAME);
        assertInput(validator.validate(regForm), INVALID_LENGTH_MSG);
    }

    @Test
    public void testInvalidInputPassword() {
        RegistrationForm regForm = createValidRegForm();
        regForm.setPassword(INVALID_PASSWORD);
        assertInput(validator.validate(regForm), INVALID_LENGTH_MSG);
    }

    private void assertInput(Set<ConstraintViolation<RegistrationForm>> result, String... correctValidationMsg) {
        assertThat(result.size(), is(correctValidationMsg.length));
        for(String msg : correctValidationMsg) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(msg))));
        }
    }

}
