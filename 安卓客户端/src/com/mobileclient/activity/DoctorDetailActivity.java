package com.mobileclient.activity;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Department;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DepartmentService;
import com.mobileclient.service.DoctorService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;

public class DoctorDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����ԤԼҽ����ť
	private Button btnOrder;
	
	// ����ҽ����ſؼ�
	private TextView TV_doctorNo;
	// ������¼����ؼ�
	private TextView TV_password;
	// �������ڿ��ҿؼ�
	private TextView TV_departmentObj;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// ����ҽ����ƬͼƬ��
	private ImageView iv_doctorPhoto;
	// ����ѧ���ؼ�
	private TextView TV_education;
	// ������Ժ���ڿؼ�
	private TextView TV_inDate;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ����ÿ�ճ�������ؼ�
	private TextView TV_visiteTimes;
	// ����������Ϣ�ؼ�
	private TextView TV_memo;
	/* Ҫ�����ҽ����Ϣ */
	Doctor doctor = new Doctor(); 
	/* ҽ������ҵ���߼��� */
	private DoctorService doctorService = new DoctorService();
	private DepartmentService departmentService = new DepartmentService();
	private String doctorNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴ҽ������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.doctor_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		btnOrder = (Button) findViewById(R.id.btnOrder);
		TV_doctorNo = (TextView) findViewById(R.id.TV_doctorNo);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto); 
		TV_education = (TextView) findViewById(R.id.TV_education);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_visiteTimes = (TextView) findViewById(R.id.TV_visiteTimes);
		TV_memo = (TextView) findViewById(R.id.TV_memo);
		Bundle extras = this.getIntent().getExtras();
		doctorNo = extras.getString("doctorNo");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DoctorDetailActivity.this.finish();
			}
		}); 
		btnOrder.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// ���ԤԼ��Ϣ
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
            	bundle.putString("doctorNo", doctorNo);
            	intent.putExtras(bundle);
				intent.setClass(DoctorDetailActivity.this, OrderInfoUserAddActivity.class);
				startActivity(intent);
			}
		}); 
		
		Declare declare = (Declare)DoctorDetailActivity.this.getApplication();
		if(declare.getIdentify().equals("user")) {
			this.btnOrder.setVisibility(View.VISIBLE);
		}
		
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    doctor = doctorService.GetDoctor(doctorNo); 
		this.TV_doctorNo.setText(doctor.getDoctorNo());
		this.TV_password.setText(doctor.getPassword());
		Department departmentObj = departmentService.GetDepartment(doctor.getDepartmentObj());
		this.TV_departmentObj.setText(departmentObj.getDepartmentName());
		this.TV_name.setText(doctor.getName());
		this.TV_sex.setText(doctor.getSex());
		byte[] doctorPhoto_data = null;
		try {
			// ��ȡͼƬ����
			doctorPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + doctor.getDoctorPhoto());
			Bitmap doctorPhoto = BitmapFactory.decodeByteArray(doctorPhoto_data, 0,doctorPhoto_data.length);
			this.iv_doctorPhoto.setImageBitmap(doctorPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_education.setText(doctor.getEducation());
		Date inDate = new Date(doctor.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		this.TV_telephone.setText(doctor.getTelephone());
		this.TV_visiteTimes.setText(doctor.getVisiteTimes() + "");
		this.TV_memo.setText(doctor.getMemo());
	} 
}
