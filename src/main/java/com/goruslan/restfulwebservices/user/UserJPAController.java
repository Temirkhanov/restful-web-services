package com.goruslan.restfulwebservices.user;

import com.goruslan.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAController {

    @Autowired
    private UserDaoService service;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(path = "/jpa/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/jpa/users/{id}")
    public Resource<User> getUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("id - " + id);
        // "all-users", SERVER_PATH + "/users"
        // getAllUsers
        Resource<User> resource = new Resource<User>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));
        // HATEOAS
        return resource;
    }


    @PostMapping("/jpa/users")
    public ResponseEntity<Object> newUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        // Will add uri to new created user into the header
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        // Returns 201 status if the user is created
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

}
