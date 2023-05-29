package com.telusko.springmvcboot.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

//this class will generate a JWT token after authentication of user
@Service
public class JwtProvider {
    private SecretKey key;
    @PostConstruct
    public void init(){
        key= Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(Authentication authentication){
        User principal= (User) authentication.getPrincipal();

        //for this need to do research
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
    }


    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        return  true;
    }

    public String  getUsernameFromJWT(String jwt) {
        Claims claims= Jwts.parser().setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();

        return  claims.getSubject();
    }
}
