package com.lee.floater.handler;

import java.util.ArrayList;

import com.lee.floater.R;
import com.lee.floater.adapters.TopicListAdapter;
import com.lee.floater.items.Topic_Item;
import com.lee.floater.listeners.TopicListItemListener;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


/**
 * ���д�����_����ҳ���е�����
 */
public class FindCategoryPageHandler {

	Context context;  //��Main������
	public View find_category_view;  //����ҳ���е���View
    public View find_category_page_head ;//����ҳ���ͷ��View����(������/�����ť/ȫ�����⿨��)
    
	public RecyclerView total_topic_list_recyclerView;//ȫ�������б��RecyclerView
	
	//ȫ�������б��е�����List
    public ArrayList<Topic_Item>total_topic_list_items = new ArrayList<Topic_Item>();
    
    //����ҳ��������ɸѡ��ť�Լ�����������
    ImageView sign_new ;
    ImageView sign_new_notice;
    ImageView sign_hot;
    ImageView sign_hot_notice;
    ImageView sign_update;
    ImageView sign_update_notice;
    
    Button button_sign_new;
    Button button_sign_hot;
    Button button_sign_update;
    
    
    /**
	 * ���췽���н�����ҳ��View��Main������
	 */
	public FindCategoryPageHandler (View find_category_view, Context context){
		this.find_category_view = find_category_view ;
		this.context = context;
		findAllViewById();
	}
	
	/**
	 * ���ط���ҳ�е����е�View����
	 */
	public void findAllViewById(){
		
		LayoutInflater inflater = LayoutInflater.from(context);
		
		//������ҳ���е�ͷ��Viewװ��ΪView
        find_category_page_head = inflater.inflate(R.layout.find_category_page_head,null);
        
      //����Ϊȫ�������б��д��RecyclerView
        total_topic_list_recyclerView = 
        		(RecyclerView)find_category_view.findViewById(R.id.total_topic_list_recyclerview);
        
        //���ط���ҳ���е�����ɸѡ�ľ�̬ͼƬ��������ť
        sign_new = (ImageView)find_category_page_head.findViewById(R.id.sign_new);
        sign_new_notice = (ImageView)find_category_page_head.findViewById(R.id.sign_new_notice);
        sign_hot = (ImageView)find_category_page_head.findViewById(R.id.sign_hot);
        sign_hot_notice = (ImageView)find_category_page_head.findViewById(R.id.sign_hot_notice);
        sign_update = (ImageView)find_category_page_head.findViewById(R.id.sign_update);
        sign_update_notice = (ImageView)find_category_page_head.findViewById(R.id.sign_update_notice);
        
        button_sign_new = (Button)find_category_page_head.findViewById(R.id.button_sign_new);
        button_sign_hot =(Button)find_category_page_head.findViewById(R.id.button_sign_hot);
        button_sign_update =(Button)find_category_page_head.findViewById(R.id.button_sign_update);
        
        //Ϊ����ҳ���е��������˰�ť��ӵ��������
        TotalTopicFilterListener filterListener = new TotalTopicFilterListener();
        button_sign_new.setOnClickListener(filterListener);
        button_sign_hot.setOnClickListener(filterListener);
        button_sign_update.setOnClickListener(filterListener);
        
	}
	
	/**
	 * һ�¼��������ǵ��������������˰�ť��ʱ�򣬸��Ķ�Ӧ����ͼ��
	 * ��ʱû���漰��ȫ��������б�Ķ�Ӧ�ĸ���
	 */
	public void setFilterView_New(){
		sign_new.setVisibility(View.INVISIBLE);
	    sign_new_notice.setVisibility(View.VISIBLE);
	    sign_hot.setVisibility(View.VISIBLE);
	    sign_hot_notice.setVisibility(View.INVISIBLE);
	    sign_update.setVisibility(View.VISIBLE);
	    sign_update_notice.setVisibility(View.INVISIBLE);
	}
	public void setFilterView_Hot(){
		sign_new.setVisibility(View.VISIBLE);
	    sign_new_notice.setVisibility(View.INVISIBLE);
	    sign_hot.setVisibility(View.INVISIBLE);
	    sign_hot_notice.setVisibility(View.VISIBLE);
	    sign_update.setVisibility(View.VISIBLE);
	    sign_update_notice.setVisibility(View.INVISIBLE);
	}
	public void setFilterView_Update(){
		sign_new.setVisibility(View.VISIBLE);
	    sign_new_notice.setVisibility(View.INVISIBLE);
	    sign_hot.setVisibility(View.VISIBLE);
	    sign_hot_notice.setVisibility(View.INVISIBLE);
	    sign_update.setVisibility(View.INVISIBLE);
	    sign_update_notice.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 *��������ҳ���е��������˰�ť�ĵ��������
	 */
	class TotalTopicFilterListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.button_sign_new) setFilterView_New();
			if(v.getId()==R.id.button_sign_hot) setFilterView_Hot();
			if(v.getId()==R.id.button_sign_update) setFilterView_Update();
		}
	}
	
	/**
	 * �Ƽ�ҳ���е����ݼ��ء���ʾ��ˢ�µ�����������м���
	 */
	public void Handle (){
		
		//�Է���ҳ����г�ʼ����Ҳ���ǿ�ʼĬ��ѡ�е�������
        setFilterView_New();
        
        //����ȫ�������б��е�����
        loadTotalTopicItems();
        
        //��ȫ�����⻰���е�����װ�ص�RecyclerView��
        setTotalTopicRecyclerView();
	}
	
	/**
	  * ����ȫ�������б��е�����
	  * ʵ����Ӧ���¿���һ���߳̽������ݵļ���
	  */
   public void loadTotalTopicItems(){
   	//Ԥ�����10��
   	for(int i=0 ; i<10; i++){
   		Topic_Item item = new Topic_Item();
   		item.setTopicIcon(R.drawable.list_topic_icon);
   		item.setTopicTitle("У԰����Щ�Դ����ܵ�B");
   		item.setTopicContentNumber(1024);
   		item.setTopicCategory(R.drawable.list_topic_category_picture);
		    total_topic_list_items.add(item);
   	}    	
   	
   }
   
   
   /**
    * ��ȫ�����⻰���е�����װ�ص�RecyclerView��
    */
   public void setTotalTopicRecyclerView(){

       //Ĭ�϶���Ч��
   	total_topic_list_recyclerView.setItemAnimator(new DefaultItemAnimator());
       //���ò��ֹ�����������������Ϊ�Ƿ����򲼾�
   	total_topic_list_recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
       //�������Ч��
   	total_topic_list_recyclerView.setHasFixedSize(true);
		//�½�������
       TopicListAdapter adapter = new TopicListAdapter();
     //����������
       total_topic_list_recyclerView.setAdapter(adapter);
       adapter.addDatas(total_topic_list_items);
       
       //����ͷ��
       adapter.setHeaderView(find_category_page_head);
       
       //���ü�����,����������б��е�ĳһ���ʱ��ص�
       adapter.setOnItemClickListener(new TopicListItemListener(context));
   }
	

}
