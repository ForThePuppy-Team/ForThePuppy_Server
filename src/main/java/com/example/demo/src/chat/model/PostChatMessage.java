package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostChatMessage {
    private String message;
    private String image;
    private int receiverIdx;
    private int senderIdx;
}
