package be.d2l.controller;

import be.d2l.Exceptions.UnauthorizedException;
import be.d2l.config.CustomProperties;
import be.d2l.model.User;
import be.d2l.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Algorithm jwtAlgorithm;

    @GetMapping
    public Iterable<User> getUsers(){
        return service.getUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id){
        if(id < 0) return ResponseEntity.badRequest().body("Malformed user id " + id);
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = service.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody User user, @PathVariable("id") int id){
        if(id < 0) return ResponseEntity.badRequest().body("Malformed user id " + id);
        if(user == null) return ResponseEntity.badRequest().body("Empty body");
        service.updateUser(user, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{mail}")
    public ResponseEntity getUserByMail(@PathVariable("mail") String mail){
        if(mail == null) return ResponseEntity.badRequest().body("Empty body");
        service.getUserByMail(mail);
        return ResponseEntity.ok().build();
    }

    /*@PostMapping
    public ResponseEntity register(@RequestBody User user){
        if (user.getMail() == null || user.getMail().isBlank() || user.getMail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank())
            return ResponseEntity.badRequest().build();

        User createdUser = service.createUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        if (user.getMail() == null || user.getMail().isBlank() || user.getMail().isEmpty()
            || user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank())
            return ResponseEntity.badRequest().build();
        User userFound = null;
        try {
            userFound = service.checkUser(user.getMail(), user.getPassword());
            String token = JWT.create().withIssuer("auth0").withClaim("user", userFound.getId()).sign(jwtAlgorithm);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userFound);
    }

    public UserController(UserService service, CustomProperties props) {
        this.service = service;
        jwtAlgorithm = Algorithm.HMAC256(props.getJWTSecret());
    }

}
