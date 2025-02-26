package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Models;
import com.example.demo.repository.ModelsRepository;

@RestController
@RequestMapping("/models")
public class ModelsController {

	@Autowired
	private ModelsRepository modelsRepository;

	@PostMapping("/create-model")
	public Models createModel(@RequestBody Models model) {
		Models savedModel = modelsRepository.save(model);
		return savedModel;
	}
}
