package com.example.demo.src.matching;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.matching.MatchingDao;
import com.example.demo.src.matching.model.*;
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
public class MatchingProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MatchingDao matchingDao;
    private final JwtService jwtService;

    @Autowired
    public MatchingProvider(MatchingDao matchingDao, JwtService jwtService) {
        this.matchingDao = matchingDao;
        this.jwtService = jwtService;
    }

    public List<GetMatchingList> getMatchingList(int userIdx) throws BaseException{
        try{
            List<GetMatchingList> getMatchingList = matchingDao.getMatchingList(userIdx);
            return getMatchingList;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
