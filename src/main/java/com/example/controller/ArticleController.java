package com.example.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;


@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ServletContext application;
	
	@ModelAttribute
	private ArticleForm setUpForm() {
		return new ArticleForm();
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
	
	@RequestMapping("/create-article")
	public String createArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);
		
		return "redirect:/ex-bbs";
	}
	
	@RequestMapping("/delete-article")
	public String createArticle(Integer id) {
		articleRepository.delete(id);
		
		return "redirect:/ex-bbs";
	}
}
