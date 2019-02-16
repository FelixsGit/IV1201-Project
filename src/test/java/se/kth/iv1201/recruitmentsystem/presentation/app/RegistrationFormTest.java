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


}
