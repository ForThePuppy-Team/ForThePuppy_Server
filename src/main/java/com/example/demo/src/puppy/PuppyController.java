package com.example.demo.src.puppy;

import com.example.demo.src.post.model.GetPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.puppy.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/puppies")
public class PuppyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PuppyProvider puppyProvider;
    @Autowired
    private final PuppyService puppyService;
    @Autowired
    private final JwtService jwtService;

    public PuppyController(PuppyProvider puppyProvider, PuppyService puppyService, JwtService jwtService) {
        this.puppyProvider = puppyProvider;
        this.puppyService = puppyService;
        this.jwtService = jwtService;
    }

    /**
     * 반려견 등록 API
     * [POST] /puppies
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createPuppy(@RequestBody PostPuppyReq postPuppyReq) {
        try{
            int userIdx = postPuppyReq.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int puppyIdx = puppyService.createPuppy(postPuppyReq);
            return new BaseResponse<>(puppyIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 반려견 조회 API
     * [GET] /puppies/:userIdx
     * @return BaseResponse<List<GetPuppy>>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetPuppy>> getPuppy(@PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            List<GetPuppy> getPuppies = puppyProvider.getPuppies(userIdx);
            return new BaseResponse<>(getPuppies);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
