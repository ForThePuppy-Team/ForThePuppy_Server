package com.example.demo.src.calendar;

import com.example.demo.src.calendar.CalendarProvider;
import com.example.demo.src.calendar.CalendarService;
import com.example.demo.src.chat.model.PostChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.calendar.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/calendars")
public class CalendarController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CalendarProvider calendarProvider;
    @Autowired
    private final CalendarService calendarService;
    @Autowired
    private final JwtService jwtService;

    public CalendarController(CalendarProvider calendarProvider, CalendarService calendarService, JwtService jwtService) {
        this.calendarProvider = calendarProvider;
        this.calendarService = calendarService;
        this.jwtService = jwtService;
    }

    /**
     * 일정 등록 API
     * [POST] /calendars
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createSchedule(@RequestBody PostCalendar postCalendar) {
        try{
            int userIdx = postCalendar.getUserIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int scheduleIdx = calendarService.createSchedule(postCalendar);
            return new BaseResponse<>(scheduleIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
