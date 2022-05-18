package com.example.demo.src.puppy;

import com.example.demo.src.puppy.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PuppyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPuppy(PostPuppyReq postPuppyReq){
        String createPuppyQuery = "insert into Puppy (puppyName, puppyPhoto, puppyGender, breed, birth, neutering, Vaccination, `character`, userIdx) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Object[] createPuppyParams = new Object[]{postPuppyReq.getPuppyName(), postPuppyReq.getPuppyPhoto(), postPuppyReq.getPuppyGender(), postPuppyReq.getBreed(), postPuppyReq.getBirth(),
                postPuppyReq.getNeutering(), postPuppyReq.getVaccination(), postPuppyReq.getCharacter(), postPuppyReq.getUserIdx()};
        this.jdbcTemplate.update(createPuppyQuery, createPuppyParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}
