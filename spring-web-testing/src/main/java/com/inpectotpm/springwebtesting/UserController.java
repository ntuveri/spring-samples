package com.inpectotpm.springwebtesting;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<Iterable<User>> findAll() {
		Iterable<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> find(@PathVariable Integer id) {
		Optional<User> user = userService.findById(id);

		if(user.isPresent()) {
			return ResponseEntity.ok(user);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/users")
	public User create(@RequestBody User user) {
		userService.save(user);
		return user;
	}

	@DeleteMapping("/users/{id}")
	public void deleteById(Integer id) {
		userService.deleteById(id);
	}



}
