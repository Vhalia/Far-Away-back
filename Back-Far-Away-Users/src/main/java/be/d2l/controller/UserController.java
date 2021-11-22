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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    /*@Autowired TODO
    private CustomProperties props;*/

    private final Algorithm jwtAlgorithm = Algorithm.HMAC256("FYy787YU936+g+uGTtUàtt)!9àt°UF");

    @GetMapping
    public Iterable<User> getUsers(){
        return service.getUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id){
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(User user, @PathVariable("id") int id){
        return service.updateUser(user, id);
    }

    @GetMapping("/{mail}")
    public User getUserByMail(@PathVariable("mail") String mail){
        return service.getUserByMail(mail);
    }

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

}
