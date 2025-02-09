package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Projects;
import com.example.demo.repository.ProjectRepository;

@RestController
@RequestMapping("/projects")
public class ProjectsController {

	@Autowired
	private ProjectRepository projectRepository;

	@PostMapping("/create-project")
	public Projects createProject(@RequestBody Projects project) {
		Projects savedProject = projectRepository.save(project);
		return savedProject;
	}
}
