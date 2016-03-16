package com.lee.floater.handler;

import java.util.ArrayList;
import java.util.List;

import com.lee.floater.R;
import com.lee.floater.adapters.ScreenPagerAdapter;
import com.lee.floater.adapters.TopicListAdapter;
import com.lee.floater.items.Topic_Item;
import com.lee.floater.listeners.BannerListener;
import com.lee.floater.listeners.TopicListItemListener;
import com.lee.floater.support.RotateImageView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
<<<<<<< master
 * 集中处理首页_推荐页面中的事务
=======
 * ���д�����ҳ_�Ƽ�ҳ���е�����
>>>>>>> 8fec199 li
 */
public class RecommendPageHandler {
	
	Context context;//将Main传进来
	public View find_recommend_view; //推荐页面的主View
	public View find_recommend_page_head;//推荐页面的头部View(Banner/10条推荐话题)
	
	public ViewPager bannerViewPager; // Banner的ViewPager
	public RecyclerView my_follow_topic_recyclerView;//我关注的话题的RecyclerView
	
    //我关注的话题数据List
    public ArrayList<Topic_Item> my_follow_topic_items = new ArrayList<Topic_Item>();

    //底部刷新View索引
    public View refresh_footer_view;
    
    //下拉刷新条的索引
    SwipeRefreshLayout refresh_bar_for_recommend_page;
    
    LinearLayoutManager linearLayoutManager;//关注的话题的RecyclerView的布局
    
	TopicListAdapter adapter; //关注的话题的RecyclerView的Adapter
    
	/**
	 * 构造方法中将推荐页面View和Main传进来
	 */
	public RecommendPageHandler(View find_recommend_view, Context context){
		this.find_recommend_view = find_recommend_view ;
		this.context = context;
		findAllViewById();
	}
	
	/**
	 * 加载推荐页中的所有的View索引
	 */
	public void findAllViewById(){
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		//将推荐页面中的头部View装载为View
        find_recommend_page_head = 
        		inflater.inflate(R.layout.find_recommend_page_head,null);
        
        //加载Banner的ViewPage
        bannerViewPager=
        		(ViewPager)find_recommend_page_head.findViewById(R.id.banner_view_pager);
        
     	//加载为我关注的话题编写的RecyclerView
        my_follow_topic_recyclerView =
        		(RecyclerView)find_recommend_view.findViewById(R.id.my_follow_topic_recyclerview);
        
        //将列表尾部的加载更多的XML装载为View
        refresh_footer_view = inflater.inflate(R.layout.refresh_footer_view,null);
        
       //加载推荐页面的刷新条SwipeRefreshLayout
        refresh_bar_for_recommend_page = 
        		(SwipeRefreshLayout)find_recommend_view.findViewById(R.id.refresh_bar_for_recommend_page);  
        //为这个刷新条添加滚动颜色
        refresh_bar_for_recommend_page.setColorSchemeResources(R.color.color1, R.color.color2,R.color.color3, R.color.color4);
        //为页面中的刷新控件添加监听器
        refresh_bar_for_recommend_page.setOnRefreshListener(new RecommendPageRefreshListener());
        //为了实现下拉刷新，这里需要为页面的RecyclerView添加滚动事件监听器
        my_follow_topic_recyclerView.setOnScrollListener(new RecommendPageOnScrollListener());

	}
	
	/**
	 * 推荐页面中的数据加载、显示、刷新等所有事务的中继器
	 */
	public void Handle (){
		
		//完成Banner的ViewPager的装载和显示
        setBannerViewPager();
        
      //加载每日话题推荐中的数据
        loadEverydayTopicItems();
        
      //加载我关注的话题中的数据
        loadMyFollowTopicItems();
        
        //将我关注的话题中的数据装载到RecycleView中
        setMyFollowTopicRecyclerView();
        
	}
	
	/**
	 * 完成BannerViewPager的装载,并为其设定监听器
	 */
	public void setBannerViewPager(){
		List<View> list = new ArrayList<View>() ;
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
				                                  LinearLayout.LayoutParams.WRAP_CONTENT); 
		
		//预设为装载3张幻灯Banner，实际应该单独开辟一条线程从服务器调取图片
		for(int i =0 ; i<3 ; i ++){
             ImageView imageView = new ImageView(context) ;
             imageView.setLayoutParams(mParams);
             imageView.setImageResource(R.drawable.banner);
             imageView.setOnClickListener(new BannerListener(i,context));
             list.add(imageView);
         }
		
		//为BannerViewPager创建适配器
		ScreenPagerAdapter sPagerAdapter = new ScreenPagerAdapter(list);

		//为ViewPager设置Adapter
		bannerViewPager.setAdapter(sPagerAdapter);
		
