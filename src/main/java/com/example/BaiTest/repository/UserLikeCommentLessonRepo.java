package com.example.BaiTest.repository;

import com.example.BaiTest.model.CommentLesson;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserLikeCommentLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeCommentLessonRepo extends JpaRepository<UserLikeCommentLesson, Integer> {
    List<UserLikeCommentLesson> findAllByCommentLessonId(int commentLessonId);
    UserLikeCommentLesson findByCommentLessonAndUser(CommentLesson commentLesson, User user);

}
