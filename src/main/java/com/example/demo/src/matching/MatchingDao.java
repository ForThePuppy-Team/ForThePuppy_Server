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

    public List<GetMatchingList> getMatchingList(int userIdx){
        String getMatchingListQuery = "select u.userIdx userIdx,\n" +
                "       u.profile profile,\n" +
                "       u.name name,\n" +
                "       u.id id,\n" +
                "       u.region region,\n" +
                "       p.puppyIdx puppyIdx,\n" +
                "       p.puppyName puppyName,\n" +
                "       p.puppyGender puppyGender,\n" +
                "       p.puppyPhoto puppyPhoto,\n" +
                "       p.breed breed,\n" +
                "       m.matchIdx matchIdx,\n" +
                "       m.startDate startDate,\n" +
                "       m.endDate endDate,\n" +
                "       m.startTime startTime,\n" +
                "       m.endTime endTime,\n" +
                "       m.location location,\n" +
                "       m.accept accept\n" +
                "from User u, Puppy p, Matching m\n" +
                "where m.careIdx = u.userIdx\n" +
                "and p.puppyIdx = m.puppyIdx\n" +
                "and m.status = 1\n" +
                "and p.status = 1\n" +
                "and m.accept = 1\n" +
                "and (m.userIdx = ? or m.careIdx = ?);";
        int getMatchingListParams = userIdx;
        return this.jdbcTemplate.query(getMatchingListQuery,
                (rs, rowNum) -> new GetMatchingList(
                        rs.getInt("userIdx"),
                        rs.getString("profile"),
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("region"),
                        rs.getInt("puppyIdx"),
                        rs.getString("puppyName"),
                        rs.getString("puppyGender"),
                        rs.getString("puppyPhoto"),
                        rs.getString("breed"),
                        rs.getInt("matchIdx"),
                        rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getTime("startTime"),
                        rs.getTime("endTime"),
                        rs.getString("location"),
                        rs.getInt("accept")
                ), getMatchingListParams, getMatchingListParams);
    }

    public int deleteMatching(int matchIdx, int userIdx){
        String deleteMatching = "update Matching set status = 0 where matchIdx = ? and userIdx = ?;";
        Object[] deleteMatchingParams = new Object[]{matchIdx, userIdx};

        return this.jdbcTemplate.update(deleteMatching,deleteMatchingParams);
    }
}
