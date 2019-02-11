package se.kth.iv1201.recruitmentsystem.presentation;

import net.jcip.annotations.NotThreadSafe;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.recruitementsystem"})
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
        ApplicationControllerTest.class,
        TransactionalTestExecutionListener.class})

public class ApplicationControllerTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext webappContext;
    @Autowired
    private DBUtil dbUtil;

    private MockMvc mockMvc;

    @Override
    public void beforeTestClass(TestContext testContext) {

    }

    @Override
    public void afterTestClass(TestContext testContext) {

    }

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webappContext).build();
    }

    @Test
    void testTest() {
        //sendGetRequest(mockMvc, "").andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"));
        MatcherAssert.assertThat(3, is(3));
    }
}
