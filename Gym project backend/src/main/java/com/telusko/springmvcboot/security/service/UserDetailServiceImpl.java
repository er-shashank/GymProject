package com.telusko.springmvcboot.security.service;

import com.telusko.springmvcboot.security.entity.User;
import com.telusko.springmvcboot.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetching the user by username
        User user= userRepository.findByusername(username).orElseThrow(()-> new UsernameNotFoundException("No User Found "+username));

        //here we are using fully qualified class name because we have a custom User class as well in this project

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true,true,true,true,
                getAuthorities("ROLE_USER")
                );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleUser) {
        return  Collections.singletonList(new SimpleGrantedAuthority(roleUser));
    }
}
