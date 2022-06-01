package com.example.demo.src.walk;

import com.example.demo.src.walk.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WalkDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createWalk(PostWalk postWalk){
        String createWalkQuery = "insert into Walk (date, startTime, endTime, totalDistance, totalTime, userIdx, puppyIdx) values (?, ?, ?, ?, ?, ?, ?);\n";
        Object[] createWalkParams = new Object[]{postWalk.getDate(), postWalk.getStartTime(), postWalk.getEndTime(), postWalk.getTotalDistance(), postWalk.getTotalTime(), postWalk.getUserIdx(), postWalk.getPuppyIdx()};
        this.jdbcTemplate.update(createWalkQuery, createWalkParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }
}
