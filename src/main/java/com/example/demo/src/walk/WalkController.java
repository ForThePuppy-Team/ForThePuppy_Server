package com.example.demo.src.walk;

import com.example.demo.src.puppy.model.PostPuppyReq;
import com.example.demo.src.walk.model.PostWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/walks")
public class WalkController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final WalkProvider walkProvider;
    @Autowired
    private final WalkService walkService;
    @Autowired
    private final JwtService jwtService;

    public WalkController(WalkProvider walkProvider, WalkService walkService, JwtService jwtService) {
        this.walkProvider = walkProvider;
        this.walkService = walkService;
        this.jwtService = jwtService;
    }

    /**
     * 산책 등록 API
     * [POST] /walks
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createWalk(@RequestBody PostWalk postWalk) {
        try{
            int userIdx = postWalk.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int walkIdx = walkService.createWalk(postWalk);
            return new BaseResponse<>(walkIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
