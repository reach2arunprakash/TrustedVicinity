package com.startup.sharing_vicinity;


//public class NewsItemInfo implements Comparable<NewsItemInfo>{
public class NewsItemInfo{

	private String message;
	private String imageUrl;
	private String tag;
	private String type;

	public NewsItemInfo(String message, String imageUrl, String tag, String type ) {
		super();
		this.message = message;
		this.imageUrl = imageUrl;
		this.tag = tag;
		this.type = type;
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
