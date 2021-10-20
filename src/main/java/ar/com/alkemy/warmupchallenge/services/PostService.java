package ar.com.alkemy.warmupchallenge.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemy.warmupchallenge.entities.Post;
import ar.com.alkemy.warmupchallenge.repos.PostRepository;

@Service
public class PostService {

    @Autowired
    PostRepository repo;

    @Autowired
    UserService userService;

    public Post create(String title, String content, String image, String category, Date creationDate, Integer userId) {

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setImage(image);
        post.setCategory(category);
        post.setCreationDate(creationDate);
        post.setUser(userService.findByUserId(userId));
        
        return repo.save(post);
    }

    public List<Post> listAll() {
        return repo.findAll();
    }

    public Post findById(Integer id) {
        return repo.findByPostId(id);
    }

    public Post findByTitle(String title) {
        return repo.findByTitle(title);
    }

    public List<Post> findByCategory(String category) {
        return repo.findByCategory(category);
    }

    public Post update(Post post) {
        return repo.save(post);
    }

    public void delete(Post post) {
        repo.delete(post);
    }
    
}
