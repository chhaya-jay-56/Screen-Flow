//package com.example.Spring_2.Security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class Spring_Sec {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/log_in" , "/sign_up" , "/home" , "/save_3" , "/index" , "/save_user_data_2" , "/save_user_data" ,
//                        "/verify" , "/verify_otp" , "/about" , "/genre" , "/contact" , "/flow_card" , "/search" , "/subs" , "/log_out" , "/edit_2/**" , "edit/**").permitAll() // Allow access to the home page and other public resources
//                        .requestMatchers("/css/**", "/fonts/**", "/js/**", "/images/**", "/webjars/**").permitAll()
//                        .requestMatchers("/admin_css/css/**", "/admin_f/**", "/admin_js/**", "/admin_images/**").permitAll()
//                        .requestMatchers("/error404" , "/error405" , "/error406" , "/error407").permitAll()
//                        .requestMatchers("/admin" , "/forms" , "/index_2").authenticated() // Require authentication for all URLs under /admin/
//                        .anyRequest().authenticated() // Require authentication for all other requests
//                )
//                .formLogin(formLogin -> formLogin
//                        .permitAll() // Allow access to the login page
//                        .defaultSuccessUrl("/index_2", true) // Redirect to /admin/forms after successful login
//                )
//                .logout(logout -> logout
//                        .logoutUrl("log_out")
//                        .logoutSuccessUrl("/home") // Redirect to home page after successful logout
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//}
