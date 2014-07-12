package com.startup.sharing_vicinity;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

	String fullData = "";

	protected List<NewsItemInfo> getFullNewsFeedList() {
		List <NewsItemInfo> out = new ArrayList<NewsItemInfo>();
		if(fullData==null){
			return out;
		}
		else{
			NewsItemInfo newsItemInfo = new NewsItemInfo("selling movie ticket of dawn of planet of apes @AGS navalur @ 5pm today", "", "ticket","selling");
			NewsItemInfo newsItemInfo2 = new NewsItemInfo("selling old book Harrypotter part 2 by JK Rolling", "", "book","selling");
			out.add(newsItemInfo);
			out.add(newsItemInfo2);
			return out;
		}
	}

	protected List<NewsItemInfo> getDataBasedOnOptions(int activeDrawerItem,int activeCategoryTab) {
		List <NewsItemInfo> out = new ArrayList<NewsItemInfo>();
		if(fullData==null){
			return out;
		}
		else{
			NewsItemInfo newsItemInfo = new NewsItemInfo("selling movie ticket of dawn of planet of apes @AGS navalur @ 5pm today", "", "tickets","selling");
			NewsItemInfo newsItemInfo2 = new NewsItemInfo("selling old book Harrypotter part 2 by JK Rolling", "", "books","selling");
			out.add(newsItemInfo);
			out.add(newsItemInfo2);

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

	public void saveData(String data) {
		fullData = data;		
	}


}
