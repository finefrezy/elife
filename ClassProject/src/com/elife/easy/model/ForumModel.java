package com.elife.easy.model;

public class ForumModel {
	private String userId;
	private String userName;
	private String userIcon;
	private String forumId;
	private String msgContent;
	private String imgs;
	private String timeValue;
	private int likeCount;
	private int commentCount;
	private int transferCount;
	
	
	
	
	public String getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}
	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getTimeValue() {
		return timeValue;
	}
	public void setTimeValue(String timeValue) {
		this.timeValue = timeValue;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getTransferCount() {
		return transferCount;
	}
	public void setTransferCount(int transferCount) {
		this.transferCount = transferCount;
	}
	@Override
	public String toString() {
		return "ForumModel [userName=" + userName + ", msgContent=" + msgContent + ", timeValue=" + timeValue
				+ ", likeCount=" + likeCount + ", commentCount=" + commentCount + ", transferCount=" + transferCount
				+ "]";
	}
	
	
}
