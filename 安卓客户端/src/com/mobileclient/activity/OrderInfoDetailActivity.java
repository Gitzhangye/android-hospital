package com.mobileclient.activity;

import java.util.Date;
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
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����ԤԼid�ؼ�
	private TextView TV_orderId;
	// ����ԤԼ�û��ؼ�
	private TextView TV_uesrObj;
	// ����ԤԼҽ���ؼ�
	private TextView TV_doctor;
	// ����ԤԼ���ڿؼ�
	private TextView TV_orderDate;
	// ����ԤԼʱ��οؼ�
	private TextView TV_timeSlotObj;
	// ��������״̬�ؼ�
	private TextView TV_visiteStateObj;
	// ����ҽ��˵���ؼ�
	private TextView TV_introduce;
	/* Ҫ�����ԤԼ��Ϣ */
	OrderInfo orderInfo = new OrderInfo(); 
	/* ԤԼ����ҵ���߼��� */
	private OrderInfoService orderInfoService = new OrderInfoService();
	private UserInfoService userInfoService = new UserInfoService();
	private DoctorService doctorService = new DoctorService();
	private TimeSlotService timeSlotService = new TimeSlotService();
	private VisitStateService visitStateService = new VisitStateService();
	private int orderId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴ԤԼ����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.orderinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_orderId = (TextView) findViewById(R.id.TV_orderId);
		TV_uesrObj = (TextView) findViewById(R.id.TV_uesrObj);
		TV_doctor = (TextView) findViewById(R.id.TV_doctor);
		TV_orderDate = (TextView) findViewById(R.id.TV_orderDate);
		TV_timeSlotObj = (TextView) findViewById(R.id.TV_timeSlotObj);
		TV_visiteStateObj = (TextView) findViewById(R.id.TV_visiteStateObj);
		TV_introduce = (TextView) findViewById(R.id.TV_introduce);
		Bundle extras = this.getIntent().getExtras();
		orderId = extras.getInt("orderId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    orderInfo = orderInfoService.GetOrderInfo(orderId); 
		this.TV_orderId.setText(orderInfo.getOrderId() + "");
		UserInfo uesrObj = userInfoService.GetUserInfo(orderInfo.getUesrObj());
		this.TV_uesrObj.setText(uesrObj.getName());
		Doctor doctor = doctorService.GetDoctor(orderInfo.getDoctor());
		this.TV_doctor.setText(doctor.getName());
		Date orderDate = new Date(orderInfo.getOrderDate().getTime());
		String orderDateStr = (orderDate.getYear() + 1900) + "-" + (orderDate.getMonth()+1) + "-" + orderDate.getDate();
		this.TV_orderDate.setText(orderDateStr);
		TimeSlot timeSlotObj = timeSlotService.GetTimeSlot(orderInfo.getTimeSlotObj());
		this.TV_timeSlotObj.setText(timeSlotObj.getTimeSlotName());
		VisitState visiteStateObj = visitStateService.GetVisitState(orderInfo.getVisiteStateObj());
		this.TV_visiteStateObj.setText(visiteStateObj.getVisitStateName());
		this.TV_introduce.setText(orderInfo.getIntroduce());
	} 
}
