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
     * [POST] /families
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createFamily(@RequestBody PostFamily postFamily) {
        try{
            int userIdx = postFamily.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int familyIdx = familyService.createFamily(postFamily);
            return new BaseResponse<>(familyIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가족 등록 API
     * [POST] /families/add
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<Integer> createFamilyMember(@RequestBody PostFamilyAdd postFamilyAdd) {
        try{
            int userIdx = postFamilyAdd.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int memberIdx = familyService.createFamilyMember(postFamilyAdd);
            if(memberIdx == 0){
                return new BaseResponse<>(FAILED_TO_FAMILY_ADD);
            }
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

    /**
     * 가족 삭제 API
     * [PATCH] /families/:idx/:userIdx/member-status
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{idx}/{userIdx}/member-status")
    public BaseResponse<String> deleteFamilyMember(@PathVariable("idx") int familyIdx, @PathVariable("userIdx") int userIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            familyService.deleteFamilyMember(familyIdx, userIdx);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 가족 조회 API
     * [GET] /families/:userOdx
     * @return BaseResponse<List<GetFamily>>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetFamily>> getFamily(@PathVariable("userIdx") int userIdx) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // Get Family
            List<GetFamily> getFamily = familyProvider.getFamily(userIdx);
            return new BaseResponse<>(getFamily);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
