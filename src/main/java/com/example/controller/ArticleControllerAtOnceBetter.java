package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.AtOnceRepositoryBetter;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/ex-bbs-at-once-better")
public class ArticleControllerAtOnceBetter {

	@ModelAttribute
	private ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	private CommentForm setUpCommentForm() {
		return new CommentForm();
	}

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private AtOnceRepositoryBetter atOnceRepositoryBetter;

	@RequestMapping("")
	public String indexAtOnceBetter(Model model) {
		List<Article> articleList = atOnceRepositoryBetter.findAllAsJoin();

		model.addAttribute("articleList", articleList);

		return "bbs-at-once-better";

	}

	@RequestMapping("/insert-article")
	public String insertArticle(@Validated ArticleForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return indexAtOnceBetter(model);
		}

		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);

		return "redirect:/ex-bbs-at-once-better";
	}

	@RequestMapping("/delete-article")
	public String deleteArticle(Integer id) {
		articleRepository.delete(id);

		// レポジトリではarticle_idとして受け取っています
		commentRepository.delete(id);

		return "redirect:/ex-bbs-at-once-better";
	}

	@RequestMapping("/insert-comment")
	public String insertComment(@Validated CommentForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return indexAtOnceBetter(model);
		}
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(form.getIntArticleId());

		commentRepository.insert(comment);

		return "redirect:/ex-bbs-at-once-better";
	}

}
