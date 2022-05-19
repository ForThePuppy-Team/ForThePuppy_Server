package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRoom {
    private int roomIdx;
    private int userIdx;
    private String profile;
    private String name;
    private String region;
    private String uploadTime;
    private String lastMessage;
}
