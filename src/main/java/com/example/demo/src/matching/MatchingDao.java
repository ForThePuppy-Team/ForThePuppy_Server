package com.example.demo.src.matching;

import com.example.demo.src.matching.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MatchingDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createMatching(PostMatching postMatching){
        String createMatchQuery = "insert into Matching (userIdx, careIdx, puppyIdx, startDate, endDate, startTime, endTime, location) values (?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] createMatchParams = new Object[]{postMatching.getUserIdx(), postMatching.getCareIdx(), postMatching.getPuppyIdx(), postMatching.getStartDate(), postMatching.getEndDate(), postMatching.getStartTime(), postMatching.getEndTime(), postMatching.getLocation()};
        this.jdbcTemplate.update(createMatchQuery, createMatchParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
