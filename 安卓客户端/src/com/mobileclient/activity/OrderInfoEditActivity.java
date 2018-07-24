package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class OrderInfoEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ����ԤԼidTextView
	private TextView TV_orderId;
	// ����ԤԼ�û�������
	private Spinner spinner_uesrObj;
	private ArrayAdapter<String> uesrObj_adapter;
	private static  String[] uesrObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*ԤԼ�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// ����ԤԼҽ��������
	private Spinner spinner_doctor;
	private ArrayAdapter<String> doctor_adapter;
	private static  String[] doctor_ShowText  = null;
	private List<Doctor> doctorList = null;
	/*ԤԼҽ������ҵ���߼���*/
	private DoctorService doctorService = new DoctorService();
	// ����ԤԼ���ڿؼ�
	private DatePicker dp_orderDate;
	// ����ԤԼʱ���������
	private Spinner spinner_timeSlotObj;
	private ArrayAdapter<String> timeSlotObj_adapter;
	private static  String[] timeSlotObj_ShowText  = null;
	private List<TimeSlot> timeSlotList = null;
	/*ԤԼʱ��ι���ҵ���߼���*/
	private TimeSlotService timeSlotService = new TimeSlotService();
	// ��������״̬������
	private Spinner spinner_visiteStateObj;
	private ArrayAdapter<String> visiteStateObj_adapter;
	private static  String[] visiteStateObj_ShowText  = null;
	private List<VisitState> visitStateList = null;
	/*����״̬����ҵ���߼���*/
	private VisitStateService visitStateService = new VisitStateService();
	// ����ҽ��˵�������
	private EditText ET_introduce;
	protected String carmera_path;
	/*Ҫ�����ԤԼ��Ϣ*/
	OrderInfo orderInfo = new OrderInfo();
	/*ԤԼ����ҵ���߼���*/
	private OrderInfoService orderInfoService = new OrderInfoService();

	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸�ԤԼ");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_edit); 
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		spinner_uesrObj = (Spinner) findViewById(R.id.Spinner_uesrObj);
		// ��ȡ���е�ԤԼ�û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		uesrObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			uesrObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		uesrObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, uesrObj_ShowText);
		// ����ͼ����������б�ķ��
		uesrObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_uesrObj.setAdapter(uesrObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_uesrObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setUesrObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_uesrObj.setVisibility(View.VISIBLE);
		spinner_doctor = (Spinner) findViewById(R.id.Spinner_doctor);
		// ��ȡ���е�ԤԼҽ��
		try {
			doctorList = doctorService.QueryDoctor(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int doctorCount = doctorList.size();
		doctor_ShowText = new String[doctorCount];
		for(int i=0;i<doctorCount;i++) { 
			doctor_ShowText[i] = doctorList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		doctor_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, doctor_ShowText);
		// ����ͼ����������б�ķ��
		doctor_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_doctor.setAdapter(doctor_adapter);
		// ����¼�Spinner�¼�����
		spinner_doctor.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setDoctor(doctorList.get(arg2).getDoctorNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_doctor.setVisibility(View.VISIBLE);
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
		// ����ͼ����������б�ķ��
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
		spinner_visiteStateObj = (Spinner) findViewById(R.id.Spinner_visiteStateObj);
		// ��ȡ���еĳ���״̬
		try {
			visitStateList = visitStateService.QueryVisitState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int visitStateCount = visitStateList.size();
		visiteStateObj_ShowText = new String[visitStateCount];
		for(int i=0;i<visitStateCount;i++) { 
			visiteStateObj_ShowText[i] = visitStateList.get(i).getVisitStateName();
		}
		// ����ѡ������ArrayAdapter��������
		visiteStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visiteStateObj_ShowText);
		// ����ͼ����������б�ķ��
		visiteStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_visiteStateObj.setAdapter(visiteStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_visiteStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				orderInfo.setVisiteStateObj(visitStateList.get(arg2).getVisitStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_visiteStateObj.setVisibility(View.VISIBLE);
		ET_introduce = (EditText) findViewById(R.id.ET_introduce);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		initViewData();
		/*�����޸�ԤԼ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��������*/
					Date orderDate = new Date(dp_orderDate.getYear()-1900,dp_orderDate.getMonth(),dp_orderDate.getDayOfMonth());
					orderInfo.setOrderDate(new Timestamp(orderDate.getTime()));
					/*��֤��ȡҽ��˵��*/ 
					if(ET_introduce.getText().toString().equals("")) {
						Toast.makeText(OrderInfoEditActivity.this, "ҽ��˵�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_introduce.setFocusable(true);
						ET_introduce.requestFocus();
						return;	
					}
					orderInfo.setIntroduce(ET_introduce.getText().toString());
					/*����ҵ���߼����ϴ�ԤԼ��Ϣ*/
					OrderInfoEditActivity.this.setTitle("���ڸ���ԤԼ��Ϣ���Ե�...");
					String result = orderInfoService.UpdateOrderInfo(orderInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�ԤԼ�������*/ 
					Intent intent = new Intent();
					intent.setClass(OrderInfoEditActivity.this, OrderInfoListActivity.class);
					startActivity(intent); 
					OrderInfoEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderId);
		this.TV_orderId.setText(orderId+"");
		for (int i = 0; i < userInfoList.size(); i++) {
			if (orderInfo.getUesrObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_uesrObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < doctorList.size(); i++) {
			if (orderInfo.getDoctor().equals(doctorList.get(i).getDoctorNo())) {
				this.spinner_doctor.setSelection(i);
				break;
			}
		}
		Date orderDate = new Date(orderInfo.getOrderDate().getTime());
		this.dp_orderDate.init(orderDate.getYear() + 1900,orderDate.getMonth(), orderDate.getDate(), null);
		for (int i = 0; i < timeSlotList.size(); i++) {
			if (orderInfo.getTimeSlotObj() == timeSlotList.get(i).getTimeSlotId()) {
				this.spinner_timeSlotObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < visitStateList.size(); i++) {
			if (orderInfo.getVisiteStateObj() == visitStateList.get(i).getVisitStateId()) {
				this.spinner_visiteStateObj.setSelection(i);
				break;
			}
		}
		this.ET_introduce.setText(orderInfo.getIntroduce());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
