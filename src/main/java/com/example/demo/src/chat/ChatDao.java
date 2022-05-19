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

    public int createChatMessage(int roomIdx, PostChatMessage postChatMessage){
        String createChatMessageQuery = "insert into ChatMessage (message, image, receiverIdx, senderIdx, roomIdx) values (?, ?, ?, ?, ?);";
        Object[] createChatMessageParams = new Object[]{postChatMessage.getMessage(), postChatMessage.getImage(), postChatMessage.getReceiverIdx(), postChatMessage.getSenderIdx(), roomIdx};
        this.jdbcTemplate.update(createChatMessageQuery, createChatMessageParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public List<GetChatRoom> getChatRoom(int userIdx){
        String getChatRoomQuery = "select cr.roomIdx roomIdx,\n" +
                "       u.userIdx userIdx,\n" +
                "       u.profile profile,\n" +
                "       u.name name,\n" +
                "       u.region region,\n" +
                "       case\n" +
                "           when (timestampdiff(minute, x.createAt, now()) < 1) then concat(timestampdiff(second, x.createAt, now()), '초', ' 전')\n" +
                "           when (timestampdiff(hour, x.createAt, now()) < 1) then concat(timestampdiff(minute, x.createAt, now()),'분', ' 전')\n" +
                "           when (timestampdiff(day, x.createAt, now()) < 1) then concat(timestampdiff(hour, x.createAt, now()), '시간', ' 전')\n" +
                "           when (timestampdiff(hour, x.createAt, now()) > 24) then concat(timestampdiff(day, x.createAt, now()), '일', ' 전')\n" +
                "           else concat(timestampdiff(month , cr.createAt, now()),'달', ' 전') end as uploadTime,\n" +
                "       x.message as lastMessage\n" +
                "from User u, ChatRoom cr\n" +
                "left join(\n" +
                "    select cm.message message, cm.roomIdx roomIdx, createAt\n" +
                "    from ChatMessage cm\n" +
                "    where(roomIdx, createAt) in (select roomIdx, max(createAt) from ChatMessage group by roomIdx) )as x on cr.roomIdx = x.roomIdx\n" +
                "where u.status = 1\n" +
                "and cr.status = 1\n" +
                "and ((cr.receiverIdx = ? and cr.senderIdx = u.userIdx) or (cr.senderIdx = ? and cr.receiverIdx = u.userIdx));";
        int getChatRoomParams = userIdx;
        return this.jdbcTemplate.query(getChatRoomQuery,
                (rs, rowNum) -> new GetChatRoom(
                        rs.getInt("roomIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("profile"),
                        rs.getString("name"),
                        rs.getString("region"),
                        rs.getString("uploadTime"),
                        rs.getString("lastMessage")
                ), getChatRoomParams, getChatRoomParams);
    }
}
