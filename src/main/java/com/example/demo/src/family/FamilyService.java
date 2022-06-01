package com.example.demo.src.family;

import com.example.demo.config.BaseException;
import com.example.demo.src.family.FamilyDao;
import com.example.demo.src.family.FamilyProvider;
import com.example.demo.src.family.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class FamilyService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FamilyDao familyDao;
    private final FamilyProvider familyProvider;
    private final JwtService jwtService;


    @Autowired
    public FamilyService(FamilyDao familyDao, FamilyProvider familyProvider, JwtService jwtService) {
        this.familyDao = familyDao;
        this.familyProvider = familyProvider;
        this.jwtService = jwtService;
    }

    //POST
    public int createFamily(PostFamily postFamily) throws BaseException {
        try{
            int familyIdx = familyDao.createFamily(postFamily);
            return familyIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    public int createFamilyMember(int familyIdx, int userIdx) throws BaseException {
        try{
            int memberIdx = familyDao.createFamilyMember(familyIdx, userIdx);
            return memberIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void deleteFamilyAccount(int familyIdx, int userIdx) throws BaseException {
        try{
            int result = familyDao.deleteFamilyAccount(familyIdx, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void deleteFamilyMember(int familyIdx, int userIdx) throws BaseException {
        try{
            int result = familyDao.deleteFamilyMember(familyIdx, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
