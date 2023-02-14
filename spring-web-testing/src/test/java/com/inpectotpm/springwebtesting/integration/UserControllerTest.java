package com.inpectotpm.springwebtesting.integration;


import java.util.Optional;

import com.inpectotpm.springwebtesting.User;
import com.inpectotpm.springwebtesting.UserController;
import com.inpectotpm.springwebtesting.UserService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	public void testGetUserById_Ok() throws Exception {

		int id = 99;
		User user = new User();
		user.setId(id);
		when(userService.findById(id)).thenReturn(Optional.of(user));

		mockMvc.perform(get("/users/" + id))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(id))
			.andReturn();
	}

	@Test
	public void testGetUserById_NotFound() throws Exception {

		int id = 99;
		when(userService.findById(id)).thenReturn(Optional.empty());

		mockMvc.perform(get("/users/" + id))
			.andExpect(status().isNotFound())
			.andExpect(content().string(Strings.EMPTY))
			.andReturn();
	}
}
