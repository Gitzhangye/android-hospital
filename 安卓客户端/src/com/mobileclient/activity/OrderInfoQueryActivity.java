package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;

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
import android.widget.EditText;
import android.widget.Spinner;

public class OrderInfoQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ����ԤԼ�û�������
	private Spinner spinner_uesrObj;
	private ArrayAdapter<String> uesrObj_adapter;
	private static  String[] uesrObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ����ԤԼҽ��������
	private Spinner spinner_doctor;
	private ArrayAdapter<String> doctor_adapter;
	private static  String[] doctor_ShowText  = null;
	private List<Doctor> doctorList = null; 
	/*ҽ������ҵ���߼���*/
	private DoctorService doctorService = new DoctorService();
	// ԤԼ���ڿؼ�
	private DatePicker dp_orderDate;
	private CheckBox cb_orderDate;
	// ����ԤԼʱ���������
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null; 
	/*ʱ��ι���ҵ���߼���*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	// ��������״̬������
	private Spinner spinner_visiteStateObj;
	private ArrayAdapter<String> visiteStateObj_adapter;
	private static  String[] visiteStateObj_ShowText  = null;
	private List<VisitState> visitStateList = null; 
	/*����״̬����ҵ���߼���*/
	private VisitStateService visitStateService = new VisitStateService();
	/*��ѯ�����������浽���������*/
	private OrderInfo queryConditionOrderInfo = new OrderInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯԤԼ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_uesrObj = (Spinner) findViewById(R.id.Spinner_uesrObj);
		// ��ȡ���е��û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		uesrObj_ShowText = new String[userInfoCount+1];
		uesrObj_ShowText[0] = "������";
		for(int i=1;i<=userInfoCount;i++) { 
			uesrObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// ����ѡ������ArrayAdapter��������
		uesrObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, uesrObj_ShowText);
		// ����ԤԼ�û������б�ķ��
		uesrObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_uesrObj.setAdapter(uesrObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_uesrObj.setVisibility(View.VISIBLE);
		spinner_doctor = (Spinner) findViewById(R.id.Spinner_doctor);
		// ��ȡ���е�ҽ��
		try {
			doctorList = doctorService.QueryDoctor(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int doctorCount = doctorList.size();
		doctor_ShowText = new String[doctorCount+1];
		doctor_ShowText[0] = "������";
		for(int i=1;i<=doctorCount;i++) { 
			doctor_ShowText[i] = doctorList.get(i-1).getName();
		} 
		// ����ѡ������ArrayAdapter��������
		doctor_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, doctor_ShowText);
		// ����ԤԼҽ�������б�ķ��
		doctor_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_doctor.setAdapter(doctor_adapter);
		// ����¼�Spinner�¼�����
		spinner_doctor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionOrderInfo.setDoctor(doctorList.get(arg2-1).getDoctorNo()); 
				else
					queryConditionOrderInfo.setDoctor("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_doctor.setVisibility(View.VISIBLE);
		dp_orderDate = (DatePicker) findViewById(R.id.dp_orderDate);
		cb_orderDate = (CheckBox) findViewById(R.id.cb_orderDate);
		spinner_timeSlotObj = (Spinner) findViewById(R.id.Spinner_timeSlotObj);
		// ��ȡ���е�ʱ���
		try {
			timeSlotList = timeSlotService.QueryTimeSlot(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int timeSlotCount = timeSlotList.size();
		timeSlotObj_ShowText = new String[timeSlotCount+1];
		timeSlotObj_ShowText[0] = "������";
		for(int i=1;i<=timeSlotCount;i++) { 
			timeSlotObj_ShowText[i] = timeSlotList.get(i-1).getTimeSlotName();
		} 
		// ����ѡ������ArrayAdapter��������
		timeSlotObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, timeSlotObj_ShowText);
		// ����ԤԼʱ��������б�ķ��
		timeSlotObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_timeSlotObj.setAdapter(timeSlotObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_timeSlotObj.setVisibility(View.VISIBLE);
		spinner_visiteStateObj = (Spinner) findViewById(R.id.Spinner_visiteStateObj);
		// ��ȡ���еĳ���״̬
		try {
			visitStateList = visitStateService.QueryVisitState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int visitStateCount = visitStateList.size();
		visiteStateObj_ShowText = new String[visitStateCount+1];
		visiteStateObj_ShowText[0] = "������";
		for(int i=1;i<=visitStateCount;i++) { 
			visiteStateObj_ShowText[i] = visitStateList.get(i-1).getVisitStateName();
		} 
		// ����ѡ������ArrayAdapter��������
		visiteStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visiteStateObj_ShowText);
		// ���ó���״̬�����б�ķ��
		visiteStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_visiteStateObj.setAdapter(visiteStateObj_adapter);
		// ����¼�Spinner�¼�����
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
		// ����Ĭ��ֵ
		spinner_visiteStateObj.setVisibility(View.VISIBLE);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					if(cb_orderDate.isChecked()) {
						/*��ȡԤԼ����*/
						Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
						queryConditionOrderInfo.setOrderDate(new Timestamp(orderDate.getTime()));
					} else {
						queryConditionOrderInfo.setOrderDate(null);
					} 
					/*������ɺ󷵻ص�ԤԼ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderInfoQueryActivity.this, OrderInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionOrderInfo", queryConditionOrderInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					OrderInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
