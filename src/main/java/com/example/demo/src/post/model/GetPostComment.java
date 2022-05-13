package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostComment {
    private int userIdx;
    private String name;
    private String region;
    private String uploadTime;
    private String comment;
}
