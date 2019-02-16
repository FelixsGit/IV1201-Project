package se.kth.iv1201.recruitmentsystem.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import se.kth.iv1201.recruitmentsystem.security.CustomUserDetailsService;

/**
 * This class is responsible for granting different authorities to different url's.
 * By restricting access to certain pages depending on the users authority, aka the users role.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * This method creates a new passwordEncoderTest object
     *
     * @return PasswordEncoderTest object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderTest();
    }

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

        http.csrf().disable();

        // These pages does not require login
        http.authorizeRequests().antMatchers("/login", "/register").permitAll();

        // /apply page requires login as applicant(1).
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/apply").access("hasRole('applicant')");

        // handleApplication page requires login as recruiter(2)
        //if no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/handleApplication").access("hasRole('recruit')");

        //loginOk redirect page, this page is reached right after login and will redirect the user based on his role
        //If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/loginOk").access("hasAnyRole('recruit', 'applicant')");

        // Config for Login/Logout
        http.authorizeRequests()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/loginOk")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .permitAll();
    }
}

