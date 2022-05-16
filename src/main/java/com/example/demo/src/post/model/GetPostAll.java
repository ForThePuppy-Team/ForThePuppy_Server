package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostAll {
    private int userIdx;
    private int postIdx;
    private String profile;
    private String name;
    private String region;
    private String uploadTime;
    private String title;
    private String content;
    private String CategoryName;
    private int commentCount;
    private String date;
}
