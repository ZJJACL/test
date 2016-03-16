package com.lee.floater.activity;

import java.util.ArrayList;
import com.lee.floater.R;
import com.lee.floater.adapters.CardListAdapter;
import com.lee.floater.items.Card_Item;
import com.lee.floater.support.FloatingButton;
import com.lee.floater.support.FloatingRelativeLayout;
import com.lee.floater.support.SystemBarTintManager;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Window;

public class TopicMainPageActivity extends Activity {
	
	//������ҳ���п�Ƭ�б��RecyclerView
	public RecyclerView topic_main_page_cards_recyclerview;
	
	//������ҳ���п�Ƭ�б��е�����List
    public ArrayList<Card_Item>topic_main_page_cards_list_items = new ArrayList<Card_Item>();
	
    public LinearLayoutManager linearLayoutManager;//������ҳRecyclerView�Ĳ���
    
	public CardListAdapter adapter; //������ҳ��RecyclerView��Adapter
	
	FloatingButton floating_action_button ; //�����Ĵ�����Ƭ��ť
	
	FloatingRelativeLayout topic_main_page_filter_layout ; //������ɸѡ��
	
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      //�趨״̬������ɫ�����汾����4.4ʱ������
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //�˴���������ָ��״̬����ɫ
            tintManager.setStatusBarTintResource(R.color.status);
        }
        //���汾����4.4֮��ǿ�����ر�����
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
        	requestWindowFeature(Window.FEATURE_NO_TITLE);   
        }
        
        
        setContentView(R.layout.topic_main_page);
        
        topic_main_page_cards_recyclerview =
        		(RecyclerView) findViewById(R.id.topic_main_page_cards_recyclerview);
        
        floating_action_button = (FloatingButton)findViewById(R.id.post_card_button);
        
        topic_main_page_filter_layout = (FloatingRelativeLayout)findViewById(R.id.topic_main_page_filter_layout);
        

        //���ػ�����ҳ�еĿ�ƬArrayList
        loadTopicMainPageCardItems();
        
        //��������ҳ�п�Ƭ�б��е�����װ�ص�RecyclerView�У�����ʾ����
        setTopicMainPageCardRecyclerView();
        
	}
	
	/**
	 * ���ػ�����ҳ�еĿ�ƬArrayList
	 */
	public void loadTopicMainPageCardItems(){
		
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
    		topic_main_page_cards_list_items.add(item);
    	}
		
	}
	
	/**
	 * ��������ҳ�п�Ƭ�б��е�����װ�ص�RecyclerView�У�����ʾ����
	 */
	public void setTopicMainPageCardRecyclerView(){
		
		 //Ĭ�϶���Ч��
		topic_main_page_cards_recyclerview.setItemAnimator(new DefaultItemAnimator());
        //���ò��ֹ�����������������Ϊ�Ƿ����򲼾�
		linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		topic_main_page_cards_recyclerview.setLayoutManager(linearLayoutManager);
        //�������Ч��
		topic_main_page_cards_recyclerview.setHasFixedSize(true);
		
		//�½�������
        adapter = new CardListAdapter(TopicMainPageActivity.this);
        adapter.addDatas(topic_main_page_cards_list_items);
        
        //���һ���յ�ͷ��
        LayoutInflater inflater = LayoutInflater.from(this);
        adapter.setHeaderView( inflater.inflate(R.layout.null_bar_for_list_head,null));
        
        //����������
        topic_main_page_cards_recyclerview.setAdapter(adapter);
        
        topic_main_page_cards_recyclerview.setOnScrollListener(new TopicMainPageOnScrollListener());
       
	}
	
	//�����¼�������
	class TopicMainPageOnScrollListener extends  RecyclerView.OnScrollListener{
		@Override  
		    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {  
		        boolean isSignificantDelta = Math.abs(dy) > 4;  
		        if (isSignificantDelta) {  
		            if (dy > 0) {  
		            	floating_action_button.hide();
		            	topic_main_page_filter_layout.hide();
		            } else {  
		            	floating_action_button.show();
		            	topic_main_page_filter_layout.show();
		            }  
		        }  
		    }  
	}
	

}
