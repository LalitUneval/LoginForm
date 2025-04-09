package com.lalit.securityDemo.service;

import com.lalit.securityDemo.model.Users;
import com.lalit.securityDemo.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //for the checking the password and username we used AuthenticationManager
    private final AuthenticationManager authenticationManager;

    //form here we stareting the Generateing the JWT token
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public Users register(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
    }

    public String verify(Users users) {
        // this will check only the user name not the password for the proper authentication we are using
        // the AuthenticationManager
//        Users users1 = userRepository.findByUserName(users.getUsername());
//        if(users1==null)
//            return "failure";
//        return"success";
        //used the authentication manage

        Authentication authentication = authenticationManager .authenticate(
                new UsernamePasswordAuthenticationToken(
                        users.getUsername(),users.getPassword()
                )
        );
        if(authentication.isAuthenticated()){
//            return "12386453";
            //know here we pass the JWT token
            return jwtService.generateToken(users);
        }
        return "failure";
    }


}
