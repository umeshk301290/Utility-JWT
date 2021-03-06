package com.digital.telco.utility.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse 
                 res, FilterChain chain) throws IOException, ServletException {
    	log.info("Deepak");
        String header = req.getHeader("Authorization");
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith("Bearer")) {
            authToken = header.replace("Bearer","");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occured during getting username from  token", e);
            } 
        } else {
            logger.warn("Couldn't find bearer string, will ignore the header");
        }
        if (username != null && 
                 SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = 
                          userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new 
                     UsernamePasswordAuthenticationToken(userDetails, null, null);
                authentication.setDetails(new 
                     WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Authenticated user " + username + ", setting security context");                          
                SecurityContextHolder.getContext().setAuthentication(
                          authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
