package com.dev.olivebakery.security;

import com.dev.olivebakery.service.SignService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SignService signService;
    private final PasswordEncoder passwordEncoder;
    private final HttpAuthenticationEntryPoint httpAuthenticationEntryPoint;
    private final AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final LogoutSuccessHandlerCustom logoutSuccessHandlerCustom;

    public SecurityConfig(SignService signService, PasswordEncoder passwordEncoder, LogoutSuccessHandlerCustom logoutSuccessHandlerCustom, HttpAuthenticationEntryPoint httpAuthenticationEntryPoint, AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter, AccessDeniedHandler accessDeniedHandler) {
        this.signService = signService;
        this.passwordEncoder = passwordEncoder;
        this.logoutSuccessHandlerCustom = logoutSuccessHandlerCustom;
        this.httpAuthenticationEntryPoint = httpAuthenticationEntryPoint;
        this.authenticationTokenProcessingFilter = authenticationTokenProcessingFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }


/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                    .mvcMatchers( "/olive/sign/signup").permitAll()
                    .mvcMatchers(HttpMethod.PUT,"/olive/sign").hasRole(MemberRole.CLIENT.name())
                    .mvcMatchers(HttpMethod.DELETE,"/olive/sign").hasRole(MemberRole.CLIENT.name())
                    .mvcMatchers("/olive/admin/**").hasRole(MemberRole.ADMIN.name())
                    .mvcMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    .mvcMatchers("/olive/sign/test").hasRole(MemberRole.CLIENT.name())
                .and()
                    .exceptionHandling().authenticationEntryPoint(httpAuthenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
                .and()
                    .addFilterBefore(authenticationTokenProcessingFilter, BasicAuthenticationFilter.class)
                    .logout().logoutUrl("/olive/logout").logoutSuccessHandler(logoutSuccessHandlerCustom)
        ;
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(signService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers(HttpMethod.POST, "/olive/sign")
                .mvcMatchers("/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
