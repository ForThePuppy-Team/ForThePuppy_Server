package com.example.demo.src.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDay {
    private int scheduleIdx;
    private int userIdx;
    private String scheduleContent;
    private String detail;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String categoryName;
    private int alert;
}
