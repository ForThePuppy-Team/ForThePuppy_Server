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

    public int createFamily(PostFamily postFamily){
        String createFamilyQuery = "insert into Family (userIdx, familyPassword) values (?, ?);\n";
        Object[] createFamilyParams = new Object[]{postFamily.getUserIdx(), postFamily.getFamilyPassword()};
        this.jdbcTemplate.update(createFamilyQuery, createFamilyParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int familyIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        String createFamilyMemberQuery = "insert into FamilyMember (familyIdx, userIdx, representative) values (?, ?, ?)";
        Object[] createFamilyMemberParams = new Object[]{familyIdx, postFamily.getUserIdx(), 1};
        this.jdbcTemplate.update(createFamilyMemberQuery, createFamilyMemberParams);

        return familyIdx;
    }

    public int createFamilyMember(PostFamilyAdd postFamilyAdd){

        String familyPassword = getFamilyPassword(postFamilyAdd.getFamilyIdx());
        if(!familyPassword.equals(postFamilyAdd.getFamilyPassword())){
            return 0;
        }

        String createFamilyMemberQuery = "insert into FamilyMember (familyIdx, userIdx) values (?, ?)";
        Object[] createFamilyMemberParams = new Object[]{postFamilyAdd.getFamilyIdx(), postFamilyAdd.getUserIdx()};
        this.jdbcTemplate.update(createFamilyMemberQuery, createFamilyMemberParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int deleteFamilyAccount(int familyIdx, int userIdx){
        String deleteFamilyAccount = "update Family set status = 0 where familyIdx = ? and userIdx = ?";
        Object[] deleteFamilyAccountParams = new Object[]{familyIdx, userIdx};
        this.jdbcTemplate.update(deleteFamilyAccount,deleteFamilyAccountParams);

        String deleteFamilyMember = "update FamilyMember set status = 0 where familyIdx = ?;\n";
        Object[] deleteFamilyMemberParams = new Object[]{familyIdx};
        return this.jdbcTemplate.update(deleteFamilyMember,deleteFamilyMemberParams);
    }

    public int deleteFamilyMember(int familyIdx, int userIdx){
        String deleteFamilyMember = "update FamilyMember set status = 0 where familyIdx = ? and userIdx = ?\n";
        Object[] deleteFamilyMemberParams = new Object[]{familyIdx, userIdx};
        return this.jdbcTemplate.update(deleteFamilyMember,deleteFamilyMemberParams);
    }

    public String getFamilyPassword(int familyIdx){
        String getUserQuery = "select familyPassword from Family where familyIdx = ?";
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new String(
                        rs.getString("familyPassword")),
                familyIdx);
    }

}
