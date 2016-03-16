package com.lee.floater.activity;

import java.util.ArrayList;
import java.util.List;
import com.lee.floater.R;
import com.lee.floater.adapters.ScreenPagerAdapter;
import com.lee.floater.handler.FindCategoryPageHandler;
import com.lee.floater.handler.FollowMainPageHandler;
import com.lee.floater.handler.MineMainPageHandler;
import com.lee.floater.handler.RecommendPageHandler;
import com.lee.floater.support.SystemBarTintManager;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Main extends Activity implements OnPageChangeListener,OnClickListener  {
	
	public ViewPager viewPager ;//5����ҳ��ViewPager
    public int currentPage = 0; //��¼5��ҳ����˭���ڻ״̬
	
	public RecommendPageHandler recommendPageHandler ; //�Ƽ�ҳ��Ĵ�����
	public FindCategoryPageHandler findCategoryPageHandler ; //����ҳ��Ĵ�����
	public FollowMainPageHandler followMainPageHandler ; //��עҳ��Ĵ�����
	public MineMainPageHandler mineMainPageHandler ; //�ҵ�ҳ��Ĵ�����
	
    //5����ҳ�������
    public View find_recommend_view;
    public View find_category_view;
    public View follow_main_view;
    public View message_main_view;
    public View mine_main_view;
    
    //��������������̬Ԫ������
    TextView title_recommend ;
    TextView title_category;
    TextView title_follow;
    TextView title_message;
    TextView title_mine;

    //�ײ�����������̬Ԫ������
    ImageView find_nav_icon_notice;
    ImageView find_nav_icon;
    TextView nav_find;
    
    ImageView follow_nav_icon_notice;
    ImageView follow_nav_icon;
    TextView nav_follow;
    
    ImageView message_nav_icon_notice;
    ImageView message_nav_icon;
    TextView nav_message;
    
    ImageView mine_nav_icon_notice;
    ImageView mine_nav_icon;
    TextView nav_mine;
    
    //�����͵ײ������ɵ������������
    Button button_title_post;
    Button button_title_audio;
    Button button_title_recommend;
    Button button_title_category;
    Button button_nav_find;
    Button button_nav_follow;
    Button button_nav_message;
    Button button_nav_mine;


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
        
        setContentView(R.layout.main_slide_page);
        findAllViewById();

       //װ��ViewPager�е�5��Viewҳ��
        setViewPager();
      //�Զ������������ײ���������ͼ����������г�ʼ��
        initNavIcon();
        
        //�����Ƽ�ҳ�������
        recommendPageHandler = new RecommendPageHandler(find_recommend_view, Main.this);
        recommendPageHandler.Handle();
        
        //������ҳ_����ҳ�������
        findCategoryPageHandler = new FindCategoryPageHandler(find_category_view,Main.this);
        findCategoryPageHandler.Handle();
        
        //�����עҳ�������
        followMainPageHandler = new FollowMainPageHandler(follow_main_view, Main.this );
        followMainPageHandler.Handle();
        
        //�����ҵ�ҳ�������
        mineMainPageHandler = new MineMainPageHandler( mine_main_view,Main.this);
        mineMainPageHandler.Handle();
        
    }
    
    /**
     * ͳһͨ��findViewById()�Բ����еĸ���������м���
     */
    public void findAllViewById(){

        //��5��ҳ���XML�ļ�װ��ΪView
        LayoutInflater inflater = LayoutInflater.from(this);
        find_recommend_view = inflater.inflate(R.layout.find_recommend_page,null);
        find_category_view = inflater.inflate(R.layout.find_category_page, null);
        follow_main_view = inflater.inflate(R.layout.follow_main_page, null);
        message_main_view = inflater.inflate(R.layout.message_main_page, null);
        mine_main_view = inflater.inflate(R.layout.mine_main_page, null);
        
        //���ض����������͵ײ���������Ԫ��
        title_recommend = (TextView)findViewById(R.id.title_recommend);
        title_category=(TextView)findViewById(R.id.title_category);
        title_follow = (TextView)findViewById(R.id.title_follow);
        title_message=(TextView)findViewById(R.id.title_message);
        title_mine=(TextView)findViewById(R.id.title_mine);
        find_nav_icon_notice=(ImageView)findViewById(R.id.find_nav_icon_notice);
        find_nav_icon=(ImageView)findViewById(R.id.find_nav_icon);
        nav_find = (TextView)findViewById(R.id.nav_find);
        follow_nav_icon_notice=(ImageView)findViewById(R.id.follow_nav_icon_notice);
        follow_nav_icon =(ImageView)findViewById(R.id.follow_nav_icon);
        nav_follow = (TextView)findViewById(R.id.nav_follow);
        message_nav_icon_notice =(ImageView)findViewById(R.id.message_nav_icon_notice);
        message_nav_icon=(ImageView)findViewById(R.id.message_nav_icon);
        nav_message = (TextView)findViewById(R.id.nav_message);
        mine_nav_icon_notice = (ImageView)findViewById(R.id.mine_nav_icon_notice);
        mine_nav_icon =(ImageView)findViewById(R.id.mine_nav_icon);
        nav_mine = (TextView)findViewById(R.id.nav_mine);
        
        button_title_post = (Button)findViewById(R.id.button_title_post);
        button_title_audio = (Button)findViewById(R.id.button_title_audio);
        button_title_recommend= (Button)findViewById(R.id.button_title_recommend);
        button_title_category= (Button)findViewById(R.id.button_title_category);
        button_nav_find= (Button)findViewById(R.id.button_nav_find);
        button_nav_follow= (Button)findViewById(R.id.button_nav_follow);
        button_nav_message= (Button)findViewById(R.id.button_nav_message);
        button_nav_mine= (Button)findViewById(R.id.button_nav_mine);
        
        //Ϊ�����������͵ײ��������е�Buttonע�������
        button_title_post.setOnClickListener(Main.this);
        button_title_audio.setOnClickListener(Main.this);
        button_title_recommend.setOnClickListener(Main.this);
        button_title_category.setOnClickListener(Main.this);
        button_nav_find.setOnClickListener(Main.this);
        button_nav_follow.setOnClickListener(Main.this);
        button_nav_message.setOnClickListener(Main.this);
        button_nav_mine.setOnClickListener(Main.this);

      //����5����Ҫҳ���ViewPage
        viewPager = (ViewPager)findViewById(R.id.viewPaper);
        
    }
    
    
    /**
     *ΪViewPager�໬����Adapter�����Ƚ�5��XMLҳ���View����װ��List��
     **/
    public void setViewPager(){
    	List<View> list_for_five_mainView = new ArrayList<View>() ;
        
        list_for_five_mainView.add(find_recommend_view);
        list_for_five_mainView.add(find_category_view);
        list_for_five_mainView.add(follow_main_view);
        list_for_five_mainView.add(message_main_view);
        list_for_five_mainView.add(mine_main_view);
        
        ScreenPagerAdapter sPagerAdapter = new ScreenPagerAdapter
        		                                                                               (list_for_five_mainView);
      //Ϊ5����ҳ��ViewPager����Adapter
      viewPager.setAdapter(sPagerAdapter);
      //��ΪViewPager���������
      viewPager.setOnPageChangeListener(this);
    	
    }
    
    /**
     * �Զ������������ײ���������ͼ����������г�ʼ����Ҳ�ǵ�ǰҳ��Ϊ0ʱ
     **/
    public void initNavIcon(){
    	currentPage = 0 ;
    	title_recommend.setVisibility(View.VISIBLE);
        title_category.setVisibility(View.VISIBLE);
    	title_recommend.setTextColor(Color.parseColor("#ffffff"));     
        title_category.setTextColor(Color.parseColor("#66d7e5"));
        title_follow.setVisibility(View.GONE);
        title_message.setVisibility(View.GONE);
        title_mine.setVisibility(View.GONE);
        find_nav_icon_notice.setVisibility(View.VISIBLE);
        find_nav_icon.setVisibility(View.GONE);
        nav_find.setTextColor(Color.parseColor("#2fb9c3"));   
        follow_nav_icon_notice.setVisibility(View.GONE);
        follow_nav_icon.setVisibility(View.VISIBLE);
        nav_follow.setTextColor(Color.parseColor("#333333")); 
        message_nav_icon_notice.setVisibility(View.GONE); 
        message_nav_icon.setVisibility(View.VISIBLE);
        nav_message.setTextColor(Color.parseColor("#333333"));
        mine_nav_icon_notice.setVisibility(View.GONE); 
        mine_nav_icon.setVisibility(View.VISIBLE);
        nav_mine.setTextColor(Color.parseColor("#333333")); 
    }
    
    /**
     * ���¼��������ֱ���ҳ�滬����ʱ�򶥲�����͵ײ�����Ԫ�صı仯����
     **/
    public void changeNavForSlide_1(){
    	title_recommend.setVisibility(View.VISIBLE);
        title_category.setVisibility(View.VISIBLE);
    	title_recommend.setTextColor(Color.parseColor("#66d7e5"));     
        title_category.setTextColor(Color.parseColor("#ffffff"));
        title_follow.setVisibility(View.GONE);
        title_message.setVisibility(View.GONE);
        title_mine.setVisibility(View.GONE);
        find_nav_icon_notice.setVisibility(View.VISIBLE);
        find_nav_icon.setVisibility(View.GONE);
        nav_find.setTextColor(Color.parseColor("#2fb9c3"));   
        follow_nav_icon_notice.setVisibility(View.GONE);
        follow_nav_icon.setVisibility(View.VISIBLE);
        nav_follow.setTextColor(Color.parseColor("#333333")); 
        message_nav_icon_notice.setVisibility(View.GONE); 
        message_nav_icon.setVisibility(View.VISIBLE);
        nav_message.setTextColor(Color.parseColor("#333333"));
        mine_nav_icon_notice.setVisibility(View.GONE); 
        mine_nav_icon.setVisibility(View.VISIBLE);
        nav_mine.setTextColor(Color.parseColor("#333333")); 
    }
    public void changeNavForSlide_2(){
    	title_recommend.setVisibility(View.GONE);
        title_category.setVisibility(View.GONE);
		title_follow.setVisibility(View.VISIBLE);
        title_message.setVisibility(View.GONE);
        title_mine.setVisibility(View.GONE);
        find_nav_icon_notice.setVisibility(View.GONE);
        find_nav_icon.setVisibility(View.VISIBLE);
        nav_find.setTextColor(Color.parseColor("#333333"));   
        follow_nav_icon_notice.setVisibility(View.VISIBLE);
        follow_nav_icon.setVisibility(View.GONE);
        nav_follow.setTextColor(Color.parseColor("#2fb9c3")); 
        message_nav_icon_notice.setVisibility(View.GONE); 
        message_nav_icon.setVisibility(View.VISIBLE);
        nav_message.setTextColor(Color.parseColor("#333333"));
        mine_nav_icon_notice.setVisibility(View.GONE); 
        mine_nav_icon.setVisibility(View.VISIBLE);
        nav_mine.setTextColor(Color.parseColor("#333333"));
    }
    public void changeNavForSlide_3(){
    	title_recommend.setVisibility(View.GONE);
        title_category.setVisibility(View.GONE);
		title_follow.setVisibility(View.GONE);
        title_message.setVisibility(View.VISIBLE);
        title_mine.setVisibility(View.GONE);
        find_nav_icon_notice.setVisibility(View.GONE);
        find_nav_icon.setVisibility(View.VISIBLE);
        nav_find.setTextColor(Color.parseColor("#333333"));   
        follow_nav_icon_notice.setVisibility(View.GONE);
        follow_nav_icon.setVisibility(View.VISIBLE);
        nav_follow.setTextColor(Color.parseColor("#333333")); 
        message_nav_icon_notice.setVisibility(View.VISIBLE); 
        message_nav_icon.setVisibility(View.GONE);
        nav_message.setTextColor(Color.parseColor("#2fb9c3"));
        mine_nav_icon_notice.setVisibility(View.GONE); 
        mine_nav_icon.setVisibility(View.VISIBLE);
        nav_mine.setTextColor(Color.parseColor("#333333")); 
    }
    public void changeNavForSlide_4(){
    	title_recommend.setVisibility(View.GONE);
        title_category.setVisibility(View.GONE);
		title_follow.setVisibility(View.GONE);
        title_message.setVisibility(View.GONE);
        title_mine.setVisibility(View.VISIBLE);
        find_nav_icon_notice.setVisibility(View.GONE);
        find_nav_icon.setVisibility(View.VISIBLE);
        nav_find.setTextColor(Color.parseColor("#333333"));   
        follow_nav_icon_notice.setVisibility(View.GONE);
        follow_nav_icon.setVisibility(View.VISIBLE);
        nav_follow.setTextColor(Color.parseColor("#333333")); 
        message_nav_icon_notice.setVisibility(View.GONE); 
        message_nav_icon.setVisibility(View.VISIBLE);
        nav_message.setTextColor(Color.parseColor("#333333"));
        mine_nav_icon_notice.setVisibility(View.VISIBLE); 
        mine_nav_icon.setVisibility(View.GONE);
        nav_mine.setTextColor(Color.parseColor("#2fb9c3"));
    }
    
    /**
     * �������õ�ǰ��Screen��ͼ
     * */
    public void setCurrentPage(int position){
        if(position< 0 || position >= 5){
            return ;
        }
        viewPager.setCurrentItem(position);
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ��ViewPager������ʱ�򣬸÷���������
	 */
	@Override
	public void onPageSelected(int arg0) {
		currentPage = arg0 ;
		if(arg0 == 0)initNavIcon();
		if(arg0 == 1)changeNavForSlide_1();
		if(arg0 == 2)changeNavForSlide_2();
		if(arg0 == 3)changeNavForSlide_3();
		if(arg0 == 4)changeNavForSlide_4();
		}

	/**
	 * �������������͵ײ���������Button�ĵ���¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        	case R.id.button_title_post:
        		//TODO
        		break;

        	case R.id.button_title_audio:
        		//TODO
        		break;
        		
        	case R.id.button_title_recommend:
        		setCurrentPage(0);
        		initNavIcon();
        		break;
        		
        	case R.id.button_title_category:
        		setCurrentPage(1);
        		changeNavForSlide_1();
        		break;
        		
        	case R.id.button_nav_find:
        		if(title_recommend.getCurrentTextColor() == 0xffffffff){
        			setCurrentPage(0);
        			initNavIcon();
        		}
        		else{
        			setCurrentPage(1);
            		changeNavForSlide_1();
        		}
        		break;
        		
        	case R.id.button_nav_follow:
        		setCurrentPage(2);
        		changeNavForSlide_2();
        		break;
        		
        	case R.id.button_nav_message:
        		setCurrentPage(3);
        		changeNavForSlide_3();
        		break;
        	
        	case R.id.button_nav_mine:
        		setCurrentPage(4);
        		changeNavForSlide_4();
        		break;
        	}

     }
	
}







