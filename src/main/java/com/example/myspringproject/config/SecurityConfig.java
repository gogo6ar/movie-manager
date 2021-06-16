package com.example.myspringproject.config;

import com.example.myspringproject.service.TokenService;
import com.example.myspringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final UserService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/register", "/auth/login").permitAll()
                .antMatchers("/movies/**").permitAll()
                .antMatchers("/comment/**").permitAll()
                .antMatchers("/emotion/**").permitAll()
                .antMatchers("/users/**").permitAll()
//                .antMatchers("**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(tokenService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
