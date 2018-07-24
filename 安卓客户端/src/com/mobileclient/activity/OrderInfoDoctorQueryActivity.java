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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.service.VisitStateService;

public class OrderInfoDoctorQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明预约用户下拉框
	private Spinner spinner_uesrObj;
	private ArrayAdapter<String> uesrObj_adapter;
	private static  String[] uesrObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*用户管理业务逻辑层*/
	private UserInfoService userInfoService = new UserInfoService();
	 
	// 预约日期控件
	private DatePicker dp_orderDate;
	private CheckBox cb_orderDate;
	// 声明预约时间段下拉框
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null; 
	/*时间段管理业务逻辑层*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	// 声明出诊状态下拉框
	private Spinner spinner_visiteStateObj;
	private ArrayAdapter<String> visiteStateObj_adapter;
	private static  String[] visiteStateObj_ShowText  = null;
	private List<VisitState> visitStateList = null; 
	/*出诊状态管理业务逻辑层*/
	private VisitStateService visitStateService = new VisitStateService();
	/*查询过滤条件保存到这个对象中*/
	private OrderInfo queryConditionOrderInfo = new OrderInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询预约条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.orderinfo_doctor_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_uesrObj = (Spinner) findViewById(R.id.Spinner_uesrObj);
		// 获取所有的用户
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		uesrObj_ShowText = new String[userInfoCount+1];
		uesrObj_ShowText[0] = "不限制";
		for(int i=1;i<=userInfoCount;i++) { 
			uesrObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		uesrObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, uesrObj_ShowText);
		// 设置预约用户下拉列表的风格
		uesrObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_uesrObj.setAdapter(uesrObj_adapter);
		// 添加事件Spinner事件监听
		spinner_uesrObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setUesrObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionOrderInfo.setUesrObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_uesrObj.setVisibility(View.VISIBLE);
		 
		dp_orderDate = (DatePicker) findViewById(R.id.dp_orderDate);
		cb_orderDate = (CheckBox) findViewById(R.id.cb_orderDate);
		spinner_timeSlotObj = (Spinner) findViewById(R.id.Spinner_timeSlotObj);
		// 获取所有的时间段
		try {
			timeSlotList = timeSlotService.QueryTimeSlot(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int timeSlotCount = timeSlotList.size();
		timeSlotObj_ShowText = new String[timeSlotCount+1];
		timeSlotObj_ShowText[0] = "不限制";
		for(int i=1;i<=timeSlotCount;i++) { 
			timeSlotObj_ShowText[i] = timeSlotList.get(i-1).getTimeSlotName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		timeSlotObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSlotObj_ShowText);
		// 设置预约时间段下拉列表的风格
		timeSlotObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_timeSlotObj.setAdapter(timeSlotObj_adapter);
		// 添加事件Spinner事件监听
		spinner_timeSlotObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setTimeSlotObj(timeSlotList.get(arg2-1).getTimeSlotId()); 
				else
					queryConditionOrderInfo.setTimeSlotObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_timeSlotObj.setVisibility(View.VISIBLE);
		spinner_visiteStateObj = (Spinner) findViewById(R.id.Spinner_visiteStateObj);
		// 获取所有的出诊状态
		try {
			visitStateList = visitStateService.QueryVisitState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int visitStateCount = visitStateList.size();
		visiteStateObj_ShowText = new String[visitStateCount+1];
		visiteStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=visitStateCount;i++) { 
			visiteStateObj_ShowText[i] = visitStateList.get(i-1).getVisitStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		visiteStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visiteStateObj_ShowText);
		// 设置出诊状态下拉列表的风格
		visiteStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_visiteStateObj.setAdapter(visiteStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_visiteStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setVisiteStateObj(visitStateList.get(arg2-1).getVisitStateId()); 
				else
					queryConditionOrderInfo.setVisiteStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_visiteStateObj.setVisibility(View.VISIBLE);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					
					Declare declare = (Declare) OrderInfoDoctorQueryActivity.this.getApplication();
					queryConditionOrderInfo.setDoctor(declare.getUserName());
					/*获取查询参数*/
					if(cb_orderDate.isChecked()) {
						/*获取预约日期*/
						Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
						queryConditionOrderInfo.setOrderDate(new Timestamp(orderDate.getTime()));
					} else {
						queryConditionOrderInfo.setOrderDate(null);
					} 
					/*操作完成后返回到预约管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(OrderInfoDoctorQueryActivity.this, OrderInfoDoctorListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionOrderInfo", queryConditionOrderInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					OrderInfoDoctorQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
