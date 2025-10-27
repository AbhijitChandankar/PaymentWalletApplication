package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.UserClass;
import com.cg.entity.Wallet;
import com.cg.repository.UserDetailsRepository;

@Service
public class UserDetailsService {
	
	@Autowired
	private UserDetailsRepository detailsRepository;
	
	public String registerUser(UserClass user) {
		

		if (user.getUsername() == null || user.getPassword() == null || user.getEmailId() == null) {
            throw new IllegalArgumentException("All fields are required.");
        }

        if (!user.getEmailId().matches("^[^@]+@[^@]+\\.[^@]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }

		if(detailsRepository.findByEmailId(user.getEmailId()).isPresent()) {
			return "User with the given Details already present";
		}else {
			Wallet wallet = new Wallet();
			wallet.setUser(user);
			user.setWallet(wallet);
			detailsRepository.save(user);
			System.out.println("user details : "+user.toString());
			return "User registered successfully.";
		}
		}

	public List<UserClass> getAllUsers() {
		
		return detailsRepository.findAll();
	}

public Optional<UserClass> getFindByUsers(int id) {
		
		return detailsRepository.findById(id);
	}

}
