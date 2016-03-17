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
 * 闆嗕腑澶勭悊棣栭〉_鎺ㄨ崘椤甸潰涓殑浜嬪姟
=======
 * 集中处理首页_推荐页面中的事务
>>>>>>> 8fec199 li
 */
public class RecommendPageHandler {
	
	Context context;//灏哅ain浼犺繘鏉�
	public View find_recommend_view; //鎺ㄨ崘椤甸潰鐨勪富View
	public View find_recommend_page_head;//鎺ㄨ崘椤甸潰鐨勫ご閮╒iew(Banner/10鏉℃帹鑽愯瘽棰�)
	public int i=0;
	public ViewPager bannerViewPager; // Banner鐨刅iewPager
	public RecyclerView my_follow_topic_recyclerView;//鎴戝叧娉ㄧ殑璇濋鐨凴ecyclerView
	
    //鎴戝叧娉ㄧ殑璇濋鏁版嵁List
    public ArrayList<Topic_Item> my_follow_topic_items = new ArrayList<Topic_Item>();

    //搴曢儴鍒锋柊View绱㈠紩
    public View refresh_footer_view;
    
    //涓嬫媺鍒锋柊鏉＄殑绱㈠紩
    SwipeRefreshLayout refresh_bar_for_recommend_page;
    
    LinearLayoutManager linearLayoutManager;//鍏虫敞鐨勮瘽棰樼殑RecyclerView鐨勫竷灞�
    
	TopicListAdapter adapter; //鍏虫敞鐨勮瘽棰樼殑RecyclerView鐨凙dapter
    
	/**
	 * 鏋勯�犳柟娉曚腑灏嗘帹鑽愰〉闈iew鍜孧ain浼犺繘鏉�
	 */
	public RecommendPageHandler(View find_recommend_view, Context context){
		this.find_recommend_view = find_recommend_view ;
		this.context = context;
		findAllViewById();
	}
	
	/**
	 * 鍔犺浇鎺ㄨ崘椤典腑鐨勬墍鏈夌殑View绱㈠紩
	 */
	public void findAllViewById(){
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		//灏嗘帹鑽愰〉闈腑鐨勫ご閮╒iew瑁呰浇涓篤iew
        find_recommend_page_head = 
        		inflater.inflate(R.layout.find_recommend_page_head,null);
        
        //鍔犺浇Banner鐨刅iewPage
        bannerViewPager=
        		(ViewPager)find_recommend_page_head.findViewById(R.id.banner_view_pager);
        
     	//鍔犺浇涓烘垜鍏虫敞鐨勮瘽棰樼紪鍐欑殑RecyclerView
        my_follow_topic_recyclerView =
        		(RecyclerView)find_recommend_view.findViewById(R.id.my_follow_topic_recyclerview);
        
        //灏嗗垪琛ㄥ熬閮ㄧ殑鍔犺浇鏇村鐨刋ML瑁呰浇涓篤iew
        refresh_footer_view = inflater.inflate(R.layout.refresh_footer_view,null);
        
       //鍔犺浇鎺ㄨ崘椤甸潰鐨勫埛鏂版潯SwipeRefreshLayout
        refresh_bar_for_recommend_page = 
        		(SwipeRefreshLayout)find_recommend_view.findViewById(R.id.refresh_bar_for_recommend_page);  
        //涓鸿繖涓埛鏂版潯娣诲姞婊氬姩棰滆壊
        refresh_bar_for_recommend_page.setColorSchemeResources(R.color.color1, R.color.color2,R.color.color3, R.color.color4);
        //涓洪〉闈腑鐨勫埛鏂版帶浠舵坊鍔犵洃鍚櫒
        refresh_bar_for_recommend_page.setOnRefreshListener(new RecommendPageRefreshListener());
        //涓轰簡瀹炵幇涓嬫媺鍒锋柊锛岃繖閲岄渶瑕佷负椤甸潰鐨凴ecyclerView娣诲姞婊氬姩浜嬩欢鐩戝惉鍣�
        my_follow_topic_recyclerView.setOnScrollListener(new RecommendPageOnScrollListener());

	}
	
