package com.msp.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public DataSource dataSource;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        dataSource.setUrl("jdbc:h2:~/test");
//        return dataSource;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password(encoder().encode("user"))
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password(encoder().encode("admin"))
//                .roles("ADMIN");

        auth.jdbcAuthentication()
                .dataSource(dataSource); //KO define datasource nên dùng H2 db làm default

//                .withDefaultSchema() // sử dụng schema default, nếu tạo schema thật thì comment dòng này

                /*Dùng theo schema default của spring*/
//        .withUser(
//                User.withUsername("user").password(encoder().encode("user")).roles("USER")
//        ).withUser(
//                User.withUsername("admin").password(encoder().encode("admin")).roles("ADMIN")
//        );

                /*Dùng theo custom schema*/
//                .usersByUsernameQuery("")
//                .authoritiesByUsernameQuery("");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/**").permitAll()
//                .antMatchers("/**").hasRole("ADMIN")
                .and().formLogin();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
