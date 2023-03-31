package com.example.RatingBackend.controller;

import com.example.RatingBackend.entity.Content;
import com.example.RatingBackend.entity.User;
import com.example.RatingBackend.exception.ResourceNotFoundException;
import com.example.RatingBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user){
        return this.userRepository.save(user);
    }
}
