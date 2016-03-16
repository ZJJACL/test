package com.lee.floater.activity;

import com.lee.floater.R;
import com.lee.floater.support.SystemBarTintManager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

public class CardDetailPageActivity extends Activity {
	
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
        
        
        setContentView(R.layout.card_detail_page);
        
        
	}

}
