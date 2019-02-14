package se.kth.iv1201.recruitmentsystem;


import net.jcip.annotations.NotThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import se.kth.iv1201.recruitmentsystem.repository.DBUtil;

import java.io.IOException;
import java.sql.SQLException;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.recruitmentsystem"})
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, MainTest.class})
public class MainTest implements TestExecutionListener {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DBUtil dbUtil;

    @Override
    public void beforeTestClass(TestContext testContext) throws SQLException, IOException, ClassNotFoundException {
        dbUtil = testContext.getApplicationContext().getBean(DBUtil.class);
        dbUtil.emptyDb();
    }

}
