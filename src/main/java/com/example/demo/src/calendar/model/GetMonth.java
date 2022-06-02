package com.example.demo.src.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetMonth {
    private int scheduleIdx;
    private String scheduleContent;
    private String startDate;
    private String endDate;
    private String scheduleColor;
}
