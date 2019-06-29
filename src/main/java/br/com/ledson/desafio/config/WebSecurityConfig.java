package br.com.ledson.desafio.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.ledson.desafio.utils.AuthenticationEntryPoint;
import br.com.ledson.desafio.utils.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationEntryPoint aep;
	
	@Autowired
	private CustomAuthenticationProvider cap;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.cors().and()
        		.authorizeRequests()
        		.antMatchers("/index.jsf").permitAll()
        		.antMatchers("/javax.faces.resource/**").permitAll()
        		.anyRequest()
        		.authenticated()
        		.and()
        		.httpBasic()
        		.authenticationEntryPoint(aep)
        		.and()
            .logout()
                .permitAll()
        		.and()
        	.sessionManagement()
        		.maximumSessions(1);
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(cap);
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.unmodifiableList(List.of("*")));
        configuration.setAllowedMethods(Collections.unmodifiableList(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.unmodifiableList(List.of("Authorization", "Cache-Control", "Content-Type", "LoginJFRN")));
        
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}