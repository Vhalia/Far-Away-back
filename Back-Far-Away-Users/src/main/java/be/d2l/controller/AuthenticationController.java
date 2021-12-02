package be.d2l.controller;

import be.d2l.Exceptions.UnauthorizedException;
import be.d2l.Exceptions.UserAlreadyExistsException;
import be.d2l.Exceptions.UserNotFoundException;
import be.d2l.config.CustomProperties;
import be.d2l.model.User;
import be.d2l.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private UserService service;

    private Algorithm jwtAlgorithm;

    private ObjectMapper jsonMapper;

    public AuthenticationController(UserService service, CustomProperties props) {
        this.service = service;
        jwtAlgorithm = Algorithm.HMAC256(props.getJWTSecret());
        jsonMapper = new ObjectMapper();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        if (!user.checkUserFields())
            return ResponseEntity.badRequest().body("Missing mandatory information");
        User createdUser = null;
        String token = null;
        try {
            createdUser = service.createUser(user);
            token = JWT.create().withIssuer("auth0").withClaim("userId", createdUser.getId())
                    .withClaim("mail", createdUser.getMail()).sign(jwtAlgorithm);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(403).body("A user with email " + user.getMail() + " already exists");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user" , createdUser);
        return ResponseEntity.created(location).body(node);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        if (user.getMail() == null || user.getMail().isBlank() || user.getMail().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank())
            return ResponseEntity.badRequest().build();
        User userFound = null;
        String token = null;
        try {
            userFound = service.checkUser(user.getMail(), user.getPassword());
            token = JWT.create().withIssuer("auth0").withClaim("userId", userFound.getId())
                    .withClaim("mail", userFound.getMail()).sign(jwtAlgorithm);
        }catch (UnauthorizedException e) {
            return ResponseEntity.status(401).build();
        }catch(Exception e) {
            return ResponseEntity.internalServerError().body("Error while creating the token");
        }
        ObjectNode node = jsonMapper.createObjectNode().put("token", token).putPOJO("user" , userFound);
        return ResponseEntity.ok(node);
    }

    /*@PostMapping("/createToken")
    public ResponseEntity<String> createToken(@RequestBody User user) {
        if(user == null || user.getMail() == null || user.getMail().isEmpty() || user.getMail().isBlank())
            return ResponseEntity.badRequest().build();
        String token = null;
        try {
            User userFound = service.getUserByMail(user.getMail());
            token = JWT.create().withIssuer("auth0").withClaim("userId", userFound.getId())
                    .withClaim("mail", userFound.getMail()).sign(jwtAlgorithm);
        }catch(UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }catch(Exception e) {
            return ResponseEntity.internalServerError().body("Error while creating the token");
        }
        return ResponseEntity.ok(token);
    }*/

}
