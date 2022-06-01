package com.example.demo.src.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCalendar {
    private String scheduleContent;
    private String detail;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private int alert;
    private int scheduleCategoryIdx;
    private String scheduleColor;
    private int userIdx;
}
