package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.UserCourseDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserCourse;
import com.example.BaiTest.repository.CourseRepo;
import com.example.BaiTest.repository.UserCourseRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.UserCourseResponse;
import com.example.BaiTest.services.iservices.UserCourseService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserCourseRepo userCourseRepo;

    @Override
    public UserCourseDto createUserCourse(UserCourseDto userCourseDto, int userId, int courseId ) {
        Course course = this.courseRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("course","courseId", (long)courseId));
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userId",(long) userId));

        UserCourse userCourse = this.dtoToUserCourse(userCourseDto);

        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        // Cập nhật lastTimeStudy và registerTime với thời gian hiện tại
        userCourse.setLastTimeStudy(currentTimestamp);
        userCourse.setRegisterTime(currentTimestamp);

        // Cộng thêm 30 ngày
        calendar.setTime(userCourse.getLastTimeStudy());
        calendar.add(Calendar.DATE, 30);
        Timestamp doneTimeTimestamp = new Timestamp(calendar.getTimeInMillis());

        // Cập nhật doneTime
        userCourse.setDoneTime(doneTimeTimestamp);

        userCourse.setDone(false);

        userCourse.setCourse(course);
        userCourse.setUser(user);
        course.setRegisterCount(course.getRegisterCount() +1);

        UserCourse createUserCourse = this.userCourseRepo.save(userCourse);

        return this.userCourseToDto(createUserCourse);
    }



    @Override
    public UserCourseDto updateUserCourse(UserCourseDto userCourseDto, int userCourseId) {
        UserCourse userCourse = this.userCourseRepo.findById(userCourseId).orElseThrow(()-> new ResourceNotFoundException("userCourse","userCourseId", (long)userCourseId));

        Timestamp registerTimeDate = userCourse.getRegisterTime();
        Timestamp lastTimeStudy = userCourse.getLastTimeStudy();
        Timestamp doneTime = userCourse.getDoneTime();


        userCourse.setLastTimeStudy(userCourseDto.getLastTimeStudy());
        userCourse.setDoneTime(userCourseDto.getDoneTime());
        userCourse.setDone(userCourseDto.isDone());

        UserCourse updateUserCourse = this.userCourseRepo.save(userCourse);

        return this.userCourseToDto(updateUserCourse);
    }

    @Override
    public void deleteUserCourse(int userCourseId) {
        UserCourse userCourse = this.userCourseRepo.findById(userCourseId).orElseThrow(()-> new ResourceNotFoundException("userCourse","userCourseId", (long)userCourseId));
        for(Course course: this.courseRepo.findAll()){
            if(userCourse.getCourse().getId() == course.getId()){
                course.setRegisterCount(course.getRegisterCount() - 1);
                this.courseRepo.save(course);
            }
        }

        this.userCourseRepo.delete(userCourse);

    }

    @Override
    public UserCourseResponse getAllUserCourse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<UserCourse> pageUserCourse = this.userCourseRepo.findAll(pageable);

        List<UserCourse> allUserCourse = pageUserCourse.getContent();

        List<UserCourseDto> userCourseDtos = allUserCourse.stream().map(userCourse -> this.userCourseToDto(userCourse)).collect(Collectors.toList());

        UserCourseResponse userCourseResponse = new UserCourseResponse();
        userCourseResponse.setContent(userCourseDtos);
        userCourseResponse.setPageNumber(pageUserCourse.getNumber());
        userCourseResponse.setPageSize(pageUserCourse.getSize());
        userCourseResponse.setTotalElement(pageUserCourse.getTotalElements());
        userCourseResponse.setTotalPage(pageUserCourse.getTotalPages());
        userCourseResponse.setLastPage(pageUserCourse.isLast());

        return userCourseResponse;
    }

    public UserCourse dtoToUserCourse(UserCourseDto userCourseDto){
        return modelMapper.map(userCourseDto, UserCourse.class);
    }

    public UserCourseDto userCourseToDto(UserCourse userCourse){
        return modelMapper.map(userCourse, UserCourseDto.class);
    }

}
