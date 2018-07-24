package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.UserInfoService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class LeaveWordSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public LeaveWordSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
    ViewHolder holder = null; 
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.leaveword_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_leaveWordId = (TextView)convertView.findViewById(R.id.tv_leaveWordId);
				holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
				holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
				holder.tv_replyTime = (TextView)convertView.findViewById(R.id.tv_replyTime);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_leaveWordId.setText("����id��" + mData.get(position).get("leaveWordId").toString());
		holder.tv_title.setText("���⣺" + mData.get(position).get("title").toString());
		holder.tv_addTime.setText("����ʱ�䣺" + mData.get(position).get("addTime").toString());
		holder.tv_userObj.setText("�����ˣ�" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
		holder.tv_replyTime.setText("�ظ�ʱ�䣺" + mData.get(position).get("replyTime").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_leaveWordId;
    	TextView tv_title;
    	TextView tv_addTime;
    	TextView tv_userObj;
    	TextView tv_replyTime;
    }
} 
