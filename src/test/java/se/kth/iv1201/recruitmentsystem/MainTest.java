package se.kth.iv1201.recruitmentsystem;


import net.jcip.annotations.NotThreadSafe;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringJUnitWebConfig(initializers = ConfigFileApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.recruitementsystem"})
@NotThreadSafe

@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, MainTest.class})
public class MainTest implements TestExecutionListener {



}
