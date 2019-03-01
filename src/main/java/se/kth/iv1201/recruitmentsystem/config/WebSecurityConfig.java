package se.kth.iv1201.recruitmentsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class is responsible for granting different authorities to different url's.
 * By restricting access to certain pages depending on the users authority, aka the users role.
 */

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
        http.authorizeRequests().antMatchers("/login", "/register", "/updateAccount").permitAll();
        http.authorizeRequests().antMatchers("/apply", "/searchApplication", "/handleApplication").fullyAuthenticated();

        http.authorizeRequests().antMatchers("/apply").access("hasRole('applicant')");
        http.authorizeRequests().antMatchers("/searchApplication", "/handleApplication").access("hasRole('recruit')");
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

