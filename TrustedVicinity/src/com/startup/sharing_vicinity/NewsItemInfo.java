package com.startup.sharing_vicinity;

import java.util.Date;


//public class NewsItemInfo implements Comparable<NewsItemInfo>{
public class NewsItemInfo{

	private String message;
	private String imageUrl;
	private String tag;
	private String type;
	private Date dateCreated;
	private String name;
	private String userId;

	public NewsItemInfo(String message, String imageUrl, String tag, String type, Date createdAt, String name, String userId ) {
		super();
		this.message = message;
		this.imageUrl = imageUrl;
		this.tag = tag;
		this.type = type;
		this.dateCreated  =createdAt;
		this.name = name;
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	

	
	
	//	@Override
	//	public int compareTo(NewsItemInfo another) {
	//		if(MainActivity.sortbySelected==1){
	//			return (int) (another.getReleaseDate()-releaseDate);
	//		}
	//		else if(MainActivity.sortbySelected==2){
	//			return (int) (releaseDate-another.getReleaseDate());
	//		}
	//		else if(MainActivity.sortbySelected==3){
	//			return message.compareTo(another.getVideoName());
	//		}
	//		else if(MainActivity.sortbySelected==4){
	//			return another.getVideoName().compareTo(message);
	//		}
	//		return 0;
	//	}

}
