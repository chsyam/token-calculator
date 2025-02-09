package com.example.demo.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NewUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String username;
	private int tokens_consumed;
	private int tokens_allocated;
	private double budget_utilized;

	@ElementCollection
	private List<String> projects;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTokens_consumed() {
		return tokens_consumed;
	}

	public void setTokens_consumed(int tokens_consumed) {
		this.tokens_consumed = tokens_consumed;
	}

	public int getTokens_allocated() {
		return tokens_allocated;
	}

	public void setTokens_allocated(int tokens_allocated) {
		this.tokens_allocated = tokens_allocated;
	}

	public double getBudget_utilized() {
		return budget_utilized;
	}

	public void setBudget_utilized(double budget_utilized) {
		this.budget_utilized = budget_utilized;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public NewUser(int id, String username, int tokens_consumed, int tokens_allocated, double budget_utilized,
			List<String> projects) {
		super();
		this.id = id;
		this.username = username;
		this.tokens_consumed = tokens_consumed;
		this.tokens_allocated = tokens_allocated;
		this.budget_utilized = budget_utilized;
		this.projects = projects;
	}

	public NewUser() {
		super();
	}

	@Override
	public String toString() {
		return "NewUser [id=" + id + ", username=" + username + ", tokens_consumed=" + tokens_consumed
				+ ", tokens_allocated=" + tokens_allocated + ", budget_utilized=" + budget_utilized + ", projects="
				+ projects + "]";
	}
}
