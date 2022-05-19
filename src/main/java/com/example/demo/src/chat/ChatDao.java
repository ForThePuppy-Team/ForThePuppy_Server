package com.example.demo.src.chat;

import com.example.demo.src.chat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChatDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createChatRoom(PostChatRoom postChatRoom){
        String createChatRoomQuery = "insert into ChatRoom (senderIdx, receiverIdx) values (?, ?);";
        Object[] createChatRoomParams = new Object[]{postChatRoom.getSenderIdx(), postChatRoom.getReceiverIdx()};
        this.jdbcTemplate.update(createChatRoomQuery, createChatRoomParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int roomIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        String createChatMessageQuery = "insert into ChatMessage (message, receiverIdx, senderIdx, roomIdx) values (?, ?, ?, ?);";
        Object[] createChatMessageParams = new Object[]{postChatRoom.getContent(), postChatRoom.getReceiverIdx(), postChatRoom.getSenderIdx(), roomIdx};
        this.jdbcTemplate.update(createChatMessageQuery, createChatMessageParams);

        return roomIdx;
    }
}
