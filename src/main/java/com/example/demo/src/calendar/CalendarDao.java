package com.example.demo.src.calendar;

import com.example.demo.src.calendar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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

}
