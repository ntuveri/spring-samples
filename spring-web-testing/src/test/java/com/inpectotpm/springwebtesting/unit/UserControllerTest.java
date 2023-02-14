package com.inpectotpm.springwebtesting.unit;


import java.util.ArrayList;
import java.util.Optional;

import com.inpectotpm.springwebtesting.User;
import com.inpectotpm.springwebtesting.UserController;
import com.inpectotpm.springwebtesting.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Test
	public void testGetUserById_Ok() {

		int id = 99;
		User user = new User();
		when(userService.findById(id)).thenReturn(Optional.of(user));

		ResponseEntity<Optional<User>> response = userController.find(id);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().get()).isEqualTo(user);
	}

	@Test
	public void testGetUserById_NotFound() {

		int id = 99;
		when(userService.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<Optional<User>> response = userController.find(id);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}
}
