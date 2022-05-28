package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.chat.ChatDao;
import com.example.demo.src.chat.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ChatProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatDao chatDao;
    private final JwtService jwtService;

    @Autowired
    public ChatProvider(ChatDao chatDao, JwtService jwtService) {
        this.chatDao = chatDao;
        this.jwtService = jwtService;
    }

    public List<GetChatRoom> getChatRoom(int userIdx) throws BaseException{
        try{
            List<GetChatRoom> getChatRoom = chatDao.getChatRoom(userIdx);
            return getChatRoom;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetChatMessage> getChatMessage(int userIdx, int roomIdx) throws BaseException{
        try{
            List<GetChatMessage> getChatMessage = chatDao.getChat(userIdx, roomIdx);
            return getChatMessage;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
