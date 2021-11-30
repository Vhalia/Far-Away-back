package be.d2l.service;

import be.d2l.Exceptions.UnauthorizedException;
import be.d2l.config.CustomProperties;
import be.d2l.model.User;
import be.d2l.repo.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CustomProperties props;

    /*private final Algorithm jwtAlgorithm = Algorithm.HMAC256(props.getJWTSecret());
    String token = JWT.create().withIssuer("auth0").withClaim("user", user.getId()).sign(jwtAlgorithm);
    private final JWTVerifier verifier = JWT.require(jwtAlgorithm).withIssuer("auth0").build();*/

    public Iterable<User> getUsers(){
        return repo.findAll();
    }

    public void deleteUser(int id){
        repo.deleteById(id);
    }

    public User createUser(User receivedUser){
        User u = new User();
        return repo.save(setUser(u,receivedUser));
    }

    public User updateUser(User receivedUser, int id){
        User u = repo.findById(id).orElseThrow(InternalError::new);
        return repo.save(setUser(u,receivedUser));
    }

    public User getUserByMail(String mail){
        return repo.findByMail(mail);
    }

    public User checkUser(String mail, String password) throws UnauthorizedException {
        User userFound = getUserByMail(mail);
        if (userFound == null)
             throw new UnauthorizedException("Email incorrect");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(userFound.getPassword(), encoder.encode(password)))
            throw new UnauthorizedException("Password incorrect");
        return userFound;
    }

    private User setUser(User u,User receivedUser){
        u.setSurname(receivedUser.getSurname());
        u.setFirstName(receivedUser.getFirstName());
        u.setNickname(receivedUser.getFirstName());
        u.setBirthDate(receivedUser.getBirthDate());
        u.setAddress(receivedUser.getAddress());
        u.setMail(receivedUser.getMail());
        u.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        return u;
    }

}
