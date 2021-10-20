package ar.com.alkemy.warmupchallenge.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.alkemy.warmupchallenge.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

    Post findByPostId(Integer id);
    Post findByTitle(String title);
    List<Post> findByCategory(String category);
    
}
