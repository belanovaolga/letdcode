package com.example.sweaterolya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Прописываются права доступа к страницам и страница авторизации
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/", "/registration").permitAll()
                                .anyRequest().authenticated()

                ).formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

    /**
     * Для ошибки java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
     * Спринг просит задать энкодер для паролей, использовал старый который ничего не кодирует
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Конфигурация для образения к БД с помощью jdbc запроса в базу данных
     * @param dataSource
     * @return
     */
    @Bean
    protected UserDetailsManager configure(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select username, password, active from usr where username=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
                //.passwordEncoder(NoOpPasswordEncoder.getInstance())
        return jdbcUserDetailsManager;
    }

    /*@Bean
    public CsrfTokenInterceptor csrfTokenInterceptor() {
        return new CsrfTokenInterceptor();
    }*/
}