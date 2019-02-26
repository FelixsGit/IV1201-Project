package se.kth.iv1201.recruitmentsystem.presentation.app;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import se.kth.iv1201.recruitmentsystem.application.ApplicationService;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.Role;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;
import se.kth.iv1201.recruitmentsystem.repository.PersonRepository;
import se.kth.iv1201.recruitmentsystem.repository.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"se.kth.iv1201.recruitmentsystem"})
@SpringBootTest
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        ApplicationControllerTest.class,
        TransactionalTestExecutionListener.class})

@Transactional
/*"a test annotation that is used to indicate that a test-managed transaction
 should be committed after the test method has completed"*/
@Commit
public class ApplicationControllerTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext webappContext;
    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DBUtil dbUtil;

    private MockMvc mockMvc;
    private Person person;

    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private RoleRepository roleRepository;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext).build();
        dbUtil.resetDB();
        startNewTransaction();
    }

    @Test
    void testDefaultView() throws Exception {
        String url = ApplicationController.DEFAULT_PAGE_URL;
        // Test default url, and make sure status is a redirection and that HTTP location header exists
        mockMvc.perform(get(url)).andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(view().name("redirect:" + ApplicationController.REGISTER_PAGE_URL));
    }

    @Test
    void testRegisterView() throws Exception {
        String url = "/" + ApplicationController.REGISTER_PAGE_URL;
        mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(view().name(ApplicationController.REGISTER_PAGE_URL));
    }

    @Test
    void testLoginView() throws Exception {
        String url = "/" + ApplicationController.LOGIN_PAGE_URL;
        mockMvc.perform(get(url)).andExpect(status().isOk())
                .andExpect(view().name(ApplicationController.LOGIN_PAGE_URL));
    }

    @Test
    void testIncorrectView() throws Exception {
        String url = "/" + "someIncorrectView";
        mockMvc.perform(get(url)).andExpect(status().isNotFound());
        // Expect error view
    }

    @Test
    void testRegisterCorrectParams() throws Exception {
        String url = "/" + ApplicationController.REGISTER_PAGE_URL;
        String testName = "testName";
        String testSurname = "testSurname";
        String testEmail = "testEmail@test.com";
        String testPassword = "testPassword";
        String testUsername = "testUsername";
        String testSsn = "19950411-1111";
        MultiValueMap<String, String> params = getMultiValueMap();
        params.add("name", testName);
        params.add("surname", testSurname);
        params.add("email", testEmail);
        params.add("password", testPassword);
        params.add("username", testUsername);
        params.add("ssn", testSsn);
        mockMvc.perform(post(url).params(params)).andExpect(status().isOk())
                .andExpect(view().name(ApplicationController.LOGIN_PAGE_URL));
        startNewTransaction();
        Person person = personRepository.findPersonByUsername(testUsername);
        assertThat(person.getName(), is(testName));
        assertThat(person.getSurname(), is(testSurname));
        assertThat(person.getEmail(), is(testEmail));
        assertThat(person.getPassword(), is(testPassword));
        assertThat(person.getUsername(), is(testUsername));
        assertThat(person.getSsn(), is(testSsn));
    }

    @Test
    void testRegisterIncorrectParams() throws Exception {
        String url = "/" + ApplicationController.REGISTER_PAGE_URL;
        String testUsername = "testUsername";
        MultiValueMap<String, String> params = getMultiValueMap();
        params.add("name", "");
        params.add("surname", "");
        params.add("email", "testEmail@@@test.com");
        params.add("password", "testPassword");
        params.add("username", testUsername);
        params.add("ssn", "19950(¤411-1111");
        mockMvc.perform(post(url).params(params)).andExpect(status().isOk())
                .andExpect(view().name(ApplicationController.REGISTER_PAGE_URL));
        startNewTransaction();
        Person person = personRepository.findPersonByUsername(testUsername);
        assertNull(person);
    }

    private MultiValueMap<String, String> getMultiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }
}
