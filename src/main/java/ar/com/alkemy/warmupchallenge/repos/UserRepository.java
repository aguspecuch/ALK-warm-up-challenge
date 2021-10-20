package ar.com.alkemy.warmupchallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.warmupchallenge.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    User findByUserId(Integer userId);
    User findByUsername(String username);
    User findByEmail(String email);
}