	/**
	 * 鎺ㄨ崘椤甸潰涓殑鏁版嵁鍔犺浇銆佹樉绀恒�佸埛鏂扮瓑鎵�鏈変簨鍔＄殑涓户鍣�
	 */
	public void Handle (){
		
		//瀹屾垚Banner鐨刅iewPager鐨勮杞藉拰鏄剧ず
        setBannerViewPager();
        
      //鍔犺浇姣忔棩璇濋鎺ㄨ崘涓殑鏁版嵁
        loadEverydayTopicItems();
        
      //鍔犺浇鎴戝叧娉ㄧ殑璇濋涓殑鏁版嵁
        loadMyFollowTopicItems();
        
        //灏嗘垜鍏虫敞鐨勮瘽棰樹腑鐨勬暟鎹杞藉埌RecycleView涓�
        setMyFollowTopicRecyclerView();
        
	}
	
	/**
	 * 瀹屾垚BannerViewPager鐨勮杞�,骞朵负鍏惰瀹氱洃鍚櫒
	 */
	public void setBannerViewPager(){
		List<View> list = new ArrayList<View>() ;
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,  
				                                  LinearLayout.LayoutParams.WRAP_CONTENT); 
		
		//棰勮涓鸿杞�3寮犲够鐏疊anner锛屽疄闄呭簲璇ュ崟鐙紑杈熶竴鏉＄嚎绋嬩粠鏈嶅姟鍣ㄨ皟鍙栧浘鐗�
		for(int i =0 ; i<3 ; i ++){
             ImageView imageView = new ImageView(context) ;
             imageView.setLayoutParams(mParams);
             imageView.setImageResource(R.drawable.banner);
             imageView.setOnClickListener(new BannerListener(i,context));
             list.add(imageView);
         }
		
		//涓築annerViewPager鍒涘缓閫傞厤鍣�
		ScreenPagerAdapter sPagerAdapter = new ScreenPagerAdapter(list);

		//涓篤iewPager璁剧疆Adapter
		bannerViewPager.setAdapter(sPagerAdapter);
		
