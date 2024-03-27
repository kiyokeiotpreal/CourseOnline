package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.dtos.Post.ApprovePostDto;
import com.example.BaiTest.dtos.Post.PostDTO;
import com.example.BaiTest.dtos.Post.PostSentencesDTO;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.ApprovePost;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.model.PostSentence;
import com.example.BaiTest.repository.ApprovePostRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.services.iservices.ApprovePostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service
public class ApprovePostServiceimpl implements ApprovePostService {

    @Autowired
    private ApprovePostRepo approvePostRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<ApprovePost> getAllApprove() {
        return approvePostRepo.findAll();
    }

    @Override
    public ApprovePostDto addApprovePost( int approvePostID, int admin_approvePost_Id) {
        ApprovePost approvePost = this.approvePostRepo.findById(approvePostID).orElseThrow(()->
                new ResourceNotFoundException("ApprovePost", "ApprovePostId", (long)approvePostID));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        approvePost.setUpdateAt(timestamp);
        approvePost.setStatusPost("Đã phê duyệt nhé");
        approvePost.setUser(userRepo.findById(admin_approvePost_Id).orElse(null));
        ApprovePost updateApproveCourse = this.approvePostRepo.save(approvePost);

        return this.approvePostToDto(updateApproveCourse);
    }

//    public ApprovePostDto approvePostToDto(ApprovePost approvePost){
//        return modelMapper.map(approvePost, ApprovePostDto.class);
//    }
public ApprovePostDto approvePostToDto(ApprovePost approvePost){
    ApprovePostDto approvePostDto = new ApprovePostDto();

    // Chuyển đổi các thuộc tính chung
    approvePostDto.setStatusPost(approvePost.getStatusPost());
    approvePostDto.setCreateAt(approvePost.getCreateAt());
    approvePostDto.setUpdateAt(approvePost.getUpdateAt());

    // Chuyển đổi post thành postDTO
    PostDTO postDTO = new PostDTO();
    postDTO.setPostId(approvePost.getPost().getId());
    postDTO.setDescription(approvePost.getPost().getDescription());
    postDTO.setImagePost(approvePost.getPost().getImagePost());
    postDTO.setUserID(approvePost.getPost().getId());
    // Chuyển đổi danh sách postSentences thành postSentencesDTO
    List<PostSentencesDTO> postSentencesDTOList = new ArrayList<>();
    for (PostSentence postSentences : approvePost.getPost().getPostSentence()) {
        PostSentencesDTO postSentencesDTO = new PostSentencesDTO();
        postSentencesDTO.setImagePost(postSentences.getImagePost());
        postSentencesDTO.setContent(postSentences.getContent());
        postSentencesDTO.setImageTile(postSentences.getImageTile());
        postSentencesDTO.setSortNumber(postSentences.getSortNumber());
        // Thêm postSentencesDTO vào danh sách
        postSentencesDTOList.add(postSentencesDTO);
    }
    postDTO.setPostSentences(postSentencesDTOList);

    approvePostDto.setPostDTO(postDTO);

    return approvePostDto;
}
}
