package com.example.demo.src.walk;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.walk.WalkDao;
import com.example.demo.src.walk.model.*;
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
public class WalkProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WalkDao walkDao;
    private final JwtService jwtService;

    @Autowired
    public WalkProvider(WalkDao walkDao, JwtService jwtService) {
        this.walkDao = walkDao;
        this.jwtService = jwtService;
    }

    public List<GetWalk> getWalk(int userIdx) throws BaseException{
        try{
            List<GetWalk> getWalk = walkDao.getWalk(userIdx);
            return getWalk;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
