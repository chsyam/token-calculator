package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Logs;
import com.example.demo.entity.Models;
import com.example.demo.entity.NewUser;
import com.example.demo.entity.Projects;
import com.example.demo.repository.LogsRepository;
import com.example.demo.repository.ModelsRepository;
import com.example.demo.repository.NewUserRepository;
import com.example.demo.repository.ProjectRepository;
import com.google.common.base.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
	@Autowired
	private NewUserRepository newUserRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private LogsRepository logsRepository;

	@Autowired
	private ModelsRepository modelsRepository;

	public Logs validatePrompt(String username, String projectName, String query, int tokens, String model_name) {
		try {
			String url = "http://10.63.16.153:31737/user/details?accessKey=ddca7447-9105-4d1d-b5ba-fdf1eb89519d";
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");

			HttpEntity<String> requestEntity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

			System.out.println(response.getBody());
		} catch (Exception e) {
			System.out.println("Error while calling the model api" + e.getMessage());
		}

		Logs newLog = new Logs();
		newLog.setProject_name(projectName);
		newLog.setModel_name(model_name);
		newLog.setUsername(username);
		newLog.setPrompt(query);
		newLog.setToken_count(tokens);

		Optional<NewUser> optionalUser = newUserRepository.findByUsername(username);
		NewUser user = optionalUser.get();
		List<String> projects_list = user.getProjects();

		List<String> models_list = user.getModels();

		if (models_list.indexOf(model_name) == -1) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("User is not allowed to use that model");
			return logsRepository.save(newLog);
		}

		if (projects_list.indexOf(projectName) == -1) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("User is not allowed to use that project");
			return logsRepository.save(newLog);
		}

		if (user.getTokens_consumed() + tokens > user.getTokens_allocated()) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("user's token balance is not sufficient");
			return logsRepository.save(newLog);
		}

		List<Projects> result = projectRepository.findAll();
		List<Projects> filteredProjects = result.stream()
				.filter(p -> p.getProject_name().toLowerCase().contains(projectName)).collect(Collectors.toList());

		if (filteredProjects.size() == 0) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("invalid project name");
			return logsRepository.save(newLog);
		}

		Projects project = filteredProjects.get(0);
		if (project.getTokens_consumed() + tokens > project.getTokens_allocated()) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("Project's token balance is not sufficient");
			return logsRepository.save(newLog);
		}

		List<Models> result_models = modelsRepository.findAll();
		List<Models> filteredModels = result_models.stream()
				.filter(p -> p.getModel_name().toLowerCase().contains(model_name)).collect(Collectors.toList());

		if (filteredModels.size() == 0) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("invalid model name");
			return logsRepository.save(newLog);
		}

		Models model = filteredModels.get(0);
		if (model.getTokens_consumed() + tokens > model.getTokens_allocated()) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("Project's token balance is not sufficient");
			return logsRepository.save(newLog);
		}

		double amount_required = project.getCost_per_token() * tokens;
		newLog.setAmount_required(amount_required);
		newLog.setStatus("Success");
		newLog.setError_message("NA");

		System.out.println("Logs table updated...!");

		user.setBudget_utilized(user.getBudget_utilized() + amount_required);
		user.setTokens_consumed(user.getTokens_consumed() + tokens);
		newUserRepository.save(user);

		System.out.println("user table updated...!");

		model.setTokens_consumed(model.getTokens_consumed() + tokens);
		model.setBudget_utilized(model.getBudget_utilized() + amount_required);
		modelsRepository.save(model);

		project.setTokens_consumed(project.getTokens_consumed() + tokens);
		project.setBudget_utilized(project.getBudget_utilized() + amount_required);
		projectRepository.save(project);

		try {
			String url = "http://10.63.20.115:8001/v1/chat/completions";
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("X-Access-Key", "ddca7447-9105-4d1d-b5ba-fdf1eb89519d");

			String requestBody = "{\"prompt\": \"" + query + "\"}";

			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

			System.out.println(response.getBody());
		} catch (Exception e) {
			System.out.println("Error while calling the model api" + e.getMessage());
		}
		return logsRepository.save(newLog);
	}
}