		//涓築annerViewPager璁惧畾鐩戝惉鍣紝浠ユ帶鍒禕anner_picker鐨勬粴鍔�
		bannerViewPager.setOnPageChangeListener(new BannerListener(
				(RotateImageView)find_recommend_page_head.findViewById(R.id.banner_picker),
				(TextView)find_recommend_page_head.findViewById(R.id.banner_number)
				));
	}
	
	/**
	 * 浠庢湇鍔″櫒绔皟鍙栨瘡鏃ユ帹鑽愮殑璇濋
	 * 鍦ㄥ疄闄呰繍琛岀殑鏃跺�欏簲璇ュ崟鐙紑杈熶竴鏉＄嚎绋嬭繘琛屽姞杞�
	 */
	public void loadEverydayTopicItems(){
		//姣忔棩鎺ㄨ崘10鏉�
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
			((TextView)(Item_List.get(i).getChildAt(1))).setText("鎴戜负瀹ゅ弸鏉ュ緛濠�");
			((TextView)(Item_List.get(i).getChildAt(2))).setText("宸插埗閫�208涓崱鐗�");
			((ImageView)(Item_List.get(i).getChildAt(3))).setImageResource(R.drawable.list_topic_category_video);
		}
	}
	
	/**
	 * 浠庢湇鍔″櫒绔皟鍙栨垜鍏虫敞鐨勮瘽棰橈紝瑁呰浇鍒癿y_follow_topic_items杩欎釜List<Topic_Item>涓�
	 * 鍦ㄥ疄闄呰繍琛岀殑鏃跺�欏簲璇ュ崟鐙紑杈熶竴鏉＄嚎绋嬭繘琛屽姞杞�
	 */
	public void loadMyFollowTopicItems(){
		//棰勮鍔犺浇10鏉�
		for(int i=0 ; i<10; i++){
			 Topic_Item item = new Topic_Item();
		     item.setTopicIcon(R.drawable.list_topic_icon);
		     item.setTopicTitle("鏍″洯涓偅浜涜嚜甯︽妧鑳界殑B");
		     item.setTopicContentNumber(1024);
		     item.setTopicUpdateNumber(35);
		     my_follow_topic_items.add(item);
		}
	}
	
	/**
	 * 灏嗘垜鍏虫敞鐨勮瘽棰樹腑鐨勬暟鎹杞藉埌RecycleView涓紝骞惰瀹氱偣鍑讳簨浠剁殑鐩戝惉鍣�
	 */
	public void setMyFollowTopicRecyclerView(){

        //榛樿鍔ㄧ敾鏁堟灉
		my_follow_topic_recyclerView.setItemAnimator(new DefaultItemAnimator());
        //璁剧疆甯冨眬绠＄悊鍣紝绗笁涓弬鏁颁负鏄惁閫嗗悜甯冨眬
		linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
		my_follow_topic_recyclerView.setLayoutManager(linearLayoutManager);
        //鍙互鎻愰珮鏁堢巼
		my_follow_topic_recyclerView.setHasFixedSize(true);
		//鏂板缓閫傞厤鍣�
        adapter = new TopicListAdapter();
       
        adapter.addDatas(my_follow_topic_items);
        //鍔犺浇澶撮儴
        adapter.setHeaderView(find_recommend_page_head);
        //鍔犺浇灏鹃儴
        adapter.setFooterView(refresh_footer_view);
        
        //璁剧疆閫傞厤鍣�
        my_follow_topic_recyclerView.setAdapter(adapter);
        //闅愯棌鍏ㄥ睆鐨凩oading
        RelativeLayout loadingPage =( RelativeLayout)find_recommend_view.findViewById(R.id.loading_start_for_recommend_page);
        loadingPage.setVisibility(View.GONE);
        
        //璁剧疆鐩戝惉鍣�,褰撹瘽棰樺垪琛ㄤ腑鐨勬煇涓�椤硅鍗曞嚮鐨勬椂鍊欏洖璋�
        adapter.setOnItemClickListener(new TopicListItemListener(context));
    }
	
	  /**
     * 鎺ㄨ崘椤甸潰涓殑涓嬫媺鍒锋柊鐩戝惉鍣�
     */
    class RecommendPageRefreshListener implements OnRefreshListener{

    	@Override
    	public void onRefresh() {
    		
    		//杩欓噷涓昏鏄鍏虫敞鐨勮瘽棰樺垪琛ㄧ殑鏇存柊
    		ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
			for(int i=0 ; i<5; i++){
				 Topic_Item item = new Topic_Item();
			     item.setTopicIcon(R.drawable.list_topic_icon);
			     item.setTopicTitle("鎴戜粠鏉ユ病鏈夎杩囩殑绉樺瘑");
			     item.setTopicContentNumber(389);
			     item.setTopicUpdateNumber(8);
			     newDatas.add(item);
			}  
			
			adapter.refreshPullDown(newDatas);
    		
    		refresh_bar_for_recommend_page.setRefreshing(false);  
    	}
    }
    
    
    /**
     * 鎺ㄨ崘椤甸潰涓殑鍔犺浇鏇村鍒锋柊鐩戝惉鍣�
     */
    class RecommendPageOnScrollListener extends RecyclerView.OnScrollListener{

    	int lastVisibleItem;
    	
    	@Override  
    	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {  
    		super.onScrollStateChanged(recyclerView, newState);  
    		
    		if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {  

    			TextView text = (TextView)refresh_footer_view.findViewById(R.id.fresh_footer_text);
    			text.setText("鍔姏鍔犺浇涓�...");
    			
    			//璁块棶鏈嶅姟鍣ㄨ繘琛屾暟鎹殑璋冨彇
    			//娉ㄦ剰锛氬鏋滃凡缁忔槸鏈�鍚庝竴椤碉紝閭ｄ箞FooterView搴旇娑堝け锛屽苟涓斾笉鍐嶆帴鏀舵粴鍔ㄤ簨浠�
    			ArrayList<Topic_Item> newDatas = new ArrayList<Topic_Item>();
    			for(int i=0 ; i<5; i++){
    				 Topic_Item item = new Topic_Item();
    			     item.setTopicIcon(R.drawable.list_topic_icon);
    			     item.setTopicTitle("浠婂ぉ鎴戣鍕囨暍涓�娆�");
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
