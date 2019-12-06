package com.goruslan.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id){
        return service.findOne(id);
    }


    @PostMapping("/users")
    public ResponseEntity<Object> newUser(@RequestBody User user){
        User savedUser = service.save(user);

        // Will add uri to new created user into the header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        // Returns 201 status if the user is created
        return ResponseEntity.created(location).build();
    }




}
