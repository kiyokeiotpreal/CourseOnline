package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.CommentLessonDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.CommentLesson;
import com.example.BaiTest.model.Lesson;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserLikeCommentLesson;
import com.example.BaiTest.repository.CommentLessonRepo;
import com.example.BaiTest.repository.LessonRepo;
import com.example.BaiTest.repository.UserLikeCommentLessonRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.CommentLessonResponse;
import com.example.BaiTest.services.iservices.CommentLessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentLessonServiceImpl implements CommentLessonService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private CommentLessonRepo commentLessonRepo;

    @Autowired
    private UserLikeCommentLessonRepo userLikeCommentLessonRepo;

    @Override
    public CommentLessonDto createCommentLesson(CommentLessonDto commentLessonDto, int userId, int lessonId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId",(long) userId));
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("Lesson","lessonID", (long)lessonId));

        CommentLesson commentLesson = this.dtoToCommentLesson(commentLessonDto);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        commentLesson.setLikeCount(0);
        commentLesson.setDislikeCount(0);
        commentLesson.setCreateAt(currentTimestamp);
        commentLesson.setUpdateAt(null);
        commentLesson.setRemoveAt(null);

        commentLesson.setUser(user);
        commentLesson.setLesson(lesson);

        CommentLesson createCommentLesson = this.commentLessonRepo.save(commentLesson);
        return this.commentLessonToDto(createCommentLesson);
    }

    @Override
    public CommentLessonDto updateCommentLesson(CommentLessonDto commentLessonDto, int commentLessonId) {
        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId).orElseThrow(()-> new ResourceNotFoundException("CommentLesson","commentLessonID",(long)commentLessonId));

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        commentLesson.setUpdateAt(currentTimestamp);
        CommentLesson updateCommentLesson = this.commentLessonRepo.save(commentLesson);

        return this.commentLessonToDto(updateCommentLesson);
    }



    @Override
    public void deleteCommentLesson(int commentLessonId) {
        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId).orElseThrow(()-> new ResourceNotFoundException("CommentLesson","commentLessonID",(long)commentLessonId));
        for (UserLikeCommentLesson userLikeCommentLesson: userLikeCommentLessonRepo.findAll()){
            if (userLikeCommentLesson.getCommentLesson().getId() == commentLessonId){
                userLikeCommentLesson.setCommentLesson(null);
                userLikeCommentLesson.setUser(null);
                userLikeCommentLessonRepo.deleteById(userLikeCommentLesson.getId());
            }
        }
        commentLesson.setUser(null);
        commentLesson.setLesson(null);
        commentLessonRepo.delete(commentLesson);

    }

    @Override
    public CommentLessonDto getCommentLesson(int commentLessonId) {
        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId).orElseThrow(()-> new ResourceNotFoundException("CommentLesson","commentLessonID",(long)commentLessonId));

        return this.commentLessonToDto(commentLesson);
    }

    @Override
    public CommentLessonResponse getAllCommentLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<CommentLesson> pageCommentLesson = this.commentLessonRepo.findAll(pageable);

        List<CommentLesson> allCommentLessons = pageCommentLesson.getContent();

        List<CommentLessonDto> commentLessonDtos = allCommentLessons.stream().map(lesson -> this.commentLessonToDto(lesson)).collect(Collectors.toList());

        CommentLessonResponse commentLessonResponse = new CommentLessonResponse();
        commentLessonResponse.setContent(commentLessonDtos);
        commentLessonResponse.setPageNumber(pageCommentLesson.getNumber());
        commentLessonResponse.setPageSize(pageCommentLesson.getSize());
        commentLessonResponse.setTotalPage(pageCommentLesson.getTotalPages());
        commentLessonResponse.setTotalElement(pageCommentLesson.getTotalElements());
        commentLessonResponse.setLastPage(pageCommentLesson.isLast());

        return commentLessonResponse;
    }

    @Override
    public List<CommentLessonDto> getCommentLessonByUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId",(long) userId));
        List<CommentLesson> commentLessons = this.commentLessonRepo.findByUser(user);
        List<CommentLessonDto> commentLessonDtos = commentLessons.stream().map(commentLesson -> this.commentLessonToDto(commentLesson)).collect(Collectors.toList());
        return commentLessonDtos;
    }

    @Override
    public List<CommentLessonDto> getCommentLessonByLesson(int lessonId) {
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("Lesson","lessonID", (long)lessonId));
        List<CommentLesson> commentLessons = this.commentLessonRepo.findByLesson(lesson);
        List<CommentLessonDto> commentLessonDtos = commentLessons.stream().map(commentLesson -> this.commentLessonToDto(commentLesson)).collect(Collectors.toList());

        return commentLessonDtos;
    }

    @Override
    public CommentLessonDto handleLikeAction(int commentLessonId, boolean like, int userId) {
        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId)
                .orElseThrow(() -> new ResourceNotFoundException("CommentLesson", "commentLessonID", (long) commentLessonId));

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId",(long) userId));

        UserLikeCommentLesson existingLike = this.userLikeCommentLessonRepo.findByCommentLessonAndUser(commentLesson, user);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (existingLike != null) {
            // User has already liked or disliked the comment
            if (existingLike.isDeleted() && like) {
                // Change from dislike to like
                existingLike.setDeleted(false);
                commentLesson.setLikeCount(commentLesson.getLikeCount() + 1);
                commentLesson.setDislikeCount(commentLesson.getDislikeCount() - 1);
                commentLesson.setUpdateAt(currentTimestamp);
            } else if (!existingLike.isDeleted() && !like) {
                // Change from like to dislike
                existingLike.setDeleted(true);
                commentLesson.setLikeCount(commentLesson.getLikeCount() - 1);
                commentLesson.setDislikeCount(commentLesson.getDislikeCount() + 1);
                commentLesson.setUpdateAt(currentTimestamp);

            }
            this.userLikeCommentLessonRepo.save(existingLike);
        } else {
            UserLikeCommentLesson newLike = new UserLikeCommentLesson();
            newLike.setCommentLesson(commentLesson);
            newLike.setCreateAt(currentTimestamp);
            newLike.setUser(user);

            if (like) {
                newLike.setDeleted(false);
                commentLesson.setLikeCount(commentLesson.getLikeCount() + 1);
            } else {
                newLike.setDeleted(true);
                commentLesson.setDislikeCount(commentLesson.getDislikeCount() + 1);
            }

            this.userLikeCommentLessonRepo.save(newLike);
        }

        this.commentLessonRepo.save(commentLesson); // Lưu lại CommentLesson sau khi cập nhật giá trị

        return this.commentLessonToDto(commentLesson);
    }

