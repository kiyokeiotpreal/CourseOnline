package com.example.BaiTest.repository;

import com.example.BaiTest.model.Majors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorsRepo extends JpaRepository<Majors, Integer> {
}
