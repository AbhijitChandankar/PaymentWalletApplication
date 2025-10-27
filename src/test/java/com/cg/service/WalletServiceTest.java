package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.dto.AddAmountRequest;
import com.cg.dto.TransferAmount;
import com.cg.entity.Transaction;
import com.cg.entity.UserClass;
import com.cg.entity.Wallet;
import com.cg.repository.TransactionRepository;
import com.cg.repository.UserDetailsRepository;
import com.cg.repository.WalletRepository;
import com.cg.serviceImp.WalletServiceImpl;

@SpringBootTest
public class WalletServiceTest {
	
	@Mock
	private WalletRepository walletRepository;
	
	@Mock
	private UserDetailsRepository userDetailsRepository;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private WalletServiceImpl walletServiceImpl;
	
	@Test
	public void testAddAmountSuccess() {
		AddAmountRequest amountRequest = new AddAmountRequest();
		UserClass userU = new UserClass();
		userU.setEmailId("abhijit@gmail.com");
		userU.setPassword("aa");
		userU.setUser_name("Abhijit");
		Wallet walletU = new Wallet();
		walletU.setBalance(50.0);
		walletU.setUser(userU);
		userU.setWallet(walletU);
		userDetailsRepository.save(userU);
		amountRequest.setEmailId("abhijit@gmail.com");
		amountRequest.setAmount(1000.0);
		amountRequest.setAction("VIEW");
		
				//Optional<UserClass> user = Optional.of(new UserClass());  
				when(userDetailsRepository.findByEmailId(amountRequest.getEmailId())).thenReturn(Optional.of(userU));
				Wallet wallet = new Wallet();
				wallet= userU.getWallet();
				if(amountRequest.getAction().equalsIgnoreCase("ADD")) {
				when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
				
				String status = walletServiceImpl.addAmount(amountRequest);
				System.out.println(status);
				assertEquals("Amount added successfully", status);
				}else {
					String status = walletServiceImpl.addAmount(amountRequest);
					System.out.println(status);
					assertEquals("Available balance: " +wallet.getBalance().toString(), status);
				}
	}
	
	@Test
	public void testAddAmountFail() {
		System.out.println("Inside testAddAmountFail");
		AddAmountRequest amountRequest = new AddAmountRequest();
		UserClass userU = new UserClass();
		userU.setEmailId("abhijit@gmail.com");
		userU.setPassword("aa");
		userU.setUser_name("Abhijit");
		Wallet walletU = new Wallet();
		walletU.setBalance(50.0);
		walletU.setUser(userU);
		userU.setWallet(walletU);
		userDetailsRepository.save(userU);
		amountRequest.setEmailId("John@gmail.com");
		amountRequest.setAmount(1000.0);
		amountRequest.setAction("ADD");
				
					Optional<UserClass> user = java.util.Optional.empty();  
				when(userDetailsRepository.findByEmailId(amountRequest.getEmailId())).thenReturn(user);
				System.out.println("userEmail = "+userU.getEmailId());
				Wallet wallet = new Wallet();
				wallet= userU.getWallet();
				when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
				System.out.println("amountRequest.getEmailId() = "+amountRequest.getEmailId());
				String status = walletServiceImpl.addAmount(amountRequest);
				System.out.println(status);
				System.out.println("Outside testAddAmountFail");
				assertEquals("There is no user registered with above email id", status);
				
	}
	
	

	@Test
	public void testTransferAmountSuccess() {
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
		
				
				Optional<UserClass> user = java.util.Optional.empty();  
				when(userDetailsRepository.findByEmailId(amountRequest.getSenderEmailId())).thenReturn(Optional.of(userS));
				when(userDetailsRepository.findByEmailId(amountRequest.getReceiverEmailId())).thenReturn(Optional.of(userR));
				//System.out.println("userEmail = "+userU.getEmailId());
				Wallet walletS = new Wallet();
				walletS= userS.getWallet();
				when(walletRepository.save(any(Wallet.class))).thenReturn(walletS);
				
				Wallet walletRR = new Wallet();
				walletRR= userR.getWallet();
				when(walletRepository.save(any(Wallet.class))).thenReturn(walletRR);
				
				//System.out.println("amountRequest.getEmailId() = "+amountRequest.getEmailId());
				
				String status = walletServiceImpl.transferAmount(amountRequest);
				System.out.println(status);
				System.out.println("Outside testAddAmountFail");
				assertEquals("Amount Transferred Successfully | Total balance of "+amountRequest.getReceiverEmailId()+" is "+walletR.getBalance(), status);
				
	}
	
	@Test
	public void testTransferAmountFail() {
		System.out.println("Inside testAddAmountFail");
		TransferAmount amountRequest = new TransferAmount();
		UserClass userS = new UserClass();
		userS.setEmailId("abhijit@gmail.com");
		userS.setPassword("aa");
		userS.setUser_name("Abhijit");
		Wallet walletU = new Wallet();
		walletU.setBalance(500.0);
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
		
				
				Optional<UserClass> user = java.util.Optional.empty();  
				when(userDetailsRepository.findByEmailId(amountRequest.getSenderEmailId())).thenReturn(Optional.of(userS));
//				when(userDetailsRepository.findByEmailId(amountRequest.getReceiverEmailId())).thenReturn(Optional.of(userR));
//				//System.out.println("userEmail = "+userU.getEmailId());
				Wallet walletS = new Wallet();
				walletS= userS.getWallet();
//				when(walletRepository.save(any(Wallet.class))).thenReturn(walletS);
//				
//				Wallet walletRR = new Wallet();
//				walletRR= userR.getWallet();
//				when(walletRepository.save(any(Wallet.class))).thenReturn(walletRR);
				
				//System.out.println("amountRequest.getEmailId() = "+amountRequest.getEmailId());
				
				String status = walletServiceImpl.transferAmount(amountRequest);
				System.out.println(status);
				System.out.println("Outside testAddAmountFail");
				assertEquals("Insufficient balance. You have Rs "+walletS.getBalance()+" balance in your wallet", status);
				
	}
}
