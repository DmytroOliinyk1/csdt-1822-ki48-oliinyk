package com.lpnu.virtual.library.config;

import com.lpnu.virtual.library.core.user.model.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority(Roles.ADMIN.name())
                .antMatchers("/asset/search/all").permitAll()
                .antMatchers("/asset/search/apply/filters").permitAll()
                .antMatchers("/asset/search/filters").permitAll()
                .antMatchers("/asset/search/authors").permitAll()
                .antMatchers("/asset/preview**").permitAll()
                .antMatchers("/asset/download**").permitAll()
                .antMatchers("/user/create**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/do-logout").permitAll()
                .antMatchers("/welcome").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}