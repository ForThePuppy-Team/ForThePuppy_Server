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

    public List<GetWalk> getWalk(int userIdx){
        String getWalkQuery = "select p.puppyIdx puppyIdx,\n" +
                "       p.puppyName puppyName,\n" +
                "       p.puppyGender puppyGender,\n" +
                "       p.puppyPhoto puppyPhoto,\n" +
                "       p.breed breed,\n" +
                "       w.walkIdx walkIdx,\n" +
                "       w.date date,\n" +
                "       w.startTime startTime,\n" +
                "       w.endTime endTime,\n" +
                "       w.totalDistance totalDistance,\n" +
                "       w.totalTime totalTime\n" +
                "from Puppy p, Walk w\n" +
                "where w.userIdx = ?\n" +
                "and p.puppyIdx = w.puppyIdx\n" +
                "and w.status = 1";
        int getWaldParams = userIdx;
        return this.jdbcTemplate.query(getWalkQuery,
                (rs, rowNum) -> new GetWalk(
                        rs.getInt("puppyIdx"),
                        rs.getString("puppyName"),
                        rs.getString("puppyGender"),
                        rs.getString("puppyPhoto"),
                        rs.getString("breed"),
                        rs.getInt("walkIdx"),
                        rs.getDate("date"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getDouble("totalDistance"),
                        rs.getLong("totalTime")
                ), getWaldParams);
    }
}
