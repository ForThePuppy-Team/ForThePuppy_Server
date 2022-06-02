package com.example.demo.src.calendar;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.calendar.CalendarDao;
import com.example.demo.src.calendar.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class CalendarProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalendarDao calendarDao;
    private final JwtService jwtService;

    @Autowired
    public CalendarProvider(CalendarDao calendarDao, JwtService jwtService) {
        this.calendarDao = calendarDao;
        this.jwtService = jwtService;
    }

    public List<GetMonth> getMonth(int userIdx) throws BaseException{
     //   try{
            List<GetMonth> getMonth = calendarDao.getMonth(userIdx);
            return getMonth;
     //   }
     //   catch (Exception exception) {
     //       throw new BaseException(DATABASE_ERROR);
     //   }
    }
}
