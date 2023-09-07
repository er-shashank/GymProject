package com.telusko.springmvcboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//This is the filter, so whenever we make a resource call (any rest call), it will come to this filter
//to check that a user is already logged in or not and authorized for the request or not
//if it is a valid logged in user then request will be redirected to the controller else not
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    //below method will be called first whenever we do any rest request
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        String usernameFromRequest= getUsernameFromRequest(request);
        String requestURI = request.getRequestURI();


        if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
            String username = jwtProvider.getUsernameFromJWT(jwt);

            //if logged in username and username to which jwt token belongs to does not match
            if(!username.equals(usernameFromRequest)) return;

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);

    }

    private String getUsernameFromRequest(HttpServletRequest request) {
        return request.getHeader("UserName");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken= request.getHeader("Authorization");

        //check whether header has any token
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
