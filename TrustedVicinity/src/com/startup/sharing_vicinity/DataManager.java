package com.startup.sharing_vicinity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseObject;

public class DataManager {

	List <NewsItemInfo> fullOriginalData = null;

	protected List<NewsItemInfo> getDataBasedOnOptions(int activeDrawerItem,int activeCategoryTab) {
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
			NewsItemInfo newsInfo = new NewsItemInfo(description, null,tag, type,createdAt,name);
			out.add(newsInfo);
		}
		fullOriginalData = out;
	}


}
