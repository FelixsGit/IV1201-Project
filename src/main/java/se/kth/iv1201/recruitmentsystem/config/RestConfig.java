package se.kth.iv1201.recruitmentsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import se.kth.iv1201.recruitmentsystem.presentation.app.rest.RestAuthenticationEntryPoint;
import se.kth.iv1201.recruitmentsystem.presentation.app.rest.RestSuccessHandler;

@Configuration
@EnableWebSecurity
@Order(1)
public class RestConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestSuccessHandler restSuccessHandler;

    private SimpleUrlAuthenticationFailureHandler restFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    /**
     * This private class is responsible for supplying spring with a password encoder.
     * The methods in this class in not used, this is a custom password-encoder made just override
     * springs default settings.
     */
    private class PasswordEncoderTest implements PasswordEncoder {
        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return charSequence.toString().equals(s);
        }
    }

    /**
     * Method to configure security over http
     *
     * @param http HttpSecurity object provided by spring
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // *** Rest config ***
        http
                .antMatcher("/api/**")
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/applications").hasRole("recruit")
                .antMatchers("/api/login").permitAll()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(restSuccessHandler)
                .failureHandler(restFailureHandler)
                .and()
                .logout();
    }
}
