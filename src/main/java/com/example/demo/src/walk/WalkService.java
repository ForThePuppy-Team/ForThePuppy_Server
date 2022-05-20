package com.example.demo.src.walk;

import com.example.demo.config.BaseException;
import com.example.demo.src.walk.WalkDao;
import com.example.demo.src.walk.WalkProvider;
import com.example.demo.src.walk.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class WalkService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WalkDao walkDao;
    private final WalkProvider walkProvider;
    private final JwtService jwtService;

    @Autowired
    public WalkService(WalkDao walkDao, WalkProvider walkProvider, JwtService jwtService) {
        this.walkDao = walkDao;
        this.walkProvider = walkProvider;
        this.jwtService = jwtService;
    }

    //POST
    public int createWalk(PostWalk postWalk) throws BaseException {
        try{
            int walkIdx = walkDao.createWalk(postWalk);
            return walkIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
