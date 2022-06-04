package com.example.demo.src.matching.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class GetMatchingWaiting {
    private int userIdx;
    private String profile;
    private String name;
    private String id;
    private String region;
    private int puppyIdx;
    private String puppyName;
    private String puppyPhoto;
    private int matchIdx;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private String location;
    private int accept;
}
