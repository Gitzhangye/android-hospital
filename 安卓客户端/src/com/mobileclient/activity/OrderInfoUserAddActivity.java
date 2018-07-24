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
	// 声明确定添加按钮
	private Button btnAdd;
	//医生编号 
	private String doctorNo; 
	// 出版预约日期控件
	private DatePicker dp_orderDate;
	// 声明预约时间段下拉框
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null;
	/*预约时间段管理业务逻辑层*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	  
	protected String carmera_path;
	/*要保存的预约信息*/
	OrderInfo orderInfo = new OrderInfo();
	/*预约管理业务逻辑层*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加预约");
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_user_add); 
		  
		Bundle extras = this.getIntent().getExtras();
		doctorNo = extras.getString("doctorNo");
		
		dp_orderDate = (DatePicker)this.findViewById(R.id.dp_orderDate);
		spinner_timeSlotObj = (Spinner) findViewById(R.id.Spinner_timeSlotObj);
		// 获取所有的预约时间段
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
		// 将可选内容与ArrayAdapter连接起来
		timeSlotObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSlotObj_ShowText);
		// 设置下拉列表的风格
		timeSlotObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_timeSlotObj.setAdapter(timeSlotObj_adapter);
		// 添加事件Spinner事件监听
		spinner_timeSlotObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setTimeSlotObj(timeSlotList.get(arg2).getTimeSlotId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_timeSlotObj.setVisibility(View.VISIBLE);
		  
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加预约按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					orderInfo.setDoctor(doctorNo); 
					/*获取预约日期*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					orderInfo.setOrderDate(new Timestamp(orderDate.getTime())); 
					orderInfo.setIntroduce("--"); 
					Declare declare = (Declare) OrderInfoUserAddActivity.this.getApplication();
					orderInfo.setUesrObj(declare.getUserName());  
					orderInfo.setVisiteStateObj(1); //状态是待出诊 
					/*调用业务逻辑层上传预约信息*/
					OrderInfoUserAddActivity.this.setTitle("正在上传预约信息，稍等...");
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
