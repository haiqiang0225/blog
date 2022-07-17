package blog.seckill.cc.config;

import blog.seckill.cc.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
                // 基于 token，不需要 csrf
                .csrf().disable()
                // 基于 token，不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 设置 jwtAuthError 处理认证失败、鉴权失败
//                .exceptionHandling().authenticationEntryPoint().accessDeniedHandler(jwtAuthError).and()
                // 下面开始设置权限
                .authorizeRequests(authorize -> authorize
                                // 请求放开
//                                .antMatchers("/**").permitAll()
                                .antMatchers("/user/login").anonymous()
                                .antMatchers("/article/**").permitAll()
                                .antMatchers("/index/**").permitAll()
                                // 其他地址的访问均需验证权限
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // 添加 JWT 过滤器，JWT 过滤器在用户名密码认证过滤器之前
//                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
//                // 认证用户时用户信息加载配置，注入springAuthUserService
//                .userDetailsService(xxxAuthUserService)
//                .build();
    }

    /**
     * 配置跨源访问(CORS)
     * 前端配置代理服务器了,所以这里其实无所谓
     *
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
