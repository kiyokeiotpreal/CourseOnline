package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.UserLessonCheckPointDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.Lesson;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserLessonCheckpoint;
import com.example.BaiTest.repository.LessonRepo;
import com.example.BaiTest.repository.UserLessonCheckpointRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.UserLessonCheckPointResponse;
import com.example.BaiTest.services.iservices.UserLessonCheckPointService;

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
public class UserLessonCheckPointServiceImpl implements UserLessonCheckPointService {

    @Autowired
    private UserLessonCheckpointRepo userLessonCheckpointRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LessonRepo lessonRepo;


    @Override
    public UserLessonCheckPointDto createUserLessonCheckpoint(UserLessonCheckPointDto userLessonCheckPointDto, int userId, int lessonId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userID",(long) userId));
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("lesson", "lessonID", (long) lessonId));

        UserLessonCheckpoint userLessonCheckpoint = this.dtoToUserLessonCheckpoint(userLessonCheckPointDto);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        userLessonCheckpoint.setOpenLessonTime(currentTimestamp);
        userLessonCheckpoint.setDone(false);

        userLessonCheckpoint.setUser(user);
        userLessonCheckpoint.setLesson(lesson);

        UserLessonCheckpoint createUserLessonCheckpoint = this.userLessonCheckpointRepo.save(userLessonCheckpoint);

        return this.userLessonCheckPointToDto(createUserLessonCheckpoint);
    }

    @Override
    public UserLessonCheckPointDto updateUserLessonCheckPoint(UserLessonCheckPointDto userLessonCheckPointDto, int userLessonCheckPointId) {
        UserLessonCheckpoint userLessonCheckpoint = this.userLessonCheckpointRepo.findById(userLessonCheckPointId).orElseThrow(()-> new ResourceNotFoundException("userLessonCheckPoint","UserLessonCheckPointID", (long)userLessonCheckPointId));
        userLessonCheckpoint.setDone(userLessonCheckPointDto.isDone());
        UserLessonCheckpoint updateUserLessonCheckpoint = this.userLessonCheckpointRepo.save(userLessonCheckpoint);

        return this.userLessonCheckPointToDto(updateUserLessonCheckpoint);
    }

    @Override
    public void deleteUserLessonCheckPoint(int userLessonCheckPointId) {
        UserLessonCheckpoint userLessonCheckpoint = this.userLessonCheckpointRepo.findById(userLessonCheckPointId).orElseThrow(()-> new ResourceNotFoundException("userLessonCheckPoint","UserLessonCheckPointID", (long)userLessonCheckPointId));

        userLessonCheckpoint.setUser(null);
        userLessonCheckpoint.setLesson(null);
        this.userLessonCheckpointRepo.deleteById(userLessonCheckPointId);

    }

    @Override
    public UserLessonCheckPointResponse getAllUserLessonCheckPoint(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<UserLessonCheckpoint> pageUserLessonCheckpoints = this.userLessonCheckpointRepo.findAll(pageable);

        List<UserLessonCheckpoint> allUserLessonCheckpoints = pageUserLessonCheckpoints.getContent();

        List<UserLessonCheckPointDto> userLessonCheckPointDtos = allUserLessonCheckpoints.stream().map(lesson -> this.userLessonCheckPointToDto(lesson)).collect(Collectors.toList());

        UserLessonCheckPointResponse userLessonCheckPointResponse = new UserLessonCheckPointResponse();
        userLessonCheckPointResponse.setContent(userLessonCheckPointDtos);
        userLessonCheckPointResponse.setPageNumber(pageUserLessonCheckpoints.getNumber());
        userLessonCheckPointResponse.setPageSize(pageUserLessonCheckpoints.getSize());
        userLessonCheckPointResponse.setTotalPage(pageUserLessonCheckpoints.getTotalPages());
        userLessonCheckPointResponse.setTotalElement(pageUserLessonCheckpoints.getTotalElements());
        userLessonCheckPointResponse.setLastPage(pageUserLessonCheckpoints.isLast());

        return userLessonCheckPointResponse;
    }

    public UserLessonCheckpoint dtoToUserLessonCheckpoint(UserLessonCheckPointDto userLessonCheckPointDto){
        return modelMapper.map(userLessonCheckPointDto, UserLessonCheckpoint.class);
    }

    public UserLessonCheckPointDto userLessonCheckPointToDto(UserLessonCheckpoint userLessonCheckpoint){
        return modelMapper.map(userLessonCheckpoint, UserLessonCheckPointDto.class);
    }

}
