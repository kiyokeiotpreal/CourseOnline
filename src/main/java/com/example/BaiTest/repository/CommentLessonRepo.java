package com.example.BaiTest.repository;

import com.example.BaiTest.model.CommentLesson;
import com.example.BaiTest.model.Lesson;
import com.example.BaiTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentLessonRepo extends JpaRepository<CommentLesson, Integer> {
    List<CommentLesson> findAllByLessonLessonTypeId(int lessonTypeId);
    List<CommentLesson> findByLesson(Lesson lesson);
    List<CommentLesson> findByUser(User user);
    List<CommentLesson> findAllByUserId(int userId);
    List<CommentLesson> findAllByLessonId(int lessonId);

}
