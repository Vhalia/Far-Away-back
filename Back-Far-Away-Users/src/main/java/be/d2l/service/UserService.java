package be.d2l.service;

import be.d2l.Exceptions.UnauthorizedException;
import be.d2l.Exceptions.UserAlreadyExistsException;
import be.d2l.Exceptions.UserNotFoundException;
import be.d2l.config.CustomProperties;
import be.d2l.model.User;
import be.d2l.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CustomProperties props;

    public Iterable<User> getUsers(){
        return repo.findAll();
    }

    public void deleteUser(int id) throws UserNotFoundException {
        if (!repo.existsById(id)) throw new UserNotFoundException("No user found with id " + id);
        repo.deleteById(id);
    }

    public User createUser(User receivedUser) throws UserAlreadyExistsException {
        if (repo.existsByMail(receivedUser.getMail())) throw new UserAlreadyExistsException();
        User u = new User();
        return repo.save(setUser(u,receivedUser));
    }

    public User updateUser(User receivedUser, int id) throws UserNotFoundException {
        User u = repo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return repo.save(setUser(u,receivedUser));
    }

    public User getUserByMail(String mail) throws UserNotFoundException {
        User userFound = repo.findByMail(mail);
        if (userFound == null) throw new UserNotFoundException("User with email " + mail);
        return userFound;
    }

    public User getUserById(int id) throws UserNotFoundException {
        User u = repo.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return u;
    }

    public User checkUser(String mail, String password) throws UnauthorizedException {
        User userFound = null;
        try {
            userFound = getUserByMail(mail);
        } catch (UserNotFoundException e) {
            throw new UnauthorizedException("Email incorrect");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(password, userFound.getPassword()))
            throw new UnauthorizedException("Password incorrect");
        return userFound;
    }

    private User setUser(User u,User receivedUser){
        if (receivedUser.getSurname() != null)
            u.setSurname(receivedUser.getSurname());
        if (receivedUser.getFirstName() != null)
            u.setFirstName(receivedUser.getFirstName());
        if (receivedUser.getNickname() != null)
            u.setNickname(receivedUser.getNickname());
        if (receivedUser.getBirthDate() != null)
            u.setBirthDate(receivedUser.getBirthDate());
        if (receivedUser.getAddress() != null)
            u.setAddress(receivedUser.getAddress());
        if (receivedUser.getMail() != null)
            u.setMail(receivedUser.getMail());
        if (receivedUser.getPassword() != null)
            u.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        return u;
    }

}
