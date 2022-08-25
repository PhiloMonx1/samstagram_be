package com.hanghae99.samstargram_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae99.samstargram_be.model.dto.ArticleRequestDto;
import com.hanghae99.samstargram_be.service.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "article")
@Entity
public class Article extends Timestamped {
	@Id
	@Column(name = "article_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long articlesId;

	@Column(nullable = false)
	private String username;

	private String useremail;

	private String userprofile;

	private String content;

	@Column
	@ElementCollection
	private List<String> image = new ArrayList<>();

	@ManyToOne
	@JsonBackReference
	private Member member;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	@JsonIgnore
	private List<Comment> commentList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Heart> heartList = new ArrayList<>();

	@Column
	@ElementCollection
	private List<String> hashtagList = new ArrayList<>();

	private int commentCnt;
	private int likeCnt;

	private Boolean isLike = false;

	private Boolean isMyArticles = false;

	public Article(ArticleRequestDto articleRequestDto, Member member){
		this.username = member.getUsername();
		this.useremail = member.getUseremail();
		this.userprofile = member.getUserprofile();
		this.content = articleRequestDto.getContent();
	}

	public Article(Member member){
		this.username = member.getUsername();
		this.useremail = member.getUseremail();
		this.userprofile = member.getUserprofile();
		this.content = (Math.random() * 8999999+1000000) + "명";
	}

	public void update(ArticleRequestDto articleRequestDto) {
		this.content = articleRequestDto.getContent();
	}

	public void addImage(String image){
		this.image.add(image);
	}

	public void addHashtag(String hashtag){
		this.hashtagList.add(hashtag);
	}

	public void setHeartCnt(int heartCnt) {
		this.likeCnt = heartCnt;
	}

	public void setLike(Boolean like) {
		isLike = like;
	}

	public void setMyArticles(Boolean myArticles) {
		isMyArticles = myArticles;
	}

	public void addComment(Comment comment) {
		this.commentList.add(comment);
		this.commentCnt = this.getCommentList().size();
	}
	public void removeComment(Comment comment) {
		this.commentList.remove(comment);
		this.commentCnt = this.getCommentList().size();
	}

	public void addHeart(Heart heart){
		this.heartList.add(heart);
		this.likeCnt = this.heartList.size();
	}
	public void removeHeart(Heart heart){
		this.heartList.remove(heart);
		this.likeCnt = this.heartList.size();
	}

	public void setImage(List<String> image) {
		this.image = image;
	}

	public void setContent(String content) {
		this.content = content;
	}
}


