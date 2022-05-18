package com.example.demo.src.puppy;

import com.example.demo.src.post.model.GetPostAll;
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

    public List<GetPuppy> getPuppy(int userIdx){
        String getPuppyQuery = "select puppyIdx, puppyName, puppyPhoto, puppyGender, breed, birth, neutering, Vaccination, `character`\n" +
                "from Puppy\n" +
                "where status = 1 and userIdx = ?";
        int getPuppyParams = userIdx;
        return this.jdbcTemplate.query(getPuppyQuery,
                (rs, rowNum) -> new GetPuppy(
                        rs.getInt("puppyIdx"),
                        rs.getString("puppyName"),
                        rs.getString("puppyPhoto"),
                        rs.getString("puppyGender"),
                        rs.getString("breed"),
                        rs.getString("birth"),
                        rs.getInt("neutering"),
                        rs.getInt("Vaccination"),
                        rs.getString("character")
                ), getPuppyParams);
    }
}
