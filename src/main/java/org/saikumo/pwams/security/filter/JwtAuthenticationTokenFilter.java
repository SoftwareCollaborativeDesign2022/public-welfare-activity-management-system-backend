package org.saikumo.pwams.security.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.saikumo.pwams.cache.Cache;
import org.saikumo.pwams.constant.CacheName;
import org.saikumo.pwams.entity.User;
import org.saikumo.pwams.properties.JwtProperties;
import org.saikumo.pwams.security.provider.JwtProvider;
import org.saikumo.pwams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录过滤器
 * 拿到请求头中的token解析出其中的用户信息，
 * 将用户信息传给下一条过滤器，
 * 拿到上下文对象赋值到上下文。
 *
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private Cache caffeineCache;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        log.info("JWT过滤器通过校验请求头token进行自动登录...");

        // 拿到Authorization请求头内的信息
        String authToken = jwtProvider.getToken(request);

        // 判断一下内容是否为空
        if (StrUtil.isNotEmpty(authToken) && authToken.startsWith(jwtProperties.getTokenPrefix())) {
            // 去掉token前缀(Bearer )，拿到真实token
            authToken = authToken.substring(jwtProperties.getTokenPrefix().length());

            // 拿到token里面的登录账号
            String loginAccount = jwtProvider.getSubjectFromToken(authToken);

            if (StrUtil.isNotEmpty(loginAccount) && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 查询用户
                User userDetails = caffeineCache.get(CacheName.USER, loginAccount, User.class);
                // 缓存击穿 数据库查
                if(userDetails == null){
                    userDetails = userRepository.findUserByUsername(loginAccount);
                }

                // 拿到用户信息后验证用户信息与token
                if (userDetails != null && jwtProvider.validateToken(authToken, userDetails)) {

                    // 组装authentication对象，构造参数是Principal Credentials 与 Authorities
                    // 后面的拦截器里面会用到 grantedAuthorities 方法
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    // 将authentication信息放入到上下文对象中
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("JWT过滤器通过校验请求头token自动登录成功, user : {}", userDetails.getUsername());
                }
            }
        }

        chain.doFilter(request, response);
    }
}
