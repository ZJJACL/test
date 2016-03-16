package com.lee.floater.handler;

import java.util.ArrayList;
import com.lee.floater.R;
import com.lee.floater.adapters.CardListAdapter;
import com.lee.floater.items.Card_Item;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FollowMainPageHandler {
	
	Context context ; //��Main������
	View follow_main_view ; // ��עҳ�����View
	
	public RecyclerView follow_main_cards_recyclerview;//��עҳ���п�Ƭ�б��RecyclerView
	
	//��עҳ���п�Ƭ�б��е�����List
    public ArrayList<Card_Item>my_follow_cards_list_items = new ArrayList<Card_Item>();
	
	//�ײ�ˢ��View����
    public View refresh_footer_view;
    
    //����ˢ����������
    SwipeRefreshLayout refresh_bar_for_follow_main_page;
	

	
	/**
	 * ���췽���н���עҳ��View��Main������
	 */
	public FollowMainPageHandler(View follow_main_view, Context context){
		this.follow_main_view = follow_main_view ;
		this.context = context;
		findAllViewById();
	}
	
	/**
	 * ���ع�עҳ�е����е�View����
	 */
	public void findAllViewById(){
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		//���ع�עҳ���еĿ�Ƭ�б��RecyclerView
        follow_main_cards_recyclerview=
        		(RecyclerView)follow_main_view.findViewById(R.id.follow_main_cards_recyclerview);
		
      //���б�β���ļ��ظ����XMLװ��ΪView
        refresh_footer_view = inflater.inflate(R.layout.refresh_footer_view,null);
        
      //���ع�עҳ���ˢ����SwipeRefreshLayout
        refresh_bar_for_follow_main_page = 
        		(SwipeRefreshLayout)follow_main_view.findViewById(R.id.refresh_bar_for_follow_main_page);  
        //Ϊ���ˢ������ӹ�����ɫ
        refresh_bar_for_follow_main_page.setColorSchemeResources(R.color.color1, R.color.color2,R.color.color3, R.color.color4);
        //Ϊ��עҳ���е�ˢ�¿ؼ���Ӽ�����
        refresh_bar_for_follow_main_page.setOnRefreshListener(new followMainPageRefreshListener());
        //Ϊ��ʵ������ˢ�£�������ҪΪ��עҳ���RecyclerView��ӹ����¼�������
        follow_main_cards_recyclerview.setOnScrollListener(new followMainPageOnScrollListener());
        
	}
	
	/**
	 * �Ƽ�ҳ���е����ݼ��ء���ʾ��ˢ�µ�����������м���
	 */
	public void Handle (){
		
		 //���ع�עҳ���п�Ƭ�б��е�����
        loadMyFollowCardItems();
        
        //����עҳ���п�Ƭ�б��е�����װ�ص�RecyclerView��
        setMyFollowCardRecyclerView();
        
	}
	
	 /**
     * ���ع�עҳ���п�Ƭ�б��е�����
     */
    public void loadMyFollowCardItems(){
    	//Ԥ�����10��
    	for(int i=0 ; i<10; i++){
    		Card_Item item = new Card_Item();
    		item.setCardUserIcon(R.drawable.card_user_icon);
    		item.setCardUserName("����Ů����");
    		item.setCardUserUniversity("³Ѹ����ѧԺ�ִ�����");
    		item.setCardPostTime("���� 20��23");
    		item.setCardImgTextPicture(R.drawable.card_imgtext_picture);
    		item.setCardTextContent("�ո�Ϊһ���ǳ���Ҫ�����������赸��ʲô�¶��������ϵ�΢����������������С��");
    		item.setCardFirstAuthor("ԭ���ߣ���溵t");
    		item.setCardFromTopic("# ��УУ������ѡ #");
    		item.setCardBottomOperateChart(R.drawable.card_bottom_operate_chart);
    		item.setCardBottomOperatePraise(R.drawable.card_bottom_operate_praise);
    		item.setCardBottomTextHello("�ȿ�");
    		item.setCardBottomTextComment("32");
    		item.setCardBottomTextForward("ת��");
    		item.setCardBottomTextPraise("Nice");
    		my_follow_cards_list_items.add(item);
    	}    	
    }
    
    
    /**
     * ����עҳ���п�Ƭ�б��е�����װ�ص�RecyclerView��
     */
    public void setMyFollowCardRecyclerView(){
    	//Ĭ�϶���Ч��
    	follow_main_cards_recyclerview.setItemAnimator(new DefaultItemAnimator());
        //���ò��ֹ�����������������Ϊ�Ƿ����򲼾�
    	follow_main_cards_recyclerview.setLayoutManager(new LinearLayoutManager( context , LinearLayoutManager.VERTICAL, false));
        //�������Ч��
    	follow_main_cards_recyclerview.setHasFixedSize(true);
		//�½�������
        CardListAdapter adapter = new CardListAdapter(context);
      	//����������
        follow_main_cards_recyclerview.setAdapter(adapter);
        adapter.addDatas(my_follow_cards_list_items);
        
        adapter.setFooterView(refresh_footer_view);
        
    }
    
    /**
     * ��עҳ���е�����ˢ�¼�����
     */
    class followMainPageRefreshListener implements OnRefreshListener{

    	@Override
    	public void onRefresh() {
    		
    		Card_Item item = new Card_Item();
    		item.setCardUserIcon(R.drawable.card_user_icon);
    		item.setCardUserName("��������");
    		item.setCardUserUniversity("��������ѧԺ����");
    		item.setCardPostTime("���� 20��23");
    		item.setCardImgTextPicture(R.drawable.card_imgtext_picture);
    		item.setCardTextContent("������ڳɹ��ˣ��������ܹ�˳���Ľ�����һ���׶��ˣ����ˣ����ˣ�");
    		item.setCardFirstAuthor("ԭ���ߣ���溵t");
    		item.setCardFromTopic("# ��УУ������ѡ #");
    		item.setCardBottomOperateChart(R.drawable.card_bottom_operate_chart);
    		item.setCardBottomOperatePraise(R.drawable.card_bottom_operate_praise);
    		item.setCardBottomTextHello("�ȿ�");
    		item.setCardBottomTextComment("32");
    		item.setCardBottomTextForward("ת��");
    		item.setCardBottomTextPraise("Nice");
    		
    		ArrayList<Card_Item> newDatas = new ArrayList<Card_Item>();
    		newDatas.add(item);
    		
    		 CardListAdapter adapter = 
    				 (CardListAdapter)follow_main_cards_recyclerview.getAdapter();
    		
    		 adapter.refreshPullDown(newDatas);
    		 follow_main_cards_recyclerview.scrollToPosition(0);
    		 
    		refresh_bar_for_follow_main_page.setRefreshing(false);  
    	}
    }
    
    
    /**
     * ��עҳ���еļ��ظ���ˢ�¼�����
     */
    class followMainPageOnScrollListener extends RecyclerView.OnScrollListener{
    	
    	LinearLayoutManager linearLayoutManager;
    	CardListAdapter adapter;
    	int lastVisibleItem;
    	
    	@Override  
    	public void onScrollStateChanged(RecyclerView recyclerView, int newState) {  
    		super.onScrollStateChanged(recyclerView, newState);  
    		
    		linearLayoutManager =  (LinearLayoutManager)recyclerView.getLayoutManager();
    		adapter = (CardListAdapter)recyclerView.getAdapter();
    		
    		if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {  
    
    			//��ˢ�´���ͷ��ʷ������������ݵĵ�ȡ
    			TextView text = (TextView)refresh_footer_view.findViewById(R.id.fresh_footer_text);
    			text.setText("Ŭ��������...");
    			
    		}  
    	}
    	
    	@Override  
    	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {  
    		super.onScrolled(recyclerView,dx, dy);  
    		lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();  
    	}	
    }

}
