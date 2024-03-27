package com.example.BaiTest.repository;

import com.example.BaiTest.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {
}
