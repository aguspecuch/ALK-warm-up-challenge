package ar.com.alkemy.warmupchallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ar.com.alkemy.warmupchallenge.entities.User;
import ar.com.alkemy.warmupchallenge.repos.UserRepository;
import ar.com.alkemy.warmupchallenge.security.Crypto;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public User register(User user) {

        User u = new User();
        u.setEmail(user.getEmail());
        u.setPassword(Crypto.encrypt(user.getPassword(), user.getEmail().toLowerCase()));

        return repo.save(u);
    }

    public User login(String username, String password) {

        User u = this.findByUsername(username);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {

            throw new BadCredentialsException("User or password invalid.");
        }

        return u;

    }

    public User findByUsername(String username) {
        return repo.findByEmail(username);
    }

}
