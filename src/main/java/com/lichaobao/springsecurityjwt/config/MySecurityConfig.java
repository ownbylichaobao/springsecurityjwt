package com.lichaobao.springsecurityjwt.config;

import com.lichaobao.springsecurityjwt.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichaobao
 * @date 2018/12/22
 * @QQ 1527563274
 */
@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAccessDeineHandler myAccessDeineHandler;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    /**
     * 模拟数据库用户
     */
    private static Map<String,String> users;
    /**
     * 模拟权限
     */
    private static Map<String,List<String>> permissions;
    static {
        users = new HashMap<>();
        permissions = new HashMap<>();
        users.put("a","a");
        String[] aper = new String[]{"/a/**","/test/all"};
        permissions.put("a",Arrays.asList(aper));
        users.put("b","b");
        String[] bper = new String[]{"/b/**","test/all"};
        permissions.put("b",Arrays.asList(bper));
        users.put("admin","password");
        String[] adminPer = new String[]{"/**"};
        permissions.put("admin",Arrays.asList(adminPer));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf()
                    .disable()//禁用csrf 因为使用jwt不需要
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//禁用session
                    .and()
                    .authorizeRequests()
                    .accessDecisionManager(accessDecisionManager())//加载自己的accessDecisionManager 用到了RoleBasedVotor
                    .antMatchers(HttpMethod.GET,
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/swagger-resources/**",
                            "/v2/api-docs/**")
                    .permitAll()//允许访问所有界面资源
                    .antMatchers("/login","/register")
                    .permitAll()//允许访问登陆注册接口  然后  "/login"与"/register"的权限为"ROLE_ANONYMOUS"
                    .antMatchers(HttpMethod.OPTIONS)
                    .permitAll()//跨域请求 会有一个OPTIONS 请求  全部允许
                    .anyRequest()//其他任何都需要验证
                    .authenticated();
        /**
         * 禁用缓存
         */
        http.headers().cacheControl();
        /**
         * 配置自定义的Filter
         */
            http.addFilterBefore(jwtAuthenticationTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        /**
         * 配置自定义的无权限以及用户名密码错误返回结果
         */
        http.exceptionHandling()
                    .accessDeniedHandler(myAccessDeineHandler)
                    .authenticationEntryPoint(myAuthenticationEntryPoint);
    }

    /**
     * 配置 userDetailsService 以及passwordEncoder;
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 在次代码中完成用户基本信息的查询比如用户名 密码 权限等封装后 返回
     * 此方法的入口 为 userDetailsService.loadUserByUsername(String username)
     * @return UserDetail
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return username ->{
            if(users.containsKey(username)){
                return new MyUserDetails(username,users.get(username),permissions.get(username));
            }
            throw new UsernameNotFoundException("用户名错误");
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new MyPasswordEncoder();
    }
    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 具体使用RoleBasedVotor方法
     * @return
     */
    @Bean
    public AccessDecisionManager accessDecisionManager(){
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                new RoleBasedVotor(),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }
}
