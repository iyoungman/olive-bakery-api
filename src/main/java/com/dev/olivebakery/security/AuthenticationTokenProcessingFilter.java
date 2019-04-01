package com.dev.olivebakery.security;

import com.dev.olivebakery.service.SignService;
import com.dev.olivebakery.utill.TokenUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
    @Autowired
    private SignService signService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = this.getAsHttpRequest(servletRequest);
        String authToken = this.extractAuthTokenFromRequest(httpRequest);
        String userName = TokenUtills.getUserNameFromToken(authToken);
        if (userName != null) {
            User user = (User)signService.loadUserByUsername(userName);
            if (TokenUtills.validateToken(authToken, user)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            else{
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private HttpServletRequest getAsHttpRequest(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting an HTTP request");
        }
        return (HttpServletRequest) request;
    }

    private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {
        String authToken = httpRequest.getHeader("X-Auth-Token");
        return authToken;
    }

}
