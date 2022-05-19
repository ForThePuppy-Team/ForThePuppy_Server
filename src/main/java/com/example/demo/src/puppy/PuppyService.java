package com.example.demo.src.puppy;

import com.example.demo.config.BaseException;
import com.example.demo.src.puppy.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class PuppyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PuppyDao puppyDao;
    private final PuppyProvider puppyProvider;
    private final JwtService jwtService;

    @Autowired
    public PuppyService(PuppyDao puppyDao, PuppyProvider puppyProvider, JwtService jwtService) {
        this.puppyDao = puppyDao;
        this.puppyProvider = puppyProvider;
        this.jwtService = jwtService;
    }

    //POST
    public int createPuppy(PostPuppyReq postPuppyReq) throws BaseException {
        try{
            int puppyIdx = puppyDao.createPuppy(postPuppyReq);
            return puppyIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void deletePuppy(int puppyIdx, int userIdx) throws BaseException {
        try{
            int result = puppyDao.deletePuppy(puppyIdx, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void modifyPuppy(int userIdx, PatchPuppyReq patchPuppyReq) throws BaseException {
        try{
            int result = puppyDao.modifyPuppy(userIdx, patchPuppyReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
