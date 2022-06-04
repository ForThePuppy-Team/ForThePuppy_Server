package com.example.demo.src.matching;

import com.example.demo.config.BaseException;
import com.example.demo.src.matching.MatchingDao;
import com.example.demo.src.matching.MatchingProvider;
import com.example.demo.src.matching.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class MatchingService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MatchingDao matchingDao;
    private final MatchingProvider matchingProvider;
    private final JwtService jwtService;

    @Autowired
    public MatchingService(MatchingDao matchingDao, MatchingProvider matchingProvider, JwtService jwtService) {
        this.matchingDao = matchingDao;
        this.matchingProvider = matchingProvider;
        this.jwtService = jwtService;
    }

    //POST
    public int createMatching(PostMatching postMatching) throws BaseException {
        try{
            int matchIdx = matchingDao.createMatching(postMatching);
            return matchIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void deleteMatching(int matchIdx, int userIdx) throws BaseException {
        try{
            int result = matchingDao.deleteMatching(matchIdx, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    public void acceptMatching(int matchIdx, int userIdx) throws BaseException {
        try{
            int result = matchingDao.acceptMatching(matchIdx, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
