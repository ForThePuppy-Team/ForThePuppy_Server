package com.example.demo.src.walk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
public class PostWalk {
    private String date;
    private String startTime;
    private String endTime;
    private double totalDistance;
    private long totalTime;
    private int userIdx;
    private int puppyIdx;
}
