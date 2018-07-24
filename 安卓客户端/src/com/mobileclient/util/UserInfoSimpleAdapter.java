package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class UserInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public UserInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.userinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_user_name = (TextView)convertView.findViewById(R.id.tv_user_name);
				holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
				holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
				holder.iv_userPhoto = (ImageView)convertView.findViewById(R.id.iv_userPhoto);
				holder.tv_birthday = (TextView)convertView.findViewById(R.id.tv_birthday);
				holder.tv_jiguan = (TextView)convertView.findViewById(R.id.tv_jiguan);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_user_name.setText("�û�����" + mData.get(position).get("user_name").toString());
		holder.tv_name.setText("������" + mData.get(position).get("name").toString());
		holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
		holder.iv_userPhoto.setImageBitmap((Bitmap)mData.get(position).get("userPhoto"));
		holder.tv_birthday.setText("�������ڣ�" + mData.get(position).get("birthday").toString().substring(0, 10));
		holder.tv_jiguan.setText("���᣺" + mData.get(position).get("jiguan").toString());
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_user_name;
    	TextView tv_name;
    	TextView tv_sex;
    	ImageView iv_userPhoto;
    	TextView tv_birthday;
    	TextView tv_jiguan;
    }
} 
