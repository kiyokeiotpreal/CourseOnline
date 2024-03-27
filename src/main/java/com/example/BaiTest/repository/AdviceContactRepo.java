package com.example.BaiTest.repository;

import com.example.BaiTest.model.AdviceContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviceContactRepo extends JpaRepository<AdviceContact, Integer> {
}
