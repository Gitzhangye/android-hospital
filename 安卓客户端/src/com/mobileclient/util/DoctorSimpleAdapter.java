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
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
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
    /*��һ��װ�����viewʱ=null,���½�һ������inflate����һ��view*/
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.doctor_list_item, null);
			holder = new ViewHolder();
			try { 
				/*�󶨸�view�����ؼ�*/
				holder.tv_doctorNo = (TextView)convertView.findViewById(R.id.tv_doctorNo);
				holder.tv_departmentObj = (TextView)convertView.findViewById(R.id.tv_departmentObj);
				holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
				holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
				holder.iv_doctorPhoto = (ImageView)convertView.findViewById(R.id.iv_doctorPhoto);
				holder.tv_education = (TextView)convertView.findViewById(R.id.tv_education);
				holder.tv_inDate = (TextView)convertView.findViewById(R.id.tv_inDate);
				holder.tv_visiteTimes = (TextView)convertView.findViewById(R.id.tv_visiteTimes);
			} catch(Exception ex) {}
			/*������view*/
			convertView.setTag(holder);
		}else{
			/*ֱ��ȡ����ǵ�view*/
			holder = (ViewHolder)convertView.getTag();
		}
		/*���ø����ؼ���չʾ����*/
		holder.tv_doctorNo.setText("ҽ����ţ�" + mData.get(position).get("doctorNo").toString());
		holder.tv_departmentObj.setText("���ڿ��ң�" + (new DepartmentService()).GetDepartment(Integer.parseInt(mData.get(position).get("departmentObj").toString())).getDepartmentName());
		holder.tv_name.setText("������" + mData.get(position).get("name").toString());
		holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
		holder.iv_doctorPhoto.setImageBitmap((Bitmap)mData.get(position).get("doctorPhoto"));
		holder.tv_education.setText("ѧ����" + mData.get(position).get("education").toString());
		holder.tv_inDate.setText("��Ժ���ڣ�" + mData.get(position).get("inDate").toString().substring(0, 10));
		holder.tv_visiteTimes.setText("ÿ�ճ��������" + mData.get(position).get("visiteTimes").toString());
		/*�����޸ĺõ�view*/
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
