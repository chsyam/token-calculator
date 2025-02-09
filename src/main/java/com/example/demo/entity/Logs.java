package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Logs {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String prompt;
	private int token_count;
	private double amount_required;
	private String username;
	private String project_name;
	private String status;
	private String error_message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public int getToken_count() {
		return token_count;
	}

	public void setToken_count(int token_count) {
		this.token_count = token_count;
	}

	public double getAmount_required() {
		return amount_required;
	}

	public void setAmount_required(double amount_required) {
		this.amount_required = amount_required;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Logs(int id, String prompt, int token_count, double amount_required, String username, String project_name,
			String status, String error_message) {
		super();
		this.id = id;
		this.prompt = prompt;
		this.token_count = token_count;
		this.amount_required = amount_required;
		this.username = username;
		this.project_name = project_name;
		this.status = status;
		this.error_message = error_message;
	}

	public Logs() {
		super();
	}
}
