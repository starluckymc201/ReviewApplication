package com.example.RatingBackend.controller;

import com.example.RatingBackend.entity.Content;
import com.example.RatingBackend.entity.User;
import com.example.RatingBackend.exception.ResourceNotFoundException;
import com.example.RatingBackend.md5.MD5;
import com.example.RatingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user){
        int id = user.getId();
        String email = user.getEmail();
        String password = MD5.getMd5(user.getPassword());
        User newUser = new User(id, email, password);

        return this.userRepository.save(newUser);
    }
}