		//为BannerViewPager设定监听器，以控制Banner_picker的滚动
		bannerViewPager.setOnPageChangeListener(new BannerListener(
				(RotateImageView)find_recommend_page_head.findViewById(R.id.banner_picker),
				(TextView)find_recommend_page_head.findViewById(R.id.banner_number)
				));
	}
	
	/**
	 * 从服务器端调取每日推荐的话题
	 * 在实际运行的时候应该单独开辟一条线程进行加载
	 */
	public void loadEverydayTopicItems(){
		//每日推荐10条
		RelativeLayout Item_1 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_1);
		RelativeLayout Item_2 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_2);
		RelativeLayout Item_3 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_3);
		RelativeLayout Item_4 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_4);
		RelativeLayout Item_5 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_5);
		RelativeLayout Item_6 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_6);
		RelativeLayout Item_7 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_7);
		RelativeLayout Item_8 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_8);
		RelativeLayout Item_9 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_9);
		RelativeLayout Item_10 = 
				(RelativeLayout)find_recommend_page_head.findViewById(R.id.recommend_topic_10);
		
		ArrayList<RelativeLayout> Item_List = new ArrayList<RelativeLayout>();
		Item_List.add(Item_1);
		Item_List.add(Item_2);
		Item_List.add(Item_3);
		Item_List.add(Item_4);
		Item_List.add(Item_5);
		Item_List.add(Item_6);
		Item_List.add(Item_7);
		Item_List.add(Item_8);
		Item_List.add(Item_9);
		Item_List.add(Item_10);
		for(int i=0 ; i<10;i++){
			((ImageView)(Item_List.get(i).getChildAt(0))).setImageResource(R.drawable.list_topic_icon);
			((TextView)(Item_List.get(i).getChildAt(1))).setText("我为室友来征婚");
			((TextView)(Item_List.get(i).getChildAt(2))).setText("已制造208个卡片");
			((ImageView)(Item_List.get(i).getChildAt(3))).setImageResource(R.drawable.list_topic_category_video);
		}
	}
	
	/**
	 * 从服务器端调取我关注的话题，装载到my_follow_topic_items这个List<Topic_Item>中
	 * 在实际运行的时候应该单独开辟一条线程进行加载
	 */
	public void loadMyFollowTopicItems(){
		//预设加载10条
		for(int i=0 ; i<10; i++){
			 Topic_Item item = new Topic_Item();
		     item.setTopicIcon(R.drawable.list_topic_icon);
		     item.setTopicTitle("校园中那些自带技能的B");
		     item.setTopicContentNumber(1024);
		     item.setTopicUpdateNumber(35);
		     my_follow_topic_items.add(item);
		}
	}
	
	/**
	 * 将我关注的话题中的数据装载到RecycleView中，并设定点击事件的监听器
	 */
	public void setMyFollowTopicRecyclerView(){

        //默认动画效果
		my_follow_topic_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置布局管理器，第三个参数为是否逆向布局
		linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
		my_follow_topic_recyclerView.setLayoutManager(linearLayoutManager);
        //可以提高效率
		my_follow_topic_recyclerView.setHasFixedSize(true);
		//新建适配器
        adapter = new TopicListAdapter();
       
        adapter.addDatas(my_follow_topic_items);
        //加载头部
        adapter.setHeaderView(find_recommend_page_head);
        //加载尾部
        adapter.setFooterView(refresh_footer_view);
        
        //设置适配器
        my_follow_topic_recyclerView.setAdapter(adapter);
        //隐藏全屏的Loading
        RelativeLayout loadingPage =( RelativeLayout)find_recommend_view.findViewById(R.id.loading_start_for_recommend_page);
        loadingPage.setVisibility(View.GONE);
        
        //设置监听器,当话题列表中的某一项被单击的时候回调
        adapter.setOnItemClickListener(new TopicListItemListener(context));
    }
	
	  /**
     * 推荐页面中的下拉刷新监听器
     */
    class RecommendPageRefreshListener implements OnRefreshListener{

    	@Override
    	public void onRefresh() {
    		
    		//这里主要是对关注的话题列表的更新
    		ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
			for(int i=0 ; i<5; i++){
				 Topic_Item item = new Topic_Item();
			     item.setTopicIcon(R.drawable.list_topic_icon);
			     item.setTopicTitle("我从来没有说过的秘密");
			     item.setTopicContentNumber(389);
			     item.setTopicUpdateNumber(8);
			     newDatas.add(item);
			}  
			
			adapter.refreshPullDown(newDatas);
    		
    		refresh_bar_for_recommend_page.setRefreshing(false);  
    	}
    }
    
    
    /**
     * 推荐页面中的加载更多刷新监听器
     */
    class RecommendPageOnScrollListener extends RecyclerView.OnScrollListener{

    	int lastVisibleItem;
    	
    	@Override  
    	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {  
    		super.onScrollStateChanged(recyclerView, newState);  
    		
    		if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {  

    			TextView text = (TextView)refresh_footer_view.findViewById(R.id.fresh_footer_text);
    			text.setText("努力加载中...");
    			
    			//访问服务器进行数据的调取
    			//注意：如果已经是最后一页，那么FooterView应该消失，并且不再接收滚动事件
    			ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
    			for(int i=0 ; i<5; i++){
    				 Topic_Item item = new Topic_Item();
    			     item.setTopicIcon(R.drawable.list_topic_icon);
    			     item.setTopicTitle("今天我要勇敢一次");
    			     item.setTopicContentNumber(3020);
    			     item.setTopicUpdateNumber(16);
    			     newDatas.add(item);
    			}  
    			
    			adapter.refreshPushUp(newDatas);
    			
    		}  
    	}
    	
    	@Override  
    	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {  
    		super.onScrolled(recyclerView,dx, dy);  
    		lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();  
    	}	
    }
	

}
