package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost(PostPostReq postPostReq){
        String createPostQuery = "insert into Post (title, content, category, userIdx) values (?, ?, ?, ?);\n";
        Object[] createPostParams = new Object[]{postPostReq.getTitle(), postPostReq.getContent(), postPostReq.getCategory(), postPostReq.getUserIdx()};
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
