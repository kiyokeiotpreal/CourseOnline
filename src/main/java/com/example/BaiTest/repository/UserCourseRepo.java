package com.example.BaiTest.repository;

import com.example.BaiTest.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCourseRepo extends JpaRepository<UserCourse, Integer> {
}
