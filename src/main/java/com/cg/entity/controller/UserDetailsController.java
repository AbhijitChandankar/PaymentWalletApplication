package com.cg.entity.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.AddAmountRequest;
import com.cg.dto.Response;
import com.cg.dto.TransferAmount;
import com.cg.entity.UserClass;
import com.cg.service.UserDetailsService;
import com.cg.service.WalletService;

@RestController
@RequestMapping("payment/v1/users")
public class UserDetailsController {
	
	@Autowired
	private UserDetailsService userdetailsService;

	@Autowired
	private WalletService walletService;
	
	@PostMapping
	public ResponseEntity<EntityModel<Response>> createUserWallet(@RequestBody UserClass user) {
		
		String result = userdetailsService.registerUser(user);
		Response response = new Response(result);
		//response.setContent(result);
		EntityModel<Response> resource = EntityModel.of(response);
		


			resource.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UserDetailsController.class).getUserDetails(user.getUserId()))
            .withSelfRel());
		
//int id= user.getUserId();
		return 
				ResponseEntity.status(HttpStatus.CREATED)
//	            .created(WebMvcLinkBuilder.linkTo(
//	                WebMvcLinkBuilder.methodOn(UserDetailsController.class)
//	                .getUserDetails(id))
//	                .toUri())
	            .body(resource);

		
	}
	
	@GetMapping("/{id}") //@GetMapping("/Allusers")
	public ResponseEntity<Optional<UserClass>> getUserDetails(@PathVariable Integer id){
		Optional<UserClass> users = userdetailsService.getFindByUsers(id);
		
		//return userdetailsService.getAllUsers();
		return ResponseEntity.ok(users);
		
	}
	
	@PostMapping("/wallet")
	public ResponseEntity<EntityModel<Response>> AddAmount(@RequestBody AddAmountRequest request){
		
		String status = walletService.addAmount(request);
		Response response = new Response(status);
		//response.setContent(status);
		EntityModel<Response> resource = EntityModel.of(response);
				//return new ResponseEntity<>(status,HttpStatus.CREATED);
		resource.add(WebMvcLinkBuilder.linkTo(
	            WebMvcLinkBuilder.methodOn(UserDetailsController.class).viewBalance(request.getEmailId()))
	            .withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(resource);
		}
	
	@GetMapping("/{email}/wallet")
	public ResponseEntity<String> viewBalance(@PathVariable String email){
		AddAmountRequest request = new AddAmountRequest();
		request.setAction("WIEW");
		request.setEmailId(email);
		String status = walletService.addAmount(request);
		return new ResponseEntity<>(status,HttpStatus.CREATED);
	}
	
	
	@PostMapping("/transfer")
	public ResponseEntity<EntityModel<Response>> transferAmount(@RequestBody TransferAmount trequest){
		
		String status = walletService.transferAmount(trequest);
		Response response = new Response(status);
		//response.setContent(status);
		EntityModel<Response> resource = EntityModel.of(response);
				//return new ResponseEntity<>(status,HttpStatus.CREATED);
		resource.add(WebMvcLinkBuilder.linkTo(
	            WebMvcLinkBuilder.methodOn(UserDetailsController.class).viewBalance(trequest.getReceiverEmailId()))
	            .withSelfRel());
		return ResponseEntity.status(HttpStatus.CREATED).body(resource);
		//return new ResponseEntity<>(status,HttpStatus.OK);
	}
}
