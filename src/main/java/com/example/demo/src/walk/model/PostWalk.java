package com.example.demo.src.walk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostWalk {
    private String date;
    private double totalDistance;
    private long totalTime;
    private int userIdx;
    private int puppyIdx;
}
