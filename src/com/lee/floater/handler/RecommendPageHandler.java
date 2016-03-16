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
 * ���д�����ҳ_�Ƽ�ҳ���е�����
 */
public class RecommendPageHandler {
	
	Context context;//��Main������
	public View find_recommend_view; //�Ƽ�ҳ�����View
	public View find_recommend_page_head;//�Ƽ�ҳ���ͷ��View(Banner/10���Ƽ�����)
	
	public ViewPager bannerViewPager; // Banner��ViewPager
	public RecyclerView my_follow_topic_recyclerView;//�ҹ�ע�Ļ����RecyclerView
	
    //�ҹ�ע�Ļ�������List
    public ArrayList<Topic_Item> my_follow_topic_items = new ArrayList<Topic_Item>();

    //�ײ�ˢ��View����
    public View refresh_footer_view;
    
    //����ˢ����������
    SwipeRefreshLayout refresh_bar_for_recommend_page;
    
    LinearLayoutManager linearLayoutManager;//��ע�Ļ����RecyclerView�Ĳ���
    
	TopicListAdapter adapter; //��ע�Ļ����RecyclerView��Adapter
    
	/**
	 * ���췽���н��Ƽ�ҳ��View��Main������
	 */
	public RecommendPageHandler(View find_recommend_view, Context context){
		this.find_recommend_view = find_recommend_view ;
		this.context = context;
		findAllViewById();
	}
	
	/**
	 * �����Ƽ�ҳ�е����е�View����
	 */
	public void findAllViewById(){
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		//���Ƽ�ҳ���е�ͷ��Viewװ��ΪView
        find_recommend_page_head = 
        		inflater.inflate(R.layout.find_recommend_page_head,null);
        
        //����Banner��ViewPage
        bannerViewPager=
        		(ViewPager)find_recommend_page_head.findViewById(R.id.banner_view_pager);
        
     	//����Ϊ�ҹ�ע�Ļ����д��RecyclerView
        my_follow_topic_recyclerView =
        		(RecyclerView)find_recommend_view.findViewById(R.id.my_follow_topic_recyclerview);
        
        //���б�β���ļ��ظ����XMLװ��ΪView
        refresh_footer_view = inflater.inflate(R.layout.refresh_footer_view,null);
        
       //�����Ƽ�ҳ���ˢ����SwipeRefreshLayout
        refresh_bar_for_recommend_page = 
        		(SwipeRefreshLayout)find_recommend_view.findViewById(R.id.refresh_bar_for_recommend_page);  
        //Ϊ���ˢ������ӹ�����ɫ
        refresh_bar_for_recommend_page.setColorSchemeResources(R.color.color1, R.color.color2,R.color.color3, R.color.color4);
        //Ϊҳ���е�ˢ�¿ؼ���Ӽ�����
        refresh_bar_for_recommend_page.setOnRefreshListener(new RecommendPageRefreshListener());
        //Ϊ��ʵ������ˢ�£�������ҪΪҳ���RecyclerView��ӹ����¼�������
        my_follow_topic_recyclerView.setOnScrollListener(new RecommendPageOnScrollListener());

	}
	
	/**
	 * �Ƽ�ҳ���е����ݼ��ء���ʾ��ˢ�µ�����������м���
	 */
	public void Handle (){
		
		//���Banner��ViewPager��װ�غ���ʾ
        setBannerViewPager();
        
      //����ÿ�ջ����Ƽ��е�����
        loadEverydayTopicItems();
        
      //�����ҹ�ע�Ļ����е�����
        loadMyFollowTopicItems();
        
        //���ҹ�ע�Ļ����е�����װ�ص�RecycleView��
        setMyFollowTopicRecyclerView();
        
	}
	
	/**
	 * ���BannerViewPager��װ��,��Ϊ���趨������
	 */
	public void setBannerViewPager(){
		List<View> list = new ArrayList<View>() ;
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
				                                  LinearLayout.LayoutParams.WRAP_CONTENT); 
		
		//Ԥ��Ϊװ��3�Żõ�Banner��ʵ��Ӧ�õ�������һ���̴߳ӷ�������ȡͼƬ
		for(int i =0 ; i<3 ; i ++){
             ImageView imageView = new ImageView(context) ;
             imageView.setLayoutParams(mParams);
             imageView.setImageResource(R.drawable.banner);
             imageView.setOnClickListener(new BannerListener(i,context));
             list.add(imageView);
         }
		
		//ΪBannerViewPager����������
		ScreenPagerAdapter sPagerAdapter = new ScreenPagerAdapter(list);

		//ΪViewPager����Adapter
		bannerViewPager.setAdapter(sPagerAdapter);
		
