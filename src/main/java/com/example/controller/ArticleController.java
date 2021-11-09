package com.example.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;


@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {

	@Autowired
	private ArticleRepository repository;
	
	@Autowired
	private ServletContext application;
	
	@RequestMapping("")
	public String index(){
		List<Article> articleList = repository.findAll();
		application.setAttribute("articleList", articleList);
		
		return "bbs";
	}
	
	@RequestMapping("/create-article")
	public String createArticle() {
		
		
		return "redirect:";
	}
}
