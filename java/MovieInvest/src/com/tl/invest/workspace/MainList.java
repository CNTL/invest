package com.tl.invest.workspace;
import java.util.ArrayList;

import antlr.collections.List;

import com.tl.invest.proj.ProjectExt;
import com.tl.invest.user.recruit.UserRecruit;
import com.tl.invest.user.user.User;


/**创建主页项目、影聘、影人
 * @author wang.yq
 *
 */
public class MainList  {
	
	 private ArrayList<ProjectExt> projectItems =new ArrayList<ProjectExt>();  
	 private ArrayList<UserRecruit> userRecruitItems =new ArrayList<UserRecruit>();  
	 private ArrayList<User> userItems =new ArrayList<User>();
	public ArrayList<ProjectExt> getProjectItems() {
		return projectItems;
	}
	public void setProjectItems(ArrayList<ProjectExt> projectItems) {
		this.projectItems = projectItems;
	}
	public ArrayList<UserRecruit> getUserRecruitItems() {
		return userRecruitItems;
	}
	public void setUserRecruitItems(ArrayList<UserRecruit> userRecruitItems) {
		this.userRecruitItems = userRecruitItems;
	}
	public ArrayList<User> getUserItem() {
		return userItems;
	}
	public void setUserItem(ArrayList<User> userItem) {
		this.userItems = userItem;
	}  
	 
	 
}
