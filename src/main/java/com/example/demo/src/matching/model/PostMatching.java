package com.example.demo.src.matching.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class PostMatching {
    private int userIdx;
    private int careIdx;
    private int puppyIdx;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private String location;
}
