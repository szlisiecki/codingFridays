package com.orange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	private RestAuthenticationSuccessHandler authenticationSuccessHandler;
	private RestAuthenticationFailureHandler authenticationFailureHandler;
	private String secret;

	public SpringSecurityConfig(RestAuthenticationSuccessHandler authenticationSuccessHandler,
            RestAuthenticationFailureHandler authenticationFailureHandler,
            @Value("${jwt.secret}") String secret) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.secret = secret;
}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("Szymon").password("{noop}Szymon").roles("MODIFIER","READER", "USER").and()
									 .withUser("Artur").password("{noop}Artur").roles("READER", "USER");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
            	.antMatchers(HttpMethod.GET, "/contacts/**"). hasRole("READER")
				.antMatchers("/contacts/**").hasRole("MODIFIER")
				.antMatchers("/users/{userId}/contacts").access("@userSecurity.hasUserId(authentication,#userId,'USER')")
//				.antMatchers("/swagger-ui.html").permitAll()
//				.antMatchers("/webjars/**").permitAll()
//				.antMatchers("/swagger-resources/**").permitAll()
				 .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagger-ui.html").permitAll()
//				/csrf
//				/v2/api-docs
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), super.userDetailsService(), secret))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic().and()
//			.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/contacts/**"). hasRole("READER")
//				.antMatchers("/contacts/**").hasRole("MODIFIER").and()
//					.csrf().disable();
//	}
	
	@Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }
}
