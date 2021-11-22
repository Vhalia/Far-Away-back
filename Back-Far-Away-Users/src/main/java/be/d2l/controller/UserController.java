package be.d2l.controller;

import be.d2l.model.User;
import be.d2l.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

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



}
