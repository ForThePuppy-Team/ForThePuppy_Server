package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.src.chat.ChatDao;
import com.example.demo.src.chat.ChatProvider;
import com.example.demo.src.chat.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ChatService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatDao chatDao;
    private final ChatProvider chatProvider;
    private final JwtService jwtService;

    @Autowired
    public ChatService(ChatDao chatDao, ChatProvider chatProvider, JwtService jwtService) {
        this.chatDao = chatDao;
        this.chatProvider = chatProvider;
        this.jwtService = jwtService;
    }
}
