package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.ArticleAndComment;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.AtOnceRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/ex-bbs-at-once")
public class ArticleControllerAtOnce {

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

	@Autowired
	private AtOnceRepository atOnceRepository;

	@RequestMapping("")
	public String indexAtOnce(Model model) {
		List<Article> allArticleList = new ArrayList<>();
		List<Comment> allCommentList = new ArrayList<>();
		List<ArticleAndComment> domainList = atOnceRepository.findAllAsJoin();
		
		//domainからコメントをインスタンス化
		for (ArticleAndComment domain2 : domainList) {
			Comment comment = new Comment();
			comment.setId(domain2.getComId());
			comment.setName(domain2.getComName());
			comment.setContent(domain2.getComContent());
			comment.setArticleId(domain2.getArticleId());
			allCommentList.add(comment);
		}
		
		//domainから記事をインスタンス化
		for (ArticleAndComment domain : domainList) {
			Article article = new Article();
			article.setId(domain.getId());
			article.setName(domain.getName());
			article.setContent(domain.getContent());

			//全体のコメントリストのすべての中から、記事idが今の記事に等しいもののみをid別コメントリストに格納。
			//そのコメントリストを今の記事に格納。
			List<Comment> commentListById = new ArrayList<>(); 
			
			for(Comment comment : allCommentList) {
				if(comment.getArticleId()==article.getId()) {
					commentListById.add(comment);
				}
			}
			
			article.setCommentList(commentListById);
			
			if (!allArticleList.isEmpty()) {
				if (allArticleList.get(allArticleList.size() - 1).getId() != article.getId()) {
					allArticleList.add(article);
				}
			} else {
				allArticleList.add(article);
			}

		}

		System.out.println(allArticleList);
		System.out.println(allCommentList);
		model.addAttribute("articleList", allArticleList);
		model.addAttribute("commentList", allCommentList);

		return "bbs-at-once";

	}
	
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form) {
		Article article = new Article();
		BeanUtils.copyProperties(form, article);
		articleRepository.insert(article);

		return "redirect:/ex-bbs-at-once";
	}

	@RequestMapping("/delete-article")
	public String deleteArticle(Integer id) {
		articleRepository.delete(id);

		// レポジトリではarticle_idとして受け取っています
		commentRepository.delete(id);

		return "redirect:/ex-bbs-at-once";
	}

	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		comment.setArticleId(form.getIntArticleId());
		System.out.println(form);

		commentRepository.insert(comment);

		return "redirect:/ex-bbs-at-once";
	}

}
