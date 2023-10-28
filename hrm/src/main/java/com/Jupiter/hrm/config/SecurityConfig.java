package com.Jupiter.hrm.config;
import com.Jupiter.hrm.utility.JwtAuthenticationFilter;
import com.Jupiter.hrm.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
//                .antMatchers("/employee/**").permitAll()
//                .antMatchers("/employees/**").hasAnyAuthority("ADMIN", "LVL1USER", "LVL2USER", "LVL3USER")
//                .antMatchers("/users/**").hasAnyAuthority("ADMIN", "LVL1USER")
//                .antMatchers("/leaveapplication/**").hasAnyAuthority("ADMIN", "LVL1USER", "LVL2USER")
                .antMatchers("/employees/**").permitAll()




                .and()
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .cors().and().antMatcher("/**").authorizeRequests().anyRequest().permitAll()
                ;



    }

}