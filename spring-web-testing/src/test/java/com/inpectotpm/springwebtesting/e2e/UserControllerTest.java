package com.inpectotpm.springwebtesting.e2e;


import java.util.Optional;

import com.inpectotpm.springwebtesting.User;
import com.inpectotpm.springwebtesting.UserService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContentAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private UserService userService;

	@Test
	public void testGetUserById_Ok() {

		int id = 99;
		User user = new User();
		user.setId(id);
		when(userService.findById(id)).thenReturn(Optional.of(user));

		ResponseEntity<String> response = testRestTemplate.getForEntity("/users/" + id, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat((Integer) JsonPath.read(response.getBody(),"$.id")).isEqualTo(id);
	}

	@Test
	public void testGetUserById_NotFound() {

		int id = 99;
		when(userService.findById(id)).thenReturn(Optional.empty());

		ResponseEntity<String> response = testRestTemplate.getForEntity("/users/" + id, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.hasBody()).isFalse();
	}
}
