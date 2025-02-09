package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Logs;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Integer>{
	
}
