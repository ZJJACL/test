package com.lee.floater.listeners;

import android.content.Context;
import android.content.Intent;

import com.lee.floater.activity.TopicMainPageActivity;
import com.lee.floater.adapters.BaseRecyclerAdapter;
import com.lee.floater.items.Topic_Item;

/**
 * �������б��е�ĳһ�������ʱ�򣬵��������������������������Ӧ�Ļ�����ҳ��Activity
 *
 */
public class TopicListItemListener  implements BaseRecyclerAdapter.OnItemClickListener<Topic_Item> {

	Context context ;
	
	public TopicListItemListener(Context context){
		this.context = context;
	}
	
	
	@Override
	public void onItemClick(int position, Topic_Item data) {
		
		Intent intent = new Intent() ;
 	    intent.setClass(context, TopicMainPageActivity.class) ;
        context.startActivity(intent) ;
		
	}


}
