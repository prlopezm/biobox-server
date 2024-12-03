package mx.com.tecnetia.orthogonal.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import mx.com.tecnetia.orthogonal.security.jwt.JwtEntryPoint;
import mx.com.tecnetia.orthogonal.security.jwt.JwtTokenFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ComponentScan(basePackages = "mx.com.tecnetia")
public class WebSecurityConfig {
    private static final String[] AUTH_WHITE_LIST = {
            "/auth/*", "/v2/*", "/v3/*",
            "/swagger-resources/*",
            "/swagger-ui.html*",
            "/webjars/*",
            "/swagger-ui/*",
            "/swagger-ui/index.html*",
            "/error",
            "/verificacion/email/link/valida"
    };

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    JwtEntryPoint jwtEntryPoint;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    /*    @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

/*    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }*/

    /*    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
        }*/
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

/*    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web->web.ignoring().antMatchers("/js/**", "/images/**");
    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeHttpRequests(
                        (authz)->authz
                                .requestMatchers(new RegexRequestMatcher(".*free.*",null)).permitAll()
                                .requestMatchers(new RegexRequestMatcher(".*swagger.*",null)).permitAll()
                                .requestMatchers(AUTH_WHITE_LIST).permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authenticationProvider(authenticationProvider());

        http.securityContext(securityContext->securityContext
                .requireExplicitSave(true)
        );

        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
