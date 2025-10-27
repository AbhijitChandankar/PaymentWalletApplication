package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.entity.UserClass;
import com.cg.repository.UserDetailsRepository;

@SpringBootTest
public class UserDetailsServiceTest {

	@Mock
	private UserDetailsRepository userDetailsRepository;

	@InjectMocks
	private UserDetailsService userDetailsService;

	@Test
	public void registerUserSucces() {

		UserClass user = new UserClass();
		user.setEmailId("abhijit@gmail.com");
		user.setPassword("aa");
		user.setUser_name("Abhijit");

		// when(userDetailsRepository.findByEmailId(user.getEmailId())).thenReturn(Optional.of(user));

		// when(userDetailsRepository.save(user)).thenReturn(user);
		String status = userDetailsService.registerUser(user);
		assertEquals("User registered successfully.", status);
	}

	@Test
	public void testDuplicateEmail() {
		UserClass user = new UserClass();
		user.setEmailId("abhijit@gmail.com");
		user.setPassword("aa");
		user.setUser_name("Abhijit");
		when(userDetailsRepository.findByEmailId(user.getEmailId())).thenReturn(Optional.of(user));
		UserClass user1 = new UserClass();
		user1.setEmailId("abhijit@gmail.com");
		user1.setPassword("aa");
		user1.setUser_name("Abhijit");

		String result = userDetailsService.registerUser(user1);
		assertEquals("User with the given Details already present", result);
	}

	@Test
	public void testMissingFields() {
		UserClass user = new UserClass();
		user.setEmailId("abhijit@gmail.com");
		user.setPassword(null);
		user.setUser_name("Abhijit");

		Exception exception = assertThrows(Exception.class, () -> {
			userDetailsService.registerUser(user);
		});

		assertEquals("All fields are required.", exception.getMessage());
	}

	@Test
	public void testInvalidEmailFormat() {
		UserClass user = new UserClass();
		user.setUsername("John");
		user.setPassword("password123");
		user.setEmailId("John#gmail.com");

		Exception exception = assertThrows(Exception.class, () -> {
			userDetailsService.registerUser(user);
		});

		assertEquals("Invalid email format.", exception.getMessage());
	}

}
