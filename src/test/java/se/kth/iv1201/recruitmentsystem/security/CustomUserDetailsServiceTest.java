package se.kth.iv1201.recruitmentsystem.security;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import se.kth.iv1201.recruitmentsystem.domain.Person;
import se.kth.iv1201.recruitmentsystem.domain.Role;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;
import se.kth.iv1201.recruitmentsystem.repository.PersonRepository;
import se.kth.iv1201.recruitmentsystem.repository.RoleRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        CustomUserDetailsServiceTest.class,
        TransactionalTestExecutionListener.class})

@Transactional
@Commit
public class CustomUserDetailsServiceTest implements TestExecutionListener {

    @Autowired
    private WebApplicationContext webappContext;
    @Autowired
    private DBUtil dbUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;
    private Person person;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext)
                //.addFilter(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
        dbUtil.resetDB();
        Role role = roleRepository.findRoleByName(Role.APPLICANT);
        roleRepository.save(role);
        person = new Person("testName", "testSurname", "19950411-1111",
                "test@te.se", "123", role, "testUsername");
        personRepository.save(person);
    }

    @Test
    void testAccessApplyWithLoggedInUser() throws Exception {
        /*
        String url = "/login";
        MultiValueMap<String, String> params = getMultiValueMap();
        params.add("username", person.getUsername());
        params.add("password", person.getPassword());
        mockMvc.perform(post(url).params(params))
                .andExpect(status().isOk())
                .andExpect(view().name("/loginOk"));
                */

        /*
        RequestBuilder requestBuilder = post("/login")
                .param("username", person.getUsername())
                .param("password", person.getPassword());
        mockMvc.perform(requestBuilder)
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(cookie().exists("JSESSIONID"))
            .andExpect(view().name("/loginOk"));
            */
        mockMvc.perform(get("/apply")
                .with(user(person.getUsername())
                    .password(person.getPassword())
                    .roles(person.getRole().getName())))
                .andExpect(status().isOk())
                .andExpect(view().name("apply"));
    }

    @Test
    void testAccessApplyWithoutLoggedInUser() throws Exception {
        mockMvc.perform(get("/apply"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testAccessHandleApplicationWithLoggedInUser() throws Exception {
        mockMvc.perform(get("/handleApplication")
                .with(user(person.getUsername())
                        .password(person.getPassword())
                        .roles(Role.RECRUITER)))
                .andExpect(status().isOk())
                .andExpect(view().name("handleApplication"));
    }

    @Test
    void testAccessHandleApplicationWithoutLoggedInUser() throws Exception {
        mockMvc.perform(get("/handleApplication"))
                .andExpect(status().is3xxRedirection());
    }


    private MultiValueMap<String, String> getMultiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    private void startNewTransaction() {
        TestTransaction.end();
        TestTransaction.start();
    }


}
