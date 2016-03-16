package com.lee.floater.handler;

import java.util.ArrayList;

import com.lee.floater.R;
import com.lee.floater.adapters.CardListAdapter;
import com.lee.floater.items.Card_Item;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

/**
 * ���д����ҵ�ҳ���е���������
 */
public class MineMainPageHandler {
	
		public Context context;  //��Main������
		public View mine_main_view ; //�ҵ�ҳ�����View
		
		//������ҳ�е�ͷ��View����(ͷ��/�ǳ�/ѧУ/��ע/������)
	    public View personal_homepage_head;
	    
		public RecyclerView personal_homepage_mine_cards_recyclerview;//�ҵ�ҳ���п�Ƭ�б��RecyclerView
	
		//�ҵĸ�����ҳ�еĿ�Ƭ�б��е�����
	    public ArrayList<Card_Item>personal_mine_cards_list_items=new ArrayList<Card_Item>();
	
	 	/**
		 * ���췽���н�����ҳ��View��Main������
		 */
		public MineMainPageHandler (View mine_main_view, Context context){
			this.mine_main_view = mine_main_view ;
			this.context = context;
			findAllViewById();
		}
		
		/**
		 * �����ҵ�ҳ�е����е�View����
		 */
		public void findAllViewById(){
			
			LayoutInflater inflater = LayoutInflater.from(context);
			
			 //��������ҳ�е�ͷ��Viewװ��ΪView
	        personal_homepage_head=inflater.inflate(R.layout.personal_homepage_head,null);
	        
	        //�����ҵ�ҳ���еĿ�Ƭ�б��RecyclerView
	        personal_homepage_mine_cards_recyclerview=
	        		(RecyclerView)mine_main_view.findViewById(R.id.personal_homepage_mine_cards_recyclerview);
		
		}
		
		/**
		 * �ҵ�ҳ���е��������м���
		 */
		public void Handle (){
			
			//�����ҵ�ҳ���еĿ�Ƭ�б��е�����
	        loadPersonalPageMineCardItems();
	        
	        //���ҵ�ҳ���п�Ƭ�б��е�����װ�ص�RecyclerView��
	        setPersonalPageMineCardRecyclerView();
			
		}
		
		/**
	     * �����ҵ�ҳ���еĿ�Ƭ�б��е�����
	     */
	    public void loadPersonalPageMineCardItems(){
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
	    		personal_mine_cards_list_items.add(item);
	    	}    	
	    }
	    
	    /**
	     * ���ҵ�ҳ���п�Ƭ�б��е�����װ�ص�RecyclerView��
	     */
	    public  void setPersonalPageMineCardRecyclerView(){
	    	//Ĭ�϶���Ч��
	    	personal_homepage_mine_cards_recyclerview.setItemAnimator(new DefaultItemAnimator());
	        //���ò��ֹ�����������������Ϊ�Ƿ����򲼾�
	    	personal_homepage_mine_cards_recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
	    	
	        //�������Ч��
	    	personal_homepage_mine_cards_recyclerview.setHasFixedSize(true);
			//�½�������
	        CardListAdapter adapter = new CardListAdapter(context);
	      //����������
	        personal_homepage_mine_cards_recyclerview.setAdapter(adapter);
	        adapter.addDatas(personal_mine_cards_list_items);
	        
	        //����ͷ��
	        adapter.setHeaderView(personal_homepage_head);
	    }

}
