package com.lalit.securityDemo.contoller;

import com.lalit.securityDemo.model.Users;
import com.lalit.securityDemo.repository.UserRepository;
import com.lalit.securityDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    //adding of the user
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public Users register(@RequestBody Users users  ){
        return userService.register(users);
    }
    //getting of user
    @PostMapping("/login")
    public  String login(@RequestBody Users users){
        return userService.verify(users);
//        Users users1 = userRepository.findByUserName(users.getUsername());
//        if(users1==null)
//            return "failure";
//        return"success";
    }
}
