package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.DepartmentService;
import com.mobileclient.activity.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class DoctorSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    public DoctorSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) { 
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
			convertView = mInflater.inflate(R.layout.doctor_list_item, null);
			holder = new ViewHolder();
			try { 
				/*绑定该view各个控件*/
				holder.tv_doctorNo = (TextView)convertView.findViewById(R.id.tv_doctorNo);
				holder.tv_departmentObj = (TextView)convertView.findViewById(R.id.tv_departmentObj);
				holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
				holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
				holder.iv_doctorPhoto = (ImageView)convertView.findViewById(R.id.iv_doctorPhoto);
				holder.tv_education = (TextView)convertView.findViewById(R.id.tv_education);
				holder.tv_inDate = (TextView)convertView.findViewById(R.id.tv_inDate);
				holder.tv_visiteTimes = (TextView)convertView.findViewById(R.id.tv_visiteTimes);
			} catch(Exception ex) {}
			/*标记这个view*/
			convertView.setTag(holder);
		}else{
			/*直接取出标记的view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*设置各个控件的展示内容*/
		holder.tv_doctorNo.setText("医生编号：" + mData.get(position).get("doctorNo").toString());
		holder.tv_departmentObj.setText("所在科室：" + (new DepartmentService()).GetDepartment(Integer.parseInt(mData.get(position).get("departmentObj").toString())).getDepartmentName());
		holder.tv_name.setText("姓名：" + mData.get(position).get("name").toString());
		holder.tv_sex.setText("性别：" + mData.get(position).get("sex").toString());
		holder.iv_doctorPhoto.setImageBitmap((Bitmap)mData.get(position).get("doctorPhoto"));
		holder.tv_education.setText("学历：" + mData.get(position).get("education").toString());
		holder.tv_inDate.setText("入院日期：" + mData.get(position).get("inDate").toString().substring(0, 10));
		holder.tv_visiteTimes.setText("每日出诊次数：" + mData.get(position).get("visiteTimes").toString());
		/*返回修改好的view*/
		return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_doctorNo;
    	TextView tv_departmentObj;
    	TextView tv_name;
    	TextView tv_sex;
    	ImageView iv_doctorPhoto;
    	TextView tv_education;
    	TextView tv_inDate;
    	TextView tv_visiteTimes;
    }
} 
