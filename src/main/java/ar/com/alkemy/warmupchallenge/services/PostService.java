package ar.com.alkemy.warmupchallenge.services;

import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.alkemy.warmupchallenge.entities.Post;
import ar.com.alkemy.warmupchallenge.repos.PostRepository;

@Service
public class PostService {

    @Autowired
    PostRepository repo;

    public Post create(Post post) {
        return repo.save(post);
    }

    public List<Post> listAll() {
        return repo.findAll();
    }

    public List<Post> listAllActives() {
        return repo.findByDeleted(false);
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
        post.setDeleted(true);
        this.update(post);
    }

    public boolean validateData(Post post) {
        if (this.findById(post.getPostId()) != null)
            return false;
        if (this.findByTitle(post.getTitle()) != null)
            return false;
        if (!(this.validateUrl(post.getImage())))
            return false;
        if(!(this.validateCreationDate(post.getCreationDate())))
            return false;
        return true;
    }

    public boolean validateUrl(String image) {

        String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }

    public boolean validateCreationDate(Date creationDate) {
        if (creationDate.after(new Date())) {
            return false;
        }

        return true;
    }

}
