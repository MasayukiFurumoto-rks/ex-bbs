package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class AtOnceRepositoryBetter {

	@Autowired
	private NamedParameterJdbcTemplate template;

	public ResultSetExtractor<List<Article>> ARTICLE_LIST_EXTRACTOR = (rs) -> {
		Map<Integer, Article> map = new LinkedHashMap<>();
		Article article = null;

		while (rs.next()) {
			Integer articleId = rs.getInt("id");

			article = map.get(articleId);

			if (article == null) {
				article = new Article();
				article.setId(articleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				List<Comment> commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				map.put(articleId, article);
			}

			Integer comId = rs.getInt("com_id");
			if (comId != 0) {
				Comment comment = new Comment();
				comment.setId(comId);
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				comment.setArticleId(articleId);

				
				article.getCommentList().add(comment);
			}
		}

		if (map.size() == 0) {
			throw new EmptyResultDataAccessException(1);
		}

		return new ArrayList<Article>(map.values());
	};

	public List<Article> findAllAsJoin() {
		String sql = "SELECT a.id AS id," + "a.name AS name," + "a.content AS content," + "c.id AS com_id,"
				+ "c.name AS com_name," + "c.content AS com_content," + "c.article_id AS article_id"
				+ " FROM articles AS a LEFT JOIN comments AS c ON a.id = c.article_id ORDER BY id DESC , com_id desc;";
		// 実際のSQL
//		 SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name
//		 AS com_name,c.content AS com_content,c.article_id AS article_id FROM articles
//		 AS a LEFT JOIN comments AS c ON a.id = c.article_id ORDER BY a.id ASC;
		return template.query(sql, ARTICLE_LIST_EXTRACTOR);

	}
}
