package com.example.demo.src.calendar;

import com.example.demo.config.BaseException;
import com.example.demo.src.calendar.CalendarDao;
import com.example.demo.src.calendar.CalendarProvider;
import com.example.demo.src.calendar.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class CalendarService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CalendarDao calendarDao;
    private final CalendarProvider calendarProvider;
    private final JwtService jwtService;

    @Autowired
    public CalendarService(CalendarDao calendarDao, CalendarProvider calendarProvider, JwtService jwtService) {
        this.calendarDao = calendarDao;
        this.calendarProvider = calendarProvider;
        this.jwtService = jwtService;
    }

    //POST
    public int createSchedule(PostCalendar postCalendar) throws BaseException {
        try{
            int scheduleIdx = calendarDao.createSchedule(postCalendar);
            return scheduleIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
