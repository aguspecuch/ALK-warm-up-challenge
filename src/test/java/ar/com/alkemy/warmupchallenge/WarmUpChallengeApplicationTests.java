package ar.com.alkemy.warmupchallenge;

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

}