		//ΪBannerViewPager�趨���������Կ���Banner_picker�Ĺ���
		bannerViewPager.setOnPageChangeListener(new BannerListener(
				(RotateImageView)find_recommend_page_head.findViewById(R.id.banner_picker),
				(TextView)find_recommend_page_head.findViewById(R.id.banner_number)
				));
	}
	
	/**
	 * �ӷ������˵�ȡÿ���Ƽ��Ļ���
	 * ��ʵ�����е�ʱ��Ӧ�õ�������һ���߳̽��м���
	 */
	public void loadEverydayTopicItems(){
		//ÿ���Ƽ�10��
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
			((TextView)(Item_List.get(i).getChildAt(1))).setText("��Ϊ����������");
			((TextView)(Item_List.get(i).getChildAt(2))).setText("������208����Ƭ");
			((ImageView)(Item_List.get(i).getChildAt(3))).setImageResource(R.drawable.list_topic_category_video);
		}
	}
	
	/**
	 * �ӷ������˵�ȡ�ҹ�ע�Ļ��⣬װ�ص�my_follow_topic_items���List<Topic_Item>��
	 * ��ʵ�����е�ʱ��Ӧ�õ�������һ���߳̽��м���
	 */
	public void loadMyFollowTopicItems(){
		//Ԥ�����10��
		for(int i=0 ; i<10; i++){
			 Topic_Item item = new Topic_Item();
		     item.setTopicIcon(R.drawable.list_topic_icon);
		     item.setTopicTitle("У԰����Щ�Դ����ܵ�B");
		     item.setTopicContentNumber(1024);
		     item.setTopicUpdateNumber(35);
		     my_follow_topic_items.add(item);
		}
	}
	
	/**
	 * ���ҹ�ע�Ļ����е�����װ�ص�RecycleView�У����趨����¼��ļ�����
	 */
	public void setMyFollowTopicRecyclerView(){

        //Ĭ�϶���Ч��
		my_follow_topic_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //���ò��ֹ�����������������Ϊ�Ƿ����򲼾�
		linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
		my_follow_topic_recyclerView.setLayoutManager(linearLayoutManager);
        //�������Ч��
		my_follow_topic_recyclerView.setHasFixedSize(true);
		//�½�������
        adapter = new TopicListAdapter();
       
        adapter.addDatas(my_follow_topic_items);
        //����ͷ��
        adapter.setHeaderView(find_recommend_page_head);
        //����β��
        adapter.setFooterView(refresh_footer_view);
        
        //����������
        my_follow_topic_recyclerView.setAdapter(adapter);
        //����ȫ����Loading
        RelativeLayout loadingPage =( RelativeLayout)find_recommend_view.findViewById(R.id.loading_start_for_recommend_page);
        loadingPage.setVisibility(View.GONE);
        
        //���ü�����,�������б��е�ĳһ�������ʱ��ص�
        adapter.setOnItemClickListener(new TopicListItemListener(context));
    }
	
	  /**
     * �Ƽ�ҳ���е�����ˢ�¼�����
     */
    class RecommendPageRefreshListener implements OnRefreshListener{

    	@Override
    	public void onRefresh() {
    		
    		//������Ҫ�ǶԹ�ע�Ļ����б�ĸ���
    		ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
			for(int i=0 ; i<5; i++){
				 Topic_Item item = new Topic_Item();
			     item.setTopicIcon(R.drawable.list_topic_icon);
			     item.setTopicTitle("�Ҵ���û��˵��������");
			     item.setTopicContentNumber(389);
			     item.setTopicUpdateNumber(8);
			     newDatas.add(item);
			}  
			
			adapter.refreshPullDown(newDatas);
    		
    		refresh_bar_for_recommend_page.setRefreshing(false);  
    	}
    }
    
    
    /**
     * �Ƽ�ҳ���еļ��ظ���ˢ�¼�����
     */
    class RecommendPageOnScrollListener extends RecyclerView.OnScrollListener{

    	int lastVisibleItem;
    	
    	@Override  
    	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {  
    		super.onScrollStateChanged(recyclerView, newState);  
    		
    		if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {  

    			TextView text = (TextView)refresh_footer_view.findViewById(R.id.fresh_footer_text);
    			text.setText("Ŭ��������...");
    			
    			//���ʷ������������ݵĵ�ȡ
    			//ע�⣺����Ѿ������һҳ����ôFooterViewӦ����ʧ�����Ҳ��ٽ��չ����¼�
    			ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
    			for(int i=0 ; i<5; i++){
    				 Topic_Item item = new Topic_Item();
    			     item.setTopicIcon(R.drawable.list_topic_icon);
    			     item.setTopicTitle("������Ҫ�¸�һ��");
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
