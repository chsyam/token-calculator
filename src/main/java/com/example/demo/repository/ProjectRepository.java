package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Projects;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Integer> {
}
