package com.mobileclient.util;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mobileclient.activity.R;
import com.mobileclient.app.Declare;
import com.mobileclient.service.DoctorService;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.VisitStateService;

public class OrderInfoSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public OrderInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.orderinfo_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_uesrObj = (TextView)convertView.findViewById(R.id.tv_uesrObj);
				holder.tv_doctor = (TextView)convertView.findViewById(R.id.tv_doctor);
				holder.tv_orderDate = (TextView)convertView.findViewById(R.id.tv_orderDate);
				holder.tv_timeSlotObj = (TextView)convertView.findViewById(R.id.tv_timeSlotObj);
				holder.tv_visiteStateObj = (TextView)convertView.findViewById(R.id.tv_visiteStateObj);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_uesrObj.setText("ԤԼ�û���" + (new UserInfoService()).GetUserInfo(mData.get(position).get("uesrObj").toString()).getName());
		holder.tv_doctor.setText("ԤԼҽ����" + (new DoctorService()).GetDoctor(mData.get(position).get("doctor").toString()).getName());
		holder.tv_orderDate.setText("ԤԼ���ڣ�" + mData.get(position).get("orderDate").toString().substring(0, 10));
		holder.tv_timeSlotObj.setText("ԤԼʱ��Σ�" + (new TimeSlotService()).GetTimeSlot(Integer.parseInt(mData.get(position).get("timeSlotObj").toString())).getTimeSlotName());
		holder.tv_visiteStateObj.setText("����״̬��" + (new VisitStateService()).GetVisitState(Integer.parseInt(mData.get(position).get("visiteStateObj").toString())).getVisitStateName());
		
		Activity activity = (Activity) this.context;
		Declare declare = (Declare)activity.getApplication();
		if(declare.getIdentify().equals("user")) {
			holder.tv_uesrObj.setVisibility(View.GONE);
		} else if(declare.getIdentify().equals("doctor")) {
			holder.tv_doctor.setVisibility(View.GONE);
		}
		
		/*�����޸ĺõ�view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_uesrObj;
    	TextView tv_doctor;
    	TextView tv_orderDate;
    	TextView tv_timeSlotObj;
    	TextView tv_visiteStateObj;
    }
} 
