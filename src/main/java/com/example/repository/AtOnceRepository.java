package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.ArticleAndComment;

@Repository
public class AtOnceRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	private final RowMapper<ArticleAndComment> ARTICLE_AND_COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(ArticleAndComment.class);

	public List<ArticleAndComment> findAllAsJoin(){
		String sql = "SELECT a.id AS id,"
						  + "a.name AS name,"
						  + "a.content AS content,"
						  + "c.id AS com_id,"
						  + "c.name AS com_name,"
						  + "c.content AS com_content,"
						  + "c.article_id AS article_id"
					+ " FROM articles AS a LEFT JOIN comments AS c ON a.id = c.article_id ORDER BY id DESC , com_id desc;";
		//	実際のSQL
		//	SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name AS com_name,c.content AS com_content,c.article_id AS article_id　FROM articles AS a LEFT JOIN comments AS c ON a.id = c.article_id ORDER BY a.id ASC;
		return template.query(sql, ARTICLE_AND_COMMENT_ROW_MAPPER);

	}
}
