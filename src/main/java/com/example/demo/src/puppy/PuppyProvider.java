package com.example.demo.src.puppy;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.puppy.PuppyDao;
import com.example.demo.src.puppy.model.*;
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
public class PuppyProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PuppyDao puppyDao;
    private final JwtService jwtService;

    @Autowired
    public PuppyProvider(PuppyDao puppyDao, JwtService jwtService) {
        this.puppyDao = puppyDao;
        this.jwtService = jwtService;
    }

    public List<GetPuppy> getPuppies(int userIdx) throws BaseException{
        try{
            List<GetPuppy> getPuppies = puppyDao.getPuppy(userIdx);
            return getPuppies;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
