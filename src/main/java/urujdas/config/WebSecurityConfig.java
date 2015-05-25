package urujdas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import static urujdas.web.common.WebCommons.VERSION_PREFIX;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, 1 from users where username = ?")
                .authoritiesByUsernameQuery("select ?, 'ROLE_USER'");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers(VERSION_PREFIX + "/register").anonymous()
                    .antMatchers(VERSION_PREFIX + "/auth").anonymous()
                    .antMatchers(VERSION_PREFIX + "/*").hasRole("USER")
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .formLogin()
                    .loginProcessingUrl(VERSION_PREFIX + "/auth")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                    .successHandler((request, response, auth) -> response.setStatus(HttpServletResponse.SC_OK))
                .and()
                    .headers()
                    .cacheControl().disable();
    }
}
