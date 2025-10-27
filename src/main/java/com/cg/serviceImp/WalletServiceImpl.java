package com.cg.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.AddAmountRequest;
import com.cg.dto.TransferAmount;
import com.cg.entity.Transaction;
import com.cg.entity.UserClass;
import com.cg.entity.Wallet;
import com.cg.repository.TransactionRepository;
import com.cg.repository.UserDetailsRepository;
import com.cg.repository.WalletRepository;
import com.cg.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {

	@Autowired
	private UserDetailsRepository detailsRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public String addAmount(AddAmountRequest request) {
		
		System.out.println("Impl request Email:"+request.getEmailId());
		Optional<UserClass> user = detailsRepository.findByEmailId(request.getEmailId());
		
//	System.out.println("Impl Email:"+user.get().getEmailId());
		if(user.isEmpty()) {
			return "There is no user registered with above email id";
		}
		Wallet wallet = new Wallet();
		wallet=user.get().getWallet();
		if(request.getAction().equalsIgnoreCase("ADD")) {
		wallet.setBalance(user.get().getWallet().getBalance()+request.getAmount());
		
		Wallet storedwallet =  walletRepository.save(wallet);
		System.out.println("storedwallet : "+storedwallet.getBalance());
		Transaction transaction = new Transaction();
			transaction.setAmount(request.getAmount());
			transaction.setWallet(wallet);
			transaction.setStatus("Credited");
			transactionRepository.save(transaction);
		//wallet.setCurrency(1);
				
		return "Amount added successfully";//"content": "Balance added successfully",
		}else {
			return "Available balance: " +user.get().getWallet().getBalance().toString();
		}
	}

	@Override
	public String transferAmount(TransferAmount trequest) {
		
		Optional<UserClass> userS = detailsRepository.findByEmailId(trequest.getSenderEmailId());
		if(userS.isEmpty()) {
			return "There is no user registered with "+trequest.getSenderEmailId()+" email id";
		}
		if(userS.get().getWallet().getBalance()<trequest.getAmount()) {
			return "Insufficient balance. You have Rs "+userS.get().getWallet().getBalance()+" balance in your wallet";
		}
		
		Optional<UserClass> userR = detailsRepository.findByEmailId(trequest.getReceiverEmailId());
		if(userR.isEmpty()) {
			return "There is no user registered with "+trequest.getReceiverEmailId()+" email id";
		}
		Wallet walletS = new Wallet();
		walletS=userS.get().getWallet();
		walletS.setBalance(userS.get().getWallet().getBalance()-trequest.getAmount());
		
		Wallet storedwallet =  walletRepository.save(walletS);
		Transaction transaction = new Transaction();
			transaction.setAmount(-trequest.getAmount());
			transaction.setWallet(walletS);
			transaction.setStatus("Debited");
			transactionRepository.save(transaction);
			
			Wallet walletR = new Wallet();
			walletR=userR.get().getWallet();
			walletR.setBalance(userR.get().getWallet().getBalance()+trequest.getAmount());
			
			Wallet storedWalletR =  walletRepository.save(walletR);
			Transaction transactionR = new Transaction();
				transactionR.setAmount(+trequest.getAmount());
				transactionR.setWallet(walletR);
				transactionR.setStatus("Credited");
				transactionRepository.save(transactionR);
		
		return "Amount Transferred Successfully | Total balance of "+trequest.getReceiverEmailId()+" is "+storedWalletR.getBalance();
	}

}
