package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Logs;
import com.example.demo.service.TokenService;

@RestController
@RequestMapping("/tokens")
public class TokenValidation {
	@Autowired
	private TokenService tokenService;

	@PostMapping("/validate")
	public Logs promptValidation(@RequestParam String query) {
		int token_count = query.split(" ").length;
		Logs response = tokenService.validatePrompt("yogesh", "platform-engineering", query, token_count, "codellama");
		return response;
	}
}
