package com.example.demo.src.family;

import com.example.demo.src.family.FamilyProvider;
import com.example.demo.src.family.FamilyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.family.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/families")
public class FamilyController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FamilyProvider familyProvider;
    @Autowired
    private final FamilyService familyService;
    @Autowired
    private final JwtService jwtService;

    public FamilyController(FamilyProvider familyProvider, FamilyService familyService, JwtService jwtService){
        this.familyProvider = familyProvider;
        this.familyService = familyService;
        this.jwtService = jwtService;
    }

    /**
     * 가족계정 생성 API
     * [POST] /families/:userIdx
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<Integer> createFamily(@PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int familyIdx = familyService.createFamily(userIdx);
            return new BaseResponse<>(familyIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가족 등록 API
     * [POST] /families/:idx/:userIdx
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("/{idx}/{userIdx}")
    public BaseResponse<Integer> createFamilyMember(@PathVariable("idx") int familyIdx, @PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int memberIdx = familyService.createFamilyMember(familyIdx, userIdx);
            return new BaseResponse<>(memberIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가족 계정 삭제 API
     * [PATCH] /families/:idx/:userIdx/account-status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{idx}/{userIdx}/account-status")
    public BaseResponse<String> deleteFamilyAccount(@PathVariable("idx") int familyIdx, @PathVariable("userIdx") int userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            familyService.deleteFamilyAccount(familyIdx, userIdx);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
