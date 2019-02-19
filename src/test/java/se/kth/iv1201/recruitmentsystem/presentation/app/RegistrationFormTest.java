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

    String validName = "Adrian";
    String validSurname = "Zander";
    String validEmail = "adrian.t.zander@gmail.com";
    String validSsn = "19970215-7695";
    String validUsername = "Acander5";
    String validPassword = "123";

    String invalidName = "Adrian4";
    String invalidSurname = "Zander1";
    String invalidEmail = "adrian.t.zander@@gmail.com";
    String invalidSsn = "19970215";
    String invalidUsername = "A";
    String invalidPassword = "1";

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

    /**private RegistrationForm createValidRegForm() {
        RegistrationForm regForm = new RegistrationForm();
        regForm.setName(validName);
        regForm.setSurname(validSurname);
        regForm.setEmail(validEmail);
        regForm.setSsn(validSsn);
        regForm.setUsername(validUsername);
        regForm.setPassword(validPassword);
        return regForm;
    }

    private void testInvalidInputName(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setName(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    public void testInvalidInputSurname(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setSurname(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    private void testInvalidInputEmail(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setEmail(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    private void testInvalidInputSsn(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setSsn(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    private void testInvalidInputUsername(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setUsername(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    private void testInvalidInputPassword(String input, String... correctValidationMsg) {
        RegistrationForm regForm = createValidRegForm();
        regForm.setPassword(input);
        assertInput(validator.validate(regForm), correctValidationMsg);
    }

    private void assertInput(Set<ConstraintViolation<RegistrationForm>> result, String... correctValidationMsg) {
        assertThat(result.size(), is(correctValidationMsg.length));
        for(String msg : correctValidationMsg) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(msg))));
        }
    }
*/

}
