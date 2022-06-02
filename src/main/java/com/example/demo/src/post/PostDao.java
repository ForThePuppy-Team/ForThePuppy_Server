package com.example.demo.src.post;

import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PostDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createPost(PostPostReq postPostReq){
        String createPostQuery = "insert into Post (title, content, category, userIdx) values (?, ?, ?, ?);\n";
        Object[] createPostParams = new Object[]{postPostReq.getTitle(), postPostReq.getContent(), postPostReq.getCategory(), postPostReq.getUserIdx()};
        this.jdbcTemplate.update(createPostQuery, createPostParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public List<GetPost> getPost(int postIdx){
        String getPostQuery = "select pc.categoryName categoryName,\n" +
                "       p.postIdx postIdx,\n" +
                "       u.userIdx userIdx,\n" +
                "       u.name name,\n" +
                "       u.profile profile,\n" +
                "       u.region region,\n" +
                "       case\n" +
                "           when (timestampdiff(minute, p.createAt, now()) < 1) then concat(timestampdiff(second, p.createAt, now()), '초', ' 전')\n" +
                "           when (timestampdiff(hour, p.createAt, now()) < 1) then concat(timestampdiff(minute, p.createAt, now()),'분', ' 전')\n" +
                "           when (timestampdiff(day, p.createAt, now()) < 1) then concat(timestampdiff(hour, p.createAt, now()), '시간', ' 전')\n" +
                "           when (timestampdiff(hour, p.createAt, now()) > 24) then concat(timestampdiff(day, p.createAt, now()), '일', ' 전')\n" +
                "           else concat(timestampdiff(month , p.createAt, now()),'달', ' 전') end as uploadTime,\n" +
                "       p.content content,\n" +
                "       date_format(p.createAt, '%Y-%m-%d') date,\n" +
                "       commentCount\n" +
                "from User u, Post p\n" +
                "   left join (\n" +
                "       select postIdx, commentIdx, count(commentIdx) as 'commentCount'\n" +
                "       from PostComment\n" +
                "    ) as x on p.postIdx = x.postIdx, PostCategory pc\n" +
                "where u.userIdx = p.userIdx\n" +
                "  and pc.categoryIdx = p.category\n" +
                "  and u.status = 1\n" +
                "  and p.status = 1\n" +
                "  and p.postIdx = ?";
        String getPostImageQuery = "select pi.image images \n" +
                "from Post p, PostImage pi\n" +
                "where p.postIdx = pi.postIdx\n" +
                "and p.status = 1\n" +
                "and pi.status = 1\n" +
                "and p.postIdx = ?";
        String getPostCommentQuery = "select u.userIdx userIdx,\n" +
                "       u.name name,\n" +
                "       u.region region,\n" +
                "       case\n" +
                "           when (timestampdiff(minute, pc.createAt, now()) < 1) then concat(timestampdiff(second, pc.createAt, now()), '초', ' 전')\n" +
                "           when (timestampdiff(hour, pc.createAt, now()) < 1) then concat(timestampdiff(minute, pc.createAt, now()),'분', ' 전')\n" +
                "           when (timestampdiff(day, pc.createAt, now()) < 1) then concat(timestampdiff(hour, pc.createAt, now()), '시간', ' 전')\n" +
                "           when (timestampdiff(hour, pc.createAt, now()) > 24) then concat(timestampdiff(day, pc.createAt, now()), '일', ' 전')\n" +
                "           else concat(timestampdiff(month , pc.createAt, now()),'달', ' 전') end as uploadTime,\n" +
                "       pc.comment comment\n" +
                "from User u, Post p, PostComment pc\n" +
                "where u.userIdx = pc.userIdx\n" +
                "  and pc.postIdx = p.postIdx\n" +
                "  and u.status = 1\n" +
                "  and p.status = 1\n" +
                "  and pc.status = 1\n" +
                "  and p.postIdx = ?";
        int getPostParams = postIdx;
        return this.jdbcTemplate.query(getPostQuery,
                (rs, rowNum) -> new GetPost(
                        rs.getString("categoryName"),
                        rs.getInt("postIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("profile"),
                        rs.getString("region"),
                        rs.getString("uploadTime"),
                        rs.getString("content"),
                        this.jdbcTemplate.query(getPostImageQuery, (rs1, rowNum1) -> new String(rs1.getString("images")), getPostParams),
                        this.jdbcTemplate.query(getPostCommentQuery, (rs2, rowNum2) -> new GetPostComment(
                                rs2.getInt("userIdx"),
                                rs2.getString("name"),
                                rs2.getString("region"),
                                rs2.getString("uploadTime"),
                                rs2.getString("comment")
                        ), getPostParams)
                ), getPostParams);
    }

    public int deletePost(int postIdx, int userIdx){
        String deletePost = "update Post set status = 0 where postIdx = ? and userIdx = ?;";
        Object[] deletePostParams = new Object[]{postIdx, userIdx};

        return this.jdbcTemplate.update(deletePost,deletePostParams);
    }

    public List<GetPostAll> getPostAll(){
        String getPostAllQuery = "select u.userIdx userIdx,\n" +
                "       p.postIdx postIdx,\n" +
                "       u.profile profile,\n" +
                "       u.name name,\n" +
                "       u.region region,\n" +
                "       case\n" +
                "           when (timestampdiff(minute, pc.createAt, now()) < 1) then concat(timestampdiff(second, pc.createAt, now()), '초', ' 전')\n" +
                "           when (timestampdiff(hour, pc.createAt, now()) < 1) then concat(timestampdiff(minute, pc.createAt, now()),'분', ' 전')\n" +
                "           when (timestampdiff(day, pc.createAt, now()) < 1) then concat(timestampdiff(hour, pc.createAt, now()), '시간', ' 전')\n" +
                "           when (timestampdiff(hour, pc.createAt, now()) > 24) then concat(timestampdiff(day, pc.createAt, now()), '일', ' 전')\n" +
                "           else concat(timestampdiff(month , pc.createAt, now()),'달', ' 전') end as uploadTime,\n" +
                "       p.title title,\n" +
                "       p.content content,\n" +
                "       pc.categoryName categoryName,\n" +
                "       commentCount,\n" +
                "       date_format(p.createAt, '%Y-%m-%d') date\n" +
                "from User u, Post p left join (\n" +
                "       select postIdx, commentIdx, count(commentIdx) as 'commentCount'\n" +
                "       from PostComment\n" +
                "    ) as x on p.postIdx = x.postIdx, PostCategory pc\n" +
                "where u.userIdx = p.postIdx\n" +
                "and p.category = pc.categoryIdx\n" +
                "and u.status = 1\n" +
                "and p.status = 1\n" +
                "and pc.status = 1\n";
        return this.jdbcTemplate.query(getPostAllQuery,
                (rs, rowNum) -> new GetPostAll(
                        rs.getInt("userIdx"),
                        rs.getInt("postIdx"),
                        rs.getString("profile"),
                        rs.getString("name"),
                        rs.getString("region"),
                        rs.getString("uploadTime"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("CategoryName"),
                        rs.getInt("commentCount"),
                        rs.getString("date")
                        ));
    }

    public int modifyPost(int postIdx, int userIdx, PatchPostReq patchPostReq){
        String modifyPostQuery = "update Post set title = ? , content = ? where postIdx = ? and userIdx = ? ";
        Object[] modifyPostParams = new Object[]{patchPostReq.getTitle(), patchPostReq.getContent(), postIdx, userIdx};

        return this.jdbcTemplate.update(modifyPostQuery,modifyPostParams);
    }
}
