package com.eazybytes.eazyschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // Permit All Requests inside the Web Application
        /*httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();*/

        // Deny All Requests inside the Web Application
        /*httpSecurity.authorizeHttpRequests(requests -> requests.anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();*/

        //custom security for individual API paths
        httpSecurity.csrf((csrf) -> {
                    csrf.ignoringRequestMatchers("/saveMsg");
                    csrf.ignoringRequestMatchers("/public/**");
                    csrf.ignoringRequestMatchers("/api/**");
                })
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers("/home","/","").permitAll()
                                .requestMatchers("/dashboard").authenticated()
                                .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                                .requestMatchers("/student/**").hasRole("STUDENT")
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/displayProfile").authenticated()
                                .requestMatchers("/updateProfile").authenticated()
                                .requestMatchers("/contact").permitAll()
                                .requestMatchers("/holidays/**").permitAll()
                                .requestMatchers("/saveMsg").permitAll()
                                .requestMatchers("/courses").permitAll()
                                .requestMatchers("/about").permitAll()
                                .requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/logout").permitAll()



                        )
                .formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
                .logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }
   /* @Bean
    public InMemoryUserDetailsManager  userDetailsManager(){
        UserDetails admin= User.withDefaultPasswordEncoder()
                .username("tejaswini")
                .password("12345")
                .roles("ADMIN")
                .build();
        UserDetails admin1= User.withDefaultPasswordEncoder()
                .username("akshaya")
                .password("1234")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin1,admin);
    }*/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
