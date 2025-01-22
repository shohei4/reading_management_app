package model;

import java.sql.Date;
import java.sql.Timestamp;


public class ReplyModel {
	
	private int id;
	private int userId;
	private int commentId;
	private String userName;
	private Date registrationDate;
	private String reply;
	private int isDeleted;
	private int favorite;
	private int favoriteCount;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}

	public int getCommentId() {
		return this.commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public String getReply() {
		return this.reply;
	}
	
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	public int getIsDeleted() {
		return this.isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public int getFavorite() {
		return this.favorite;
	}
	
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}
	
	public int getFavoriteCount() {
		return this.favoriteCount;
	}
	
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}
	
	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	


}
