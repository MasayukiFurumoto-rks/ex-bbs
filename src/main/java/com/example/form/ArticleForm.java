package com.example.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 記事についての投稿情報を受け取るフォームクラスです。<br>
 * 
 * @author cyjoh
 *
 */
public class ArticleForm {
	@NotEmpty(message="名前を入力してください。")
	@Size(max=50,message="名前は50字以内で入力してください")
	private String name;

	@NotEmpty(message="内容を入力してください。")
	private String content;
	
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
		return "ArticleForm [name=" + name + ", content=" + content + "]";
	}

}
