package com.cg.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.dto.Response;
import com.cg.dto.TransferAmount;
import com.cg.entity.UserClass;
import com.cg.entity.Wallet;
import com.cg.entity.controller.UserDetailsController;
import com.cg.repository.UserDetailsRepository;
import com.cg.service.UserDetailsService;
import com.cg.service.WalletService;

@SpringBootTest
//@AutoConfigureMockMvc
public class UserDetailsControllerTest {
	

	 //  @Autowired
	//    private MockMvc mockMvc;

	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private WalletService walletService;
	
	@Mock
	private UserDetailsRepository userDetailsRepository;
	
	@InjectMocks
	private UserDetailsController userDetailsController;
	
	
	@Test
	public void createUserWalletSuccess() {
		UserClass user = new UserClass();
		user.setEmailId("abhijit@gmail.com");
		user.setPassword("aa");
		user.setUser_name("Abhijit");
		
		//Mockito.
		when(userDetailsService.registerUser(user)).thenReturn("User registered successfully.");
		//String status= userDetailsService.registerUser(user);
		ResponseEntity<EntityModel<Response>> response  = userDetailsController.createUserWallet(user);
		System.out.println(response.getBody());
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		EntityModel<Response> res =response.getBody();
		assertEquals("User registered successfully.", response.getBody().getContent().getContent());
		assertTrue(response.getBody().getLinks().hasLink("self"));
	}
	
	
	@Test
	public void transferAmountSuccess() {
		System.out.println("Inside testAddAmountFail");
		TransferAmount amountRequest = new TransferAmount();
		UserClass userS = new UserClass();
		userS.setEmailId("abhijit@gmail.com");
		userS.setPassword("aa");
		userS.setUser_name("Abhijit");
		Wallet walletU = new Wallet();
		walletU.setBalance(5000.0);
		walletU.setUser(userS);
		userS.setWallet(walletU);
		userDetailsRepository.save(userS);
		UserClass userR = new UserClass();
		userR.setEmailId("John@gmail.com");
		userR.setPassword("aa");
		userR.setUser_name("John");
		Wallet walletR = new Wallet();
		walletR.setBalance(50.0);
		walletR.setUser(userR);
		userR.setWallet(walletR);
		userDetailsRepository.save(userR);
		
		amountRequest.setReceiverEmailId("John@gmail.com");
		amountRequest.setSenderEmailId("abhijit@gmail.com");
		amountRequest.setAmount(1000.0);
		//String status = walletService.transferAmount(amountRequest);
		
		//Mockito.
		when(walletService.transferAmount(amountRequest)).thenReturn("Amount Transferred Successfully | Total balance of "+amountRequest.getReceiverEmailId()+" is "+walletR.getBalance());
		
		ResponseEntity<EntityModel<Response>> response  = userDetailsController.transferAmount(amountRequest);
		System.out.println(response.getBody());
		assertEquals(HttpStatus.CREATED,response.getStatusCode());
		EntityModel<Response> res =response.getBody();
		assertEquals("Amount Transferred Successfully | Total balance of "+amountRequest.getReceiverEmailId()+" is "+walletR.getBalance(), response.getBody().getContent().getContent());
		assertTrue(response.getBody().getLinks().hasLink("self"));
	}
	
}
