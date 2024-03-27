package com.example.BaiTest.services.iservices;


import com.example.BaiTest.dtos.Post.ApprovePostDto;
import com.example.BaiTest.model.ApprovePost;

import java.util.List;

public interface ApprovePostService {
    List<ApprovePost> getAllApprove();
    ApprovePostDto addApprovePost( int approvePostID, int admin_approvePost_Id );
}
