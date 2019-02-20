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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@SpringBootTest
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, LoginFormTest.class})
public class LoginFormTest implements TestExecutionListener {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private Validator validator;

    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DBUtil.class);
        dbUtil.resetDB();
    }

    private final String INVALID_USERNAME = "A";
    private final String INVALID_PASSWORD = "1";

    private final String VALID_USERNAME = "Acander5";
    private final String VALID_PASSWORD = "123";

    private final String INVALID_USERNAME_MSG = "{login.username.missing}";
    private final String INVALID_PASSWORD_MSG = "{login.password.missing}";

    private final String INVALID_LENGTH_MSG = "{general-input.msg-length}";

    @Test
    void testNullNo() {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername("");
        loginForm.setPassword("");
        List<String> expectedMsg = new ArrayList<String>();
        expectedMsg.add(INVALID_USERNAME_MSG);
        expectedMsg.add(INVALID_PASSWORD_MSG);
        expectedMsg.add(INVALID_LENGTH_MSG);
        expectedMsg.add(INVALID_LENGTH_MSG);

        assertInput(validator.validate(loginForm), expectedMsg);
    }

    private LoginForm createValidLoginForm() {
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(VALID_USERNAME);
        loginForm.setPassword(VALID_PASSWORD);
        return loginForm;
    }

    @Test
    public void testInvalidInputUsername() {
        LoginForm loginForm = createValidLoginForm();
        loginForm.setUsername(INVALID_USERNAME);
        List<String> expectedMsg = new ArrayList<>();
        expectedMsg.add(INVALID_LENGTH_MSG);
        assertInput(validator.validate(loginForm), expectedMsg);
    }

    @Test
    public void testInvalidInputPassword() {
        LoginForm loginForm = createValidLoginForm();
        loginForm.setPassword(INVALID_PASSWORD);
        List<String> expectedMsg = new ArrayList<>();
        expectedMsg.add(INVALID_LENGTH_MSG);
        assertInput(validator.validate(loginForm), expectedMsg);
    }

    private void assertInput(Set<ConstraintViolation<LoginForm>> result, List<String> correctValidationMsg) {
        assertThat(result.size(), is(correctValidationMsg.size()));
        for(String msg : correctValidationMsg) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(msg))));
        }
    }


}
