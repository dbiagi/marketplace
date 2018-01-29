package org.dbiagi.marketplace.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private ApiUserDetailService apiUserDetailService;

    private ApiAuthenticationEntryPoint apiAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(ApiUserDetailService apiUserDetailService, ApiAuthenticationEntryPoint apiAuthenticationEntryPoint) {
        this.apiUserDetailService = apiUserDetailService;
        this.apiAuthenticationEntryPoint = apiAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .and().httpBasic().realmName(ApiAuthenticationEntryPoint.REALM_NAME)
                .authenticationEntryPoint(apiAuthenticationEntryPoint);

        // Fix h2 blank page
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(apiUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
