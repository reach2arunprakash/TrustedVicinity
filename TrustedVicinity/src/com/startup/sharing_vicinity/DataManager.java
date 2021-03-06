package com.startup.sharing_vicinity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseObject;

public class DataManager {

	static List <NewsItemInfo> fullOriginalData = null;
	static List <NewsItemInfo> fullMyPostsData = null;
	static String searchQuery = "";

	protected List<NewsItemInfo> getDataBasedOnOptions(int activeDrawerItem,int activeCategoryTab, boolean isSearch) {
		List <NewsItemInfo> out = new ArrayList<NewsItemInfo>();
		if(fullOriginalData==null){
			return out;
		}
		else{
			out.addAll(fullOriginalData);
			for(int i=0;i<out.size();i++){
				if(!out.get(i).getType().equalsIgnoreCase(BaseActivity.drawerItems[activeDrawerItem])){
					out.remove(i);
					i--;
				}
				else if(!out.get(i).getTag().equalsIgnoreCase(BaseActivity.tabItems[activeCategoryTab])){
					out.remove(i);
					i--;
				}
			}
			if(isSearch)
				return getDataBasedOnSearch(out);
			else
				return out;
		}
	}

	public void saveData(List<ParseObject> data) {
		List<NewsItemInfo> out = new ArrayList<NewsItemInfo>();

		for(int i=0;i<data.size();i++)
		{
			String name=data.get(i).getString("name");
			String description = data.get(i).getString("description");
			Date createdAt = data.get(i).getCreatedAt();
			String type = data.get(i).getString("type");
			String tag = data.get(i).getString("tags");
			String userId = data.get(i).getString("userId");
			String imgURL = data.get(i).getString("URL");
			NewsItemInfo newsInfo = new NewsItemInfo(description, imgURL,tag, type,createdAt,name,userId);
			out.add(newsInfo);
		}	
		fullOriginalData = out;
	}

	public void saveMyPostsData(List<ParseObject> data) {
		List<NewsItemInfo> out = new ArrayList<NewsItemInfo>();

		for(int i=0;i<data.size();i++)
		{
			String name=data.get(i).getString("name");
			String description = data.get(i).getString("description");
			Date createdAt = data.get(i).getCreatedAt();
			String type = data.get(i).getString("type");
			String tag = data.get(i).getString("tags");
			NewsItemInfo newsInfo = new NewsItemInfo(description, null,tag, type,createdAt,name, null);
			out.add(newsInfo);
		}	
		fullMyPostsData = out;
	}

	protected List<NewsItemInfo> getMyPosts() {
		List <NewsItemInfo> out = new ArrayList<NewsItemInfo>();
		if(fullMyPostsData==null){
			return out;
		}
		else{
			out.addAll(fullMyPostsData);
			return out;
		}
	}

	public List<NewsItemInfo> getDataBasedOnSearch(List<NewsItemInfo> out) {
		for(int i=0;i<out.size();i++){
			if(!out.get(i).getMessage().contains(searchQuery)){
				out.remove(i);
				i--;	
			}
		}
		return out;

	}



}
