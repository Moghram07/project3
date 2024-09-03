package com.example.project3.ConfigSecurity;


import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/login", "/api/v1/user/register","/api/v1/employee/register").permitAll()
                .requestMatchers("/api/v1/user/delete/{id}","/api/v1/customer/delete/{id}","/api/v1/employee/delete/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/employee/get", "/api/v1/employee/update/{id}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/get", "/api/v1/customer/register", "/api/v1/customer/update/{id}", "/api/v1/account/block/").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/v1/account/register","/api/v1/account/deposit/{customerId}/{id}", "/api/v1/account/withdraw/{customerId}/{id}",
                        "/api/v1/account/{fromCustomerId}/{fromAccountId}/transfer/{toAccountid}").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
