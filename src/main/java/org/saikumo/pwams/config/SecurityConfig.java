package org.saikumo.pwams.config;

import org.saikumo.pwams.constant.RoleName;
import org.saikumo.pwams.security.filter.JwtAuthenticationTokenFilter;
import org.saikumo.pwams.security.handler.RestAuthenticationEntryPoint;
import org.saikumo.pwams.security.handler.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
		return new RestfulAccessDeniedHandler();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				//配置 权限控制规则
				.antMatchers("/api/activity/enroll", "/api/activity/userFind", "/api/activity/getDesc").hasAnyAuthority(RoleName.STUDENT.getRoleName(),RoleName.MENTOR.getRoleName(), RoleName.ACTIVITY_ORGANIZER.getRoleName(), RoleName.MANAGER.getRoleName(), RoleName.STAFF_MEMBER.getRoleName())
				.antMatchers("/api/activity/**").hasAnyAuthority(RoleName.MENTOR.getRoleName(), RoleName.ACTIVITY_ORGANIZER.getRoleName(), RoleName.MANAGER.getRoleName(), RoleName.STAFF_MEMBER.getRoleName())
				.antMatchers("/api/file/**").hasAnyAuthority(RoleName.STUDENT.getRoleName(), RoleName.MENTOR.getRoleName(), RoleName.ACTIVITY_ORGANIZER.getRoleName(), RoleName.MANAGER.getRoleName(), RoleName.STAFF_MEMBER.getRoleName())
				.antMatchers("/api/manager/**").hasAuthority(RoleName.MANAGER.getRoleName())
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/doc.html", "/doc.html/**", "/webjars/**", "/v3/**", "/v2/**", "/swagger-resources",
						"/swagger-resources/**", "/swagger-ui.html", "/swagger-ui.html/**").permitAll()
				.antMatchers("/test/**").hasAuthority(RoleName.STUDENT.getRoleName())
				.anyRequest().authenticated()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler())
				.authenticationEntryPoint(restAuthenticationEntryPoint())
				.and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.cors()
				.and()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

}