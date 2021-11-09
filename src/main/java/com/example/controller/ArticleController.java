package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;


@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	@RequestMapping("")
	public String index(Model model){
		List<Article> articleList = articleRepository.findAll();
		//for分で全部取り出して、中にcommentListを詰めていく…？
		for(Article article:articleList) {
			article.setCommentList(commentRepository.findByArticleId(article.getId()));
		}
		model.addAttribute("articleList", articleList);
		
		return "bbs";
	}
	
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		
		return "redirect:/ex-bbs";
	}
	
	@RequestMapping("/delete-article")
	public String deleteArticle(Integer id) {
		articleRepository.delete(id);
	
		//レポジトリではarticle_idとして受け取っています
		commentRepository.delete(id);
		
		return "redirect:/ex-bbs";
	}
	
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(form.getIntArticleId());
		System.out.println(form);
		
		commentRepository.insert(comment);
		
		return "redirect:/ex-bbs";
	}
	
}
