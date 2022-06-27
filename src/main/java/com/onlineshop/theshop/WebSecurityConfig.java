package com.onlineshop.theshop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService jdbcUserDetailsService;


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jdbcUserDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }
    private static final String LOGIN = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())

                .authenticationProvider(authProvider())
                .csrf()
                    .and()

                .headers()
                    .frameOptions().sameOrigin()
                    .and()

                .authorizeRequests()
                    .antMatchers("/h2/**", "/h2", "/addAllProducts", "/deleteAllProducts", "/getCollection", "/addCollection", "/uploadFile", "/selectFile").permitAll()
                    .antMatchers("/templates/**", "/success", "/error/", "/error/**", "/**/*.ico", "/files/**", "/signup", "/signupAddress", "/user/add.post", "/dist/**").permitAll()
                    .antMatchers("/product", "/product/**", "/managementSuccess", "/management", "/user/edit.post", "/user/delete.post").authenticated()
                    .antMatchers("/user", "/user/all", "/user/delete","/user/add").hasAuthority("USERMANAGEMENT_PRIVILEGE")
                    .antMatchers("/authorities/**").hasAuthority("AUTHORITYMANAGEMENT_PRIVILEGE")
                    .antMatchers("/management/store", "/management/store/**", "/management/category", "/management/category/**", "/management/product" , "/management/product/**", "/management/order" , "/management/order/**").hasAuthority("STOREMANAGEMENT_PRIVILEGE")
                    .antMatchers("/test").permitAll()
                    .anyRequest().authenticated()
                    /*.anyRequest().permitAll()*/
                    .and()

                .formLogin()
                    .loginPage(LOGIN)
                    .defaultSuccessUrl("/", false)
                    .failureUrl(LOGIN)
                    .permitAll()
                    .and()

                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl(LOGIN)
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll();

    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


