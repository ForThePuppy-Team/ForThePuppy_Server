package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class chatContent {
    private int senderIdx;
    private int receiverIdx;
    private String message;
    private String time;
}
