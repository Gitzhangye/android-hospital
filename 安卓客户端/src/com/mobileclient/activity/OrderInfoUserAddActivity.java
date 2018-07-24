package com.mobileclient.activity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.service.TimeSlotService;
public class OrderInfoUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	//ҽ����� 
	private String doctorNo; 
	// ����ԤԼ���ڿؼ�
	private DatePicker dp_orderDate;
	// ����ԤԼʱ���������
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null;
	/*ԤԼʱ��ι���ҵ���߼���*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	  
	protected String carmera_path;
	/*Ҫ�����ԤԼ��Ϣ*/
	OrderInfo orderInfo = new OrderInfo();
	/*ԤԼ����ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ԤԼ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_user_add); 
		  
		Bundle extras = this.getIntent().getExtras();
		doctorNo = extras.getString("doctorNo");
		
		dp_orderDate = (DatePicker)this.findViewById(R.id.dp_orderDate);
		spinner_timeSlotObj = (Spinner) findViewById(R.id.Spinner_timeSlotObj);
		// ��ȡ���е�ԤԼʱ���
		try {
			timeSlotList = timeSlotService.QueryTimeSlot(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int timeSlotCount = timeSlotList.size();
		timeSlotObj_ShowText = new String[timeSlotCount];
		for(int i=0;i<timeSlotCount;i++) { 
			timeSlotObj_ShowText[i] = timeSlotList.get(i).getTimeSlotName();
		}
		// ����ѡ������ArrayAdapter��������
		timeSlotObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSlotObj_ShowText);
		// ���������б�ķ��
		timeSlotObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_timeSlotObj.setAdapter(timeSlotObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_timeSlotObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setTimeSlotObj(timeSlotList.get(arg2).getTimeSlotId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_timeSlotObj.setVisibility(View.VISIBLE);
		  
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ԤԼ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					orderInfo.setDoctor(doctorNo); 
					/*��ȡԤԼ����*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					orderInfo.setOrderDate(new Timestamp(orderDate.getTime())); 
					orderInfo.setIntroduce("--"); 
					Declare declare = (Declare) OrderInfoUserAddActivity.this.getApplication();
					orderInfo.setUesrObj(declare.getUserName());  
					orderInfo.setVisiteStateObj(1); //״̬�Ǵ����� 
					/*����ҵ���߼����ϴ�ԤԼ��Ϣ*/
					OrderInfoUserAddActivity.this.setTitle("�����ϴ�ԤԼ��Ϣ���Ե�...");
					String result = orderInfoService.AddOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
				 
					OrderInfoUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
