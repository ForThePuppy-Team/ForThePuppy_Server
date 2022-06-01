package com.example.demo.src.matching;

import com.example.demo.src.matching.MatchingProvider;
import com.example.demo.src.matching.MatchingService;
import com.example.demo.src.post.model.GetPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.matching.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/matchings")
public class MatchingController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final MatchingProvider matchingProvider;
    @Autowired
    private final MatchingService matchingService;
    @Autowired
    private final JwtService jwtService;

    public MatchingController(MatchingProvider matchingProvider, MatchingService matchingService, JwtService jwtService) {
        this.matchingProvider = matchingProvider;
        this.matchingService = matchingService;
        this.jwtService = jwtService;
    }

    /**
     * 대리산책 등록 API
     * [POST] /matching
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createMatching(@RequestBody PostMatching postMatching) {
        try{
            int userIdx = postMatching.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int matchIdx = matchingService.createMatching(postMatching);
            return new BaseResponse<>(matchIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 대리산책 조회 API
     * [GET] /matchings/:userIdx
     * @return BaseResponse<List<GetMatchingList>>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetMatchingList>> getMatchingList(@PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // Get Posts
            List<GetMatchingList> getMatchingList = matchingProvider.getMatchingList(userIdx);
            return new BaseResponse<>(getMatchingList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 대리산책 삭제 API
     * [PATCH] /matchings/:idx/:userIdx/status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{idx}/{userIdx}/status")
    public BaseResponse<String> deletePost(@PathVariable("idx") int matchIdx, @PathVariable("userIdx") int userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            matchingService.deleteMatching(matchIdx, userIdx);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
