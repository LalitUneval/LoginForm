package com.lalit.securityDemo.service;

import com.lalit.securityDemo.CustomUserDetails;
import com.lalit.securityDemo.model.Users;
import com.lalit.securityDemo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUserName(username);
        if(Objects.isNull(user)){
            System.out.println("User not avaiable");
            throw new UsernameNotFoundException("User not found");
        }
         return new CustomUserDetails(user);
    }
}
