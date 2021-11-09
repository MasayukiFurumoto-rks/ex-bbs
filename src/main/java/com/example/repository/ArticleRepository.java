package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template ;

	private final String TABLE_NAME = "articles";

	private final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
	
	public List<Article> findAll(){
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC";
		return template.query(sql, ARTICLE_ROW_MAPPER);
	}
	
	public void insert(Article article){
		String sql = "INSERT INTO " + TABLE_NAME + " (name,content) VALUES (:name , :content)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		
		template.update(sql, param);
	}
	
	public void delete(Integer id){
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = :id ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
