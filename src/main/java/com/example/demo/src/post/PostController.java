package com.example.demo.src.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/posts")
public class PostController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;

    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService) {
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    /**
     * 게시글 등록 API
     * [POST] /posts
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createPost(@RequestBody PostPostReq postPostReq) {
        try{
            int userIdx = postPostReq.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int postIdx = postService.createPost(postPostReq);
            return new BaseResponse<>(postIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
