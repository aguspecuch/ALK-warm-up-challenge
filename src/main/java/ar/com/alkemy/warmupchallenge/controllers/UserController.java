package ar.com.alkemy.warmupchallenge.controllers;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemy.warmupchallenge.entities.User;
import ar.com.alkemy.warmupchallenge.models.request.LoginRequest;
import ar.com.alkemy.warmupchallenge.models.response.GenericResponse;
import ar.com.alkemy.warmupchallenge.models.response.LoginResponse;
import ar.com.alkemy.warmupchallenge.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse> register(@RequestBody User user) {

        User u = service.register(user);
        GenericResponse r = new GenericResponse();
        r.isOk = true;
        r.id = u.getUserId();
        r.message = "Registered user successfully.";

        return ResponseEntity.ok(r);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {

        User u = service.login(req.username, req.password);
        String token = getJWTToken(u.getUsername());

        LoginResponse r = new LoginResponse();
        r.id = u.getUserId();
        r.username = u.getUsername();
        r.token = token;

        return ResponseEntity.ok(r);
    }

    private String getJWTToken(String username) {

        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder().setId("softtekJWT").setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return token;
    }

}
