package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Models {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String model_name;
    private int tokens_consumed;
    private int tokens_allocated;
    private double cost_per_token;
    private double budget_utilized;

    public int getId() {
        return id;
    }

    public String getModel_name() {
        return model_name;
    }

    public int getTokens_consumed() {
        return tokens_consumed;
    }

    public int getTokens_allocated() {
        return tokens_allocated;
    }

    public double getCost_per_token() {
        return cost_per_token;
    }

    public double getBudget_utilized() {
        return budget_utilized;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public void setTokens_consumed(int tokens_consumed) {
        this.tokens_consumed = tokens_consumed;
    }

    public void setTokens_allocated(int tokens_allocated) {
        this.tokens_allocated = tokens_allocated;
    }

    public void setCost_per_token(double cost_per_token) {
        this.cost_per_token = cost_per_token;
    }

    public void setBudget_utilized(double budget_utilized) {
        this.budget_utilized = budget_utilized;
    }

    public Models(int id, String model_name, int tokens_consumed, int tokens_allocated, double cost_per_token,
            double budget_utilized) {
        this.id = id;
        this.model_name = model_name;
        this.tokens_consumed = tokens_consumed;
        this.tokens_allocated = tokens_allocated;
        this.cost_per_token = cost_per_token;
        this.budget_utilized = budget_utilized;
    }

    public Models() {
    }

}