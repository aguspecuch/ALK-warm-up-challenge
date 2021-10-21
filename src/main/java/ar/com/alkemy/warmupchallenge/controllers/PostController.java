package ar.com.alkemy.warmupchallenge.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.alkemy.warmupchallenge.entities.Post;
import ar.com.alkemy.warmupchallenge.models.request.PostRequest;
import ar.com.alkemy.warmupchallenge.models.response.PostResponse;
import ar.com.alkemy.warmupchallenge.services.PostService;
import ar.com.alkemy.warmupchallenge.services.UserService;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService service;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllActives() {

        List<Post> posts = service.listAllActives();
        posts.sort(Comparator.comparing(Post::getCreationDate).reversed());
        List<PostResponse> list = new ArrayList<>();

        for (Post post : posts) {

            PostResponse p = new PostResponse();
            p.postId = post.getPostId();
            p.title = post.getTitle();
            p.image = post.getImage();
            p.category = post.getCategory();
            p.creationDate = post.getCreationDate();

            list.add(p);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAll() {

        List<Post> posts = service.listAll();
        posts.sort(Comparator.comparing(Post::getCreationDate).reversed());
        List<PostResponse> list = new ArrayList<>();

        for (Post post : posts) {

            PostResponse p = new PostResponse();
            p.postId = post.getPostId();
            p.title = post.getTitle();
            p.image = post.getImage();
            p.category = post.getCategory();
            p.creationDate = post.getCreationDate();

            list.add(p);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {

        Post post = service.findById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No post was found with that ID");
    }

    @GetMapping(params = "title")
    public ResponseEntity<?> findByTitle(@RequestParam(value = "title") String title) {

        Post p = service.findByTitle(title);

        if (p != null) {
            return ResponseEntity.ok(p);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Title not found");
    }

    @GetMapping(params = "category")
    public ResponseEntity<?> findByCategory(@RequestParam(value = "category") String category) {

        List<Post> p = service.findByCategory(category);

        if (p != null) {
            return ResponseEntity.ok(p);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostRequest req) {

        Post post = new Post();
        post.setTitle(req.title);
        post.setContent(req.content);
        post.setImage(req.image);
        post.setCategory(req.category);
        post.setCreationDate(req.creationDate);
        post.setUser(userService.findByUserId(req.userId));

        if (service.validateData(post)) {
            service.create(post);
            return ResponseEntity.ok("Post " + post.getPostId() + " created with succes");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PostRequest req) {
        Post p = service.findById(id);

        if (p != null) {
            p.setTitle(req.title);
            p.setContent(req.content);
            p.setImage(req.image);
            p.setCategory(req.category);
            p.setCreationDate(req.creationDate);
            p.setUser(userService.findByUserId(req.userId));
            service.update(p);

            return ResponseEntity.ok("Post " + p.getPostId() + " updated with succes.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PostId not found.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Post p = service.findById(id);

        if (p != null) {
            service.delete(p);
            return ResponseEntity.ok("Post " + id + " deleted with succes.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PostId not found");
    }

}
