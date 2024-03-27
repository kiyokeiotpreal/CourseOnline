package com.example.BaiTest.repository;

import com.example.BaiTest.model.LearningExperience;
import com.example.BaiTest.model.Majors;
import com.example.BaiTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningExperienceRepo extends JpaRepository<LearningExperience, Integer> {
    List<LearningExperience> findByUser(User user);
    List<LearningExperience> findByMajors(Majors majors);

}
