package com.example.demo.src.post;

import com.example.demo.src.user.model.PatchUserReq;
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

    /**
     * 게시글 조회 API
     * [GET] /posts/:idx/:userIdx
     * @return BaseResponse<List<GetPost>>
     */
    @ResponseBody
    @GetMapping("/{idx}/{userIdx}")
    public BaseResponse<List<GetPost>> getPosts(@PathVariable("idx") int postIdx, @PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // Get Posts
            List<GetPost> getPostsRes = postProvider.getPosts(postIdx);
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시글 삭제 API
     * [PATCH] /posts/:idx/:userIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{idx}/{userIdx}/status")
    public BaseResponse<String> deletePost(@PathVariable("idx") int postIdx, @PathVariable("userIdx") int userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            postService.deletePost(postIdx, userIdx);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 전체 게시글 조회 API
     * [GET] /posts/:userIdx?regionName=
     * @return BaseResponse<List<GetPostAll>>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetPostAll>> getPostsAll(@PathVariable("userIdx") int userIdx, @RequestParam("regionName") String region) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // Get Post All
            List<GetPostAll> getPostAllRes = postProvider.getPostAll(region);
            return new BaseResponse<>(getPostAllRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 게시글 수정 API
     * [PATCH] /posts/:idx/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{idx}/{userIdx}")
    public BaseResponse<String> modifyPost(@PathVariable("idx") int postIdx, @PathVariable("userIdx") int userIdx, @RequestBody PatchPostReq patchPostReq){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            postService.modifyPost(postIdx, userIdx, patchPostReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
