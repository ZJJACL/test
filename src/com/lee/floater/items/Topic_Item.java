package com.lee.floater.items;

/**
 * 
 * ÿһ�����ⶼ��������ͬ����Ԫ��
 *�˴�ֻ�г��˻����б��еļ���Ԫ�أ�����Ԫ�ؿ��Ժ����
 *
 */
public class Topic_Item {
	
	public  int topic_icon ;//����icon
	public String topic_title;//������
	public int topic_content_number ;//��Ƭ��
	public int topic_category;//�������
	public int update_number ;//����������Ѿ���ע�Ļ���
	
	
	//get��set����
	public void setTopicIcon(int topic_icon){
		this.topic_icon = topic_icon;
	}
	public int getTopicIcon(){
		return this.topic_icon;
	}
	
	public void setTopicTitle(String topic_title){
		this.topic_title = topic_title;
	}
	public String getTopicTitle(){
		return this.topic_title;
	}
	
	public void setTopicContentNumber(int topic_content_number){
		this.topic_content_number = topic_content_number;
	}
	public int getTopicContentNumber(){
		return this.topic_content_number;
	}
	
	public void setTopicCategory(int topic_category){
		this.topic_category = topic_category ;
	}
	public int getTopicCategory(){
		return this.topic_category;
	}
	
	public void setTopicUpdateNumber(int update_number){
		this.update_number=update_number;
	}
	public String getTopicUpdateNumber(){
		if(this.update_number <=0 ) return "";
		else return "+"+this.update_number;
	}

}
