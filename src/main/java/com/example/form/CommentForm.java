package com.example.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentForm {

	@NotEmpty(message="名前を入力してください。")
	@Size(min=0,max=50,message="名前は50字以内で入力してください")
	private String name;
	
	@NotEmpty(message="コメントを入力してください。")
	private String content;
	private String articleId;

	public String getArticleId() {
		return articleId;
	}
	
	public Integer getIntArticleId() {
		return Integer.parseInt(articleId);
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

}
