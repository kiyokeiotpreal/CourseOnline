package com.example.BaiTest.repository;

import com.example.BaiTest.model.Lesson;
import com.example.BaiTest.model.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepo extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByLessonType(LessonType lessonType);
    List<Lesson> findByNameContaining(String name);
    List<Lesson> findAllByLessonTypeId(int lessonType);

}