//    @Override
//    public CommentLessonDto handleLikeAction(int commentLessonId, boolean like, long userId) {
//        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId)
//                .orElseThrow(() -> new ResourceNotFoundException("CommentLesson", "commentLessonID", (long) commentLessonId));
//
//        User user = this.userRepo.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
//
//        UserLikeCommentLesson existingLike = this.userLikeCommentLessonRepo.findByCommentLessonAndUser(commentLesson, user);
//
//        if (existingLike != null) {
//            // User has already liked or disliked the comment, no action required
//            return this.commentLessonToDto(commentLesson);
//        }
//
//        UserLikeCommentLesson newLike = new UserLikeCommentLesson();
//        newLike.setCommentLesson(commentLesson);
//        newLike.setUser(user);
//
//        if (like) {
//            newLike.setDeleted(false);
//            commentLesson.setLikeCount(commentLesson.getLikeCount() + 1);
//
//        } else {
//            newLike.setDeleted(true);
//            commentLesson.setLikeCount(commentLesson.getLikeCount() - 1);
//            commentLesson.setDislikeCount(commentLesson.getDislikeCount() + 1);
//
//        }
//
//        this.userLikeCommentLessonRepo.save(newLike);
//        this.commentLessonRepo.save(commentLesson); // Lưu lại CommentLesson sau khi cập nhật giá trị
//
//        return this.commentLessonToDto(commentLesson);
//    }

//
//
//    public CommentLesson toggleLike(CommentLesson commentLesson, int userId) {
//        if (commentLesson.getUser().getId() == userId) {
//            // User is the owner of the comment, remove like
//            commentLesson.setLikeCount(commentLesson.getLikeCount() > 0 ? commentLesson.getLikeCount() - 1 : 0);
//            commentLesson.getUser().setId(null);
//        } else {
//            // User is not the owner of the comment, toggle like
//            commentLesson.setLikeCount(commentLesson.getLikeCount() + 1);
//            commentLesson.setDislikeCount(commentLesson.getDislikeCount() > 0 ? commentLesson.getDislikeCount() - 1 : 0);
//            commentLesson.getUser().setId(userId);
//
//            // Remove dislike if present
//            commentLesson.getUserLikeCommentLesson().removeIf(likeComment -> likeComment.getUser().getId().equals(userId) && likeComment.isDeleted());
//            commentLesson.setDislikeCount(commentLesson.getDislikeCount() > 0 ? commentLesson.getDislikeCount() - 1 : 0);
//        }
//
//        return commentLessonRepo.save(commentLesson);
//    }
//
//    public CommentLesson togDislike(CommentLesson commentLesson, int userId) {
//        if (commentLesson.getDislikeCount() == 0 || commentLesson.getUser().getId() != userId) {
//            commentLesson.setDislikeCount(commentLesson.getDislikeCount() + 1);
//            commentLesson.setLikeCount(commentLesson.getLikeCount() > 0 ? commentLesson.getLikeCount() - 1 : 0);
//            commentLesson.getUser().setId(userId);
//        } else {
//            commentLesson.setDislikeCount(0);
//        }
//
//        return commentLessonRepo.save(commentLesson);
//    }

    public CommentLesson dtoToCommentLesson(CommentLessonDto commentLessonDto){
        return modelMapper.map(commentLessonDto, CommentLesson.class);
    }

    public CommentLessonDto commentLessonToDto(CommentLesson commentLesson){
        return modelMapper.map(commentLesson, CommentLessonDto.class);
    }

}
