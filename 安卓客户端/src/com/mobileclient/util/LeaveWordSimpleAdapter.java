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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
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
    /*第一次装载这个view时=null,就新建一个调用inflate宣誓一个view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.leaveword_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_leaveWordId = (TextView)convertView.findViewById(R.id.tv_leaveWordId);
				holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
				holder.tv_userObj = (TextView)convertView.findViewById(R.id.tv_userObj);
				holder.tv_replyTime = (TextView)convertView.findViewById(R.id.tv_replyTime);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_leaveWordId.setText("留言id：" + mData.get(position).get("leaveWordId").toString());
		holder.tv_title.setText("标题：" + mData.get(position).get("title").toString());
		holder.tv_addTime.setText("留言时间：" + mData.get(position).get("addTime").toString());
		holder.tv_userObj.setText("留言人：" + (new UserInfoService()).GetUserInfo(mData.get(position).get("userObj").toString()).getName());
		holder.tv_replyTime.setText("回复时间：" + mData.get(position).get("replyTime").toString());
		/*返回修改好的view*/
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
