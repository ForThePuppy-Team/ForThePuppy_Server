package com.example.demo.src.family;

import com.example.demo.src.family.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FamilyDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createFamily(int userIdx){
        String createFamilyQuery = "insert into Family (userIdx) values (?);";
        Object[] createFamilyParams = new Object[]{userIdx};
        this.jdbcTemplate.update(createFamilyQuery, createFamilyParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int familyIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        String createFamilyMemberQuery = "insert into FamilyMember (familyIdx, userIdx, representative) values (?, ?, ?)";
        Object[] createFamilyMemberParams = new Object[]{familyIdx, userIdx, 1};
        this.jdbcTemplate.update(createFamilyMemberQuery, createFamilyMemberParams);

        return familyIdx;
    }

    public int createFamilyMember(int familyIdx, int userIdx){
        String createFamilyMemberQuery = "insert into FamilyMember (familyIdx, userIdx) values (?, ?)";
        Object[] createFamilyMemberParams = new Object[]{familyIdx, userIdx};
        this.jdbcTemplate.update(createFamilyMemberQuery, createFamilyMemberParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

}
