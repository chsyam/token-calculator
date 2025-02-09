package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Logs;
import com.example.demo.entity.NewUser;
import com.example.demo.entity.Projects;
import com.example.demo.repository.LogsRepository;
import com.example.demo.repository.NewUserRepository;
import com.example.demo.repository.ProjectRepository;
import com.google.common.base.Optional;

@Service
public class TokenService {
	@Autowired
	private NewUserRepository newUserRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private LogsRepository logsRepository;

	public Logs validatePrompt(String username, String projectName, String query, int tokens) {
		Logs newLog = new Logs();
		newLog.setProject_name(projectName);
		newLog.setUsername(username);
		newLog.setPrompt(query);
		newLog.setToken_count(tokens);

		Optional<NewUser> optionalUser = newUserRepository.findByUsername(username);
		NewUser user = optionalUser.get();
		List<String> projects_list = user.getProjects();

		if (projects_list.indexOf(projectName) == -1) {
			newLog.setAmount_required(0);
			newLog.setStatus("Failed");
			newLog.setError_message("User is not part of that project");
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
				.filter(p -> p.getProject_name().toLowerCase().contains("platform")).collect(Collectors.toList());

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

		double amount_required = project.getCost_per_token() * tokens;
		newLog.setAmount_required(amount_required);
		newLog.setStatus("Success");
		newLog.setError_message("Sufficent tokens");

		System.out.println("Logs table updated...!");

		user.setBudget_utilized(user.getBudget_utilized() + amount_required);
		user.setTokens_consumed(user.getTokens_consumed() + tokens);
		newUserRepository.save(user);

		System.out.println("user table updated...!");

		project.setTokens_consumed(project.getTokens_consumed() + tokens);
		project.setBudget_utilized(project.getBudget_utilized() + amount_required);
		projectRepository.save(project);

		return logsRepository.save(newLog);
	}
}
