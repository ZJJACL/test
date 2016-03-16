package com.lee.floater.adapters;

import com.lee.floater.R;
import com.lee.floater.items.Topic_Item;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * ��д�����б���������ӿ�
 *
 */
public class TopicListAdapter extends BaseRecyclerAdapter<Topic_Item>{
	
	//��д�õ��б���ֽ��а�
	@Override
	public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.list_topic, parent, false);
		return new ItemViewHolder(view);
	}

	//��ÿ���б����е�ʵ�����ݽ��а�
	//������ʵ�ʵ����ݵ�ʱ��topic_icon �� topic_category_icon�϶���Ҫת��һ��
	@Override
	public void onBind(ViewHolder viewHolder, int RealPosition, Topic_Item data) {
		if(viewHolder instanceof ItemViewHolder) {
			((ItemViewHolder)viewHolder).topic_icon.setImageResource(data.getTopicIcon());
			((ItemViewHolder)viewHolder).topic_title.setText(data.getTopicTitle());
			((ItemViewHolder)viewHolder).topic_content_number.setText("������"+data.getTopicContentNumber()+"����Ƭ");
			((ItemViewHolder)viewHolder).topic_category_icon.setImageResource(data.getTopicCategory());
			((ItemViewHolder)viewHolder).topic_update_number.setText(""+data.getTopicUpdateNumber());
		    }
	}

	//����һ��ItemViewHoder,ָ��ÿ��Item��View�а����Ŀؼ�
   class ItemViewHolder extends BaseRecyclerAdapter.Holder  {
	   
		ImageView topic_icon;
		TextView topic_title;
		TextView topic_content_number;
		ImageView topic_category_icon;
		TextView topic_update_number;//��������ڵ�ǰ�û��Ѿ���ע�Ļ���
		TextView list_topic_separation; //������ӵķָ���

		public ItemViewHolder(View itemView) {
			super(itemView);
			topic_icon = (ImageView) itemView.findViewById(R.id.list_topic_icon);
			topic_title = (TextView) itemView.findViewById(R.id.list_topic_title);
			topic_content_number = (TextView) itemView.findViewById(R.id.list_topic_content_number);
			topic_category_icon = (ImageView) itemView.findViewById(R.id.list_topic_category_icon);
			list_topic_separation=(TextView)itemView.findViewById(R.id.list_topic_separation);
			topic_update_number=(TextView)itemView.findViewById(R.id.list_topic_update_number);
		}
	}
}
