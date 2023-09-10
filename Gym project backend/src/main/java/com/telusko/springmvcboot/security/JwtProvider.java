package com.telusko.springmvcboot.security;


import com.telusko.springmvcboot.security.exception.GymException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

//this class will generate a JWT token after authentication of user
@Service
public class JwtProvider {
    private KeyStore keyStore;

    //this will load the private+public key from springgym.jks file present in resources
    @PostConstruct
    public void init(){
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springgym.jks");
            keyStore.load(resourceAsStream, "welcome*12".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new GymException("Exception occured while loading keystore");
        }
    }

    public String generateToken(Authentication authentication){
        User principal= (User) authentication.getPrincipal();

        //for this need to do research
        return Jwts.builder()
                .setSubject(principal.getUsername())
                 .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            PrivateKey key=(PrivateKey) keyStore.getKey("springgym", "welcome*12".toCharArray());
            return key;
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new GymException("Exception occured while retrieving private key from keystore");
        }
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springgym").getPublicKey();
        } catch (KeyStoreException e) {
            throw new GymException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt){
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return  true;
    }

    public String  getUsernameFromJWT(String jwt) {
        Claims claims= Jwts.parser().setSigningKey(getPublickey())
                .parseClaimsJws(jwt)
                .getBody();

        return  claims.getSubject();
    }
}
