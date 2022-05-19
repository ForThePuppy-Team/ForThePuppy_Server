package com.example.demo.src.chat;

import com.example.demo.src.chat.ChatProvider;
import com.example.demo.src.chat.ChatService;
import com.example.demo.src.post.model.PostPostReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.chat.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/chats")
public class ChatController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ChatProvider chatProvider;
    @Autowired
    private final ChatService chatService;
    @Autowired
    private final JwtService jwtService;

    public ChatController(ChatProvider chatProvider, ChatService chatService, JwtService jwtService) {
        this.chatProvider = chatProvider;
        this.chatService = chatService;
        this.jwtService = jwtService;
    }

    /**
     * 채팅방 생성 API
     * [POST] /chats
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<Integer> createChat(@RequestBody PostChatRoom postChatRoom) {
        try{
            int userIdx = postChatRoom.getSenderIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int roomIdx = chatService.createChatRoom(postChatRoom);
            return new BaseResponse<>(roomIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채팅 메세지 생성 API
     * [POST] /chats/:roomIdx/message
     * @return BaseResponse<Integer>
     */
    // Body
    @ResponseBody
    @PostMapping("/{roomIdx}/message")
    public BaseResponse<Integer> createChatMessage(@PathVariable("roomIdx") int roomIdx, @RequestBody PostChatMessage postChatMessage) {
        try{
            int userIdx = postChatMessage.getSenderIdx();
            int userIdxByJwt = jwtService.getUserIdx();

            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            int messageIdx = chatService.createChatMessage(roomIdx, postChatMessage);
            return new BaseResponse<>(messageIdx);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
