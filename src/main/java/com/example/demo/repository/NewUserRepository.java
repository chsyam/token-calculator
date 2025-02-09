package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.NewUser;
import com.google.common.base.Optional;

@Repository
public interface NewUserRepository extends JpaRepository<NewUser, Integer>{
	Optional<NewUser> findByUsername(String username);
}
