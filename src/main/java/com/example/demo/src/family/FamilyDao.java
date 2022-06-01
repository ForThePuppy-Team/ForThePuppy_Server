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

    public List<GetFamily> getFamily(int userIdx){
        String getFamilyQuery = "select f.familyIdx familyIdx,\n" +
                "       DATE_FORMAT((f.createAt), '%Y년 %m월 %d일') as createDate,\n" +
                "       f.userIdx userIdx\n" +
                "from Family f, FamilyMember fm, User u\n" +
                "where f.familyIdx = fm.familyIdx\n" +
                "and f.userIdx = u.userIdx\n" +
                "and f.status = 1\n" +
                "and fm.status = 1\n" +
                "and u.status = 1\n" +
                "and fm.userIdx = ?;";
        String getFamilyMemebrQuery = "select u.userIdx userIdx,\n" +
                "       DATE_FORMAT((fm.createAt), '%Y년 %m월 %d일') as createDate,\n" +
                "       u.profile profile,\n" +
                "       u.name name,\n" +
                "       u.id id,\n" +
                "       u.region region\n" +
                "from FamilyMember fm, User u, Family f\n" +
                "where f.familyIdx = fm.familyIdx\n" +
                "and f.status = 1\n" +
                "and fm.status = 1\n" +
                "and u.status = 1\n" +
                "and fm.userIdx = ?;\n";
        int getFamilyParams = userIdx;
        return this.jdbcTemplate.query(getFamilyQuery,
                (rs, rowNum) -> new GetFamily(
                        rs.getInt("familyIdx"),
                        rs.getString("createDate"),
                        rs.getInt("userIdx"),
                        this.jdbcTemplate.query(getFamilyMemebrQuery, (rs1, rowNum1) -> new familyMember(
                                rs1.getInt("userIdx"),
                                rs1.getString("createDate"),
                                rs1.getString("profile"),
                                rs1.getString("name"),
                                rs1.getString("id"),
                                rs1.getString("region")
                                ), getFamilyParams)
                ), getFamilyParams);
    }

}
