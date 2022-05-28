package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetChatMessage {
    private int roomIdx;
    private int userIdx;
    private String profile;
    private String name;
    List<chatContent> chatContents;
}
