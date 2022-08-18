package com.quantumdataengines.app.model;


public class ChatMessage {
	private String chatWindowId;
	private String fromUser;
	private String toUser;
	private String toUserName;
	private String messageId;
	private String messageContent;
	private String messageSentDate;
	private String messageReceiveDate;
	private String status;
	private String showFrom;
	private String showTime;
	private String directionLeft;
	private String directionRight;
	private String messageMaxNo;
	
	public String getChatWindowId() {
		return chatWindowId;
	}
	public void setChatWindowId(String chatWindowId) {
		this.chatWindowId = chatWindowId;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageSentDate() {
		return messageSentDate;
	}
	public void setMessageSentDate(String messageSentDate) {
		this.messageSentDate = messageSentDate;
	}
	public String getMessageReceiveDate() {
		return messageReceiveDate;
	}
	public void setMessageReceiveDate(String messageReceiveDate) {
		this.messageReceiveDate = messageReceiveDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShowFrom() {
		return showFrom;
	}
	public void setShowFrom(String showFrom) {
		this.showFrom = showFrom;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getDirectionLeft() {
		return directionLeft;
	}
	public void setDirectionLeft(String directionLeft) {
		this.directionLeft = directionLeft;
	}
	public String getDirectionRight() {
		return directionRight;
	}
	public void setDirectionRight(String directionRight) {
		this.directionRight = directionRight;
	}
	public String getMessageMaxNo() {
		return messageMaxNo;
	}
	public void setMessageMaxNo(String messageMaxNo) {
		this.messageMaxNo = messageMaxNo;
	}
}
