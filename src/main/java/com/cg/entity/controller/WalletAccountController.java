package com.cg.entity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.AddAmountRequest;
import com.cg.dto.TransferAmount;
import com.cg.service.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletAccountController {

	@Autowired
	private WalletService service;
	
	@PostMapping("/addAmount")
	public ResponseEntity<String> AddAmount(@RequestBody AddAmountRequest request){
		
		String status = service.addAmount(request);
		return new ResponseEntity<>(status,HttpStatus.CREATED);
	}
	
	@PostMapping("/transferAmount")
	public ResponseEntity<String> transferAmount(@RequestBody TransferAmount trequest){
		
		String status = service.transferAmount(trequest);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
}
