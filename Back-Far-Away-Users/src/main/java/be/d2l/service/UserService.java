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

    public User updateUser(User user, int id){
        User u = repo.findById(id).orElseThrow(InternalError::new);
        u.setSurname(user.getSurname());
        u.setFirstName(user.getFirstName());
        u.setNickname(user.getFirstName());
        u.setBirthDate(user.getBirthDate());
        u.setAddress(user.getAddress());
        u.setMail(user.getMail());
        u.setPassword(user.getPassword());
        u.setAdmin(user.isAdmin());
        return repo.save(u);
    }

    public User getUserByMail(String mail){
        return repo.findByMail(mail);
    }

    public User checkUser(String mail, String password) throws UnauthorizedException {
        User userFound = getUserByMail(mail);
        if (userFound == null)
             throw new UnauthorizedException("Email incorrect");
        //TODO HASHER LE MDP & LE VERIFIER
        if (!userFound.getPassword().equals(password))
            throw new UnauthorizedException("Password incorrect");
        return userFound;
    }

}
