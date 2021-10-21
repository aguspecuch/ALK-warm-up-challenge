package ar.com.alkemy.warmupchallenge.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean login(String username, String password) {

        User u = this.findByUsername(username);

        if (!u.getPassword().equals(Crypto.encrypt(password, u.getEmail().toLowerCase()))) {
            return false;
        }

        return true;

    }

    public User findByUsername(String username) {
        return repo.findByEmail(username);
    }

    public User findByUserId(Integer id) {
        return repo.findByUserId(id);
    }

    public boolean validateData(User user) {

        if (this.findByUsername(user.getEmail()) != null)
            return false;
        if (!(this.isUsernameValid(user.getEmail())))
            return false;
        // if (!(this.isPasswordValid(user.getPassword())))
        //     return false;

        return true;
    }

    public boolean isUsernameValid(String username) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    // public boolean isPasswordValid(String password) {
    //     String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    //     Pattern pattern = Pattern.compile(regex);
    //     Matcher matcher = pattern.matcher(password);
    //     return matcher.matches();
    // }

}
