package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * commentsテーブルを操作するレポジトリクラスです。
 * 
 * @author cyjoh
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);
	
	private final String TABLE_NAME = "comments";
	
	public List<Comment> findByArticleId(Integer articleId){
		String sql = "SELECT * FROM " + TABLE_NAME + " where article_id = :articleId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		return template.query(sql, param, COMMENT_ROW_MAPPER);
	}
}