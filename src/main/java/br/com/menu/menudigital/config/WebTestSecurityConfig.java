package br.com.menu.menudigital.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebTestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public DataSource datasource;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(this.datasource)
			.usersByUsernameQuery("select username, password, 1 from user where username = ?")
			.authoritiesByUsernameQuery("select ?, 'ROLE_USER';");
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/user").permitAll()
			.antMatchers("/authenticate").permitAll()
			.antMatchers(HttpMethod.GET).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(jwtUsernamePasswordAuthenticationFilter())
			.addFilter(jwtBasicAuthenticationFilter())
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.logout().permitAll();
		}
		
		@Bean
		public JWTUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter() throws Exception {
			JWTUsernamePasswordAuthenticationFilter auth = new JWTUsernamePasswordAuthenticationFilter();
			auth.setAuthenticationManager(this.authenticationManager());
			return auth;
		}
		
		@Bean
		public JWTBasicAuthenticationFilter jwtBasicAuthenticationFilter() throws Exception {
			return new JWTBasicAuthenticationFilter(this.authenticationManager());
		}
		
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			return super.authenticationManager();
		}
		
}