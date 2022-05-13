package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPost {
    private String categoryName;
    private int postIdx;
    private int userIdx;
    private String name;
    private String profile;
    private String region;
    private String uploadTime;
    private String content;
    private List<String> images;
    private List<GetPostComment> getPostComments;
}

