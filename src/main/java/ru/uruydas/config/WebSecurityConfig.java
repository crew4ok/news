package ru.uruydas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import ru.uruydas.social.security.SocialAuthenticationFilter;
import ru.uruydas.social.security.SocialNetworkAuthenticationProvider;
import ru.uruydas.social.service.SocialNetworkUserService;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import static ru.uruydas.web.common.WebCommons.VERSION_PREFIX;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SocialNetworkUserService socialNetworkUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, 1 from users where username = ?")
                .authoritiesByUsernameQuery("select ?, 'ROLE_USER'")
            .and()
                .authenticationProvider(new SocialNetworkAuthenticationProvider(socialNetworkUserService));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(socialAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers(VERSION_PREFIX + "/users/register").anonymous()
                    .antMatchers(VERSION_PREFIX + "/auth/**").anonymous()
                    .antMatchers(VERSION_PREFIX + "/healthcheck").anonymous()
                    .antMatchers(VERSION_PREFIX + "/images/**").permitAll()
                    .antMatchers(VERSION_PREFIX + "/**").hasRole("USER")
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .formLogin()
                    .loginProcessingUrl(VERSION_PREFIX + "/auth/basic")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureHandler(authenticationFailureHandler())
                    .successHandler(authenticationSuccessHandler())
                .and()
                    .logout()
                    .logoutUrl(VERSION_PREFIX + "/logout")
                    .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                    .headers()
                    .cacheControl().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SocialAuthenticationFilter socialAuthenticationFilter() throws Exception {
        SocialAuthenticationFilter filter = new SocialAuthenticationFilter(VERSION_PREFIX + "/auth/social", objectMapper);

        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return filter;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, auth) -> response.setStatus(HttpServletResponse.SC_OK);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (req, resp, auth) -> resp.setStatus(HttpServletResponse.SC_OK);
    }
}
