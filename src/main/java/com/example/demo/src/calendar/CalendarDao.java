package com.example.demo.src.calendar;

import com.example.demo.src.calendar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class CalendarDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createSchedule(PostCalendar postCalendar){
        String createScheduleQuery = "insert into Calendar(scheduleContent, detail, startDate, endDate, startTime, endTime, alert, scheduleCategoryIdx, scheduleColor, userIdx)\n" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] createScheduleParams = new Object[]{
                postCalendar.getScheduleContent(),
                postCalendar.getDetail(),
                postCalendar.getStartDate(),
                postCalendar.getEndDate(),
                postCalendar.getStartTime(),
                postCalendar.getEndTime(),
                postCalendar.getAlert(),
                postCalendar.getScheduleCategoryIdx(),
                postCalendar.getScheduleColor(),
                postCalendar.getUserIdx()
        };
        this.jdbcTemplate.update(createScheduleQuery, createScheduleParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public List<GetMonth> getMonth(int userIdx){
        String getMonthQuery = "select c.scheduleIdx scheduleIdx,\n" +
                "       c.scheduleContent scheduleContent,\n" +
                "       c.startDate startDate,\n" +
                "       c.endDate endDate,\n" +
                "       c.scheduleColor scheduleColor\n" +
                "from Calendar c, scheduleCategory sc\n" +
                "where userIdx = ?\n" +
                "and c.scheduleCategoryIdx = sc.categoryIdx\n" +
                "and c.status = 1\n" +
                "order by startDate;";
        return this.jdbcTemplate.query(getMonthQuery,
                (rs, rowNum) -> new GetMonth(
                        rs.getInt("scheduleIdx"),
                        rs.getString("scheduleContent"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("scheduleColor")
                ), userIdx);
    }

    public List<GetDay> getDay(int scheduleIdx, int userIdx){
        String getDayQuery = "select c.scheduleIdx scheduleIdx,\n" +
                "       c.userIdx userIdx,\n" +
                "       c.scheduleContent scheduleContent,\n" +
                "       c.detail detail,\n" +
                "       c.startDate startDate,\n" +
                "       c.endDate endDate,\n" +
                "       c.startTime startTime,\n" +
                "       c.endTime endTime,\n" +
                "       sc.categoryName categoryName,\n" +
                "       c.alert alert\n" +
                "from Calendar c, scheduleCategory sc\n" +
                "where c.scheduleCategoryIdx = sc.categoryIdx\n" +
                "and scheduleIdx = ?\n" +
                "and userIdx = ?\n" +
                "and c.status = 1";
        return this.jdbcTemplate.query(getDayQuery,
                (rs, rowNum) -> new GetDay(
                        rs.getInt("scheduleIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("scheduleContent"),
                        rs.getString("detail"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("startTime"),
                        rs.getString("endTime"),
                        rs.getString("categoryName"),
                        rs.getInt("alert")
                ), scheduleIdx, userIdx);
    }

    public int deleteSchedule(int scheduleIdx, int userIdx){
        String deleteScheduleQuery = "update Calendar set status = 0 where scheduleIdx = ? and userIdx = ?;";
        Object[] deleteScheduleParams = new Object[]{scheduleIdx, userIdx};

        return this.jdbcTemplate.update(deleteScheduleQuery,deleteScheduleParams);
    }
}
