package com.example.demo.src.family;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.family.FamilyDao;
import com.example.demo.src.family.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class FamilyProvider {

    private final FamilyDao familyDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FamilyProvider(FamilyDao familyDao, JwtService jwtService) {
        this.familyDao = familyDao;
        this.jwtService = jwtService;
    }

    public List<GetFamily> getFamily(int userIdx) throws BaseException{
        try{
            List<GetFamily> getFamily = familyDao.getFamily(userIdx);
            return getFamily;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
