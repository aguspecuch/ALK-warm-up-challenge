package ar.com.alkemy.warmupchallenge;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.com.alkemy.warmupchallenge.services.PostService;
import ar.com.alkemy.warmupchallenge.services.UserService;
import ar.com.alkemy.warmupchallenge.entities.User;
import ar.com.alkemy.warmupchallenge.entities.Post;

@SpringBootTest
class WarmUpChallengeApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	// Tests that the data entered by the user is correct
	@Test
	void validateRegisterData() {

		User userOk = new User();
		userOk.setEmail("validEmail@hotmail.com"); // Correct email/username.
		userOk.setPassword("ValidPassword123"); // Correct password.

		assertTrue(userService.validateData(userOk));

		User userNotOk = new User();
		userNotOk.setEmail("@hotmail.com"); // Incorrect email/username.
		userNotOk.setPassword("ValidPassword123"); // Correct password.

		assertFalse(userService.validateData(userNotOk));

		User userAlredyExists = new User();
		userAlredyExists.setEmail("alkemy@hotmail.com"); // Email already registered.
		userAlredyExists.setPassword("ValidPassword123"); // Correct password.

		assertFalse(userService.validateData(userAlredyExists));

	}

	// Tests the search of users by ID and username.
	@Test
	void validateUserFind() {

		assertNull(userService.findByUserId(15)); // Inexistent user with ID = 15
		assertNull(userService.findByUsername("notRegisteredEmail@hotmail.com")); // Inexistent user with that email.
		assertNotNull(userService.findByUserId(1));
		assertNotNull(userService.findByUsername("alkemy@hotmail.com"));
	}

	// Tests that the data of the new post is valid.
	@Test
	void validatePostData() {

		Post postOk = new Post();
		postOk.setTitle("A valid title");
		postOk.setContent("A valid content");
		postOk.setImage("https://validurl.com/image.jpg");
		postOk.setCategory("Test");
		postOk.setCreationDate(new Date());
		
		assertTrue(postService.validateData(postOk));

		Post postTitleNotOk = new Post();
		postTitleNotOk.setTitle("Tito the dog"); // A post with the same title already exists.
		postTitleNotOk.setContent("A valid content");
		postTitleNotOk.setImage("https://validurl.com/image.jpg");
		postTitleNotOk.setCategory("Test");
		postTitleNotOk.setCreationDate(new Date());

		assertFalse(postService.validateData(postTitleNotOk));

		Post postImageNotOk = new Post();
		postImageNotOk.setTitle("A valid title");
		postImageNotOk.setContent("A valid content");
		postImageNotOk.setImage("https://validurl.com/image"); // Not valid URL (doesn't end with .jpg, .png, .gif)
		postImageNotOk.setCategory("Test");
		postImageNotOk.setCreationDate(new Date());

		assertFalse(postService.validateData(postImageNotOk));

	}

}
