package be.d2l.controller;

import be.d2l.Exceptions.UnauthorizedException;
import be.d2l.Exceptions.UserAlreadyExistsException;
import be.d2l.Exceptions.UserNotFoundException;
import be.d2l.config.CustomProperties;
import be.d2l.model.User;
import be.d2l.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<User> getUsers(){
        return service.getUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        if(id < 0) return ResponseEntity.badRequest().body("Malformed user id " + id);
        try {
            service.deleteUser(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        User createdUser = null;
        try {
            createdUser = service.createUser(user);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(403).body("A user with email " + user.getMail() + " already exists");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable("id") int id){
        if(id < 0) return ResponseEntity.badRequest().body("Malformed user id " + id);
        if(user == null) return ResponseEntity.badRequest().body("Empty body");
        User updatedUser = null;
        try {
            updatedUser = service.updateUser(user, id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedUser);
    }

    @GetMapping("/{mail}")
    public ResponseEntity getUserByMail(@PathVariable("mail") String mail){
        if(mail == null || mail.isEmpty() || mail.isBlank()) return ResponseEntity.badRequest().body("Empty body");
        User userFound = null;
        try {
            userFound = service.getUserByMail(mail);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userFound);
    }

    @GetMapping("/withid/{id}")
    public ResponseEntity getUserById(@PathVariable("id") int id){
        if(id < 0) return ResponseEntity.badRequest().body("Malformed user id " + id);
        User userFound;
        try {
            userFound = service.getUserById(id);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userFound);
    }

}
