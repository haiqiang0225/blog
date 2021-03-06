package blog.seckill.cc.config;

import blog.seckill.cc.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * description: WebSecurityConfig <br>
 * date: 2022/7/16 19:05 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // ?????? token???????????? csrf
                .csrf().disable()
                // ?????? token???????????? session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // ?????? jwtAuthError ?????????????????????????????????
//                .exceptionHandling().authenticationEntryPoint().accessDeniedHandler(jwtAuthError).and()
                // ????????????????????????
                .authorizeRequests(authorize -> authorize
                                // ????????????
//                                .antMatchers("/**").permitAll()
                                .antMatchers("/user/login").anonymous()
                                .antMatchers("/article/**").permitAll()
                                .antMatchers("/index/**").permitAll()
                                .antMatchers("/category/**").permitAll()
                                .antMatchers("/tag/**").permitAll()
                                .antMatchers("/hello/**").permitAll()
                                .antMatchers("/comment/**").authenticated()
                                // ???????????????????????????????????????
                                .anyRequest().authenticated()
                )
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // ?????? JWT ????????????JWT ????????????????????????????????????????????????
//                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
//                // ????????????????????????????????????????????????springAuthUserService
//                .userDetailsService(xxxAuthUserService)
//                .build();
    }

    /**
     * ??????????????????(CORS)
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "https://seckill.cc",
                "https://blog.seckill.cc"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
