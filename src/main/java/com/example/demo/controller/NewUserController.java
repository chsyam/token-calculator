package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.NewUser;
import com.example.demo.repository.NewUserRepository;

@RestController
@RequestMapping("/users")
public class NewUserController {

	@Autowired
	private NewUserRepository newUserRepository;

	@PostMapping("/create-user")
	public NewUser createUser(@RequestBody NewUser user) {
		NewUser savedUser = newUserRepository.save(user);
		return savedUser;
	}
}
