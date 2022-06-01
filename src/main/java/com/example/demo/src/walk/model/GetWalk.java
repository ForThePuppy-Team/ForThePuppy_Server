package com.example.demo.src.walk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class GetWalk {
    private int puppyIdx;
    private String puppyName;
    private String puppyGender;
    private String puppyPhoto;
    private String breed;
    private int walkIdx;
    private Date date;
    private Time startTime;
    private Time endTime;
    private double totalDistance;
    private long totalTime;
}
