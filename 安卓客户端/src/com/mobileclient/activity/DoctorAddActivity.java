package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class DoctorAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����ҽ����������
	private EditText ET_doctorNo;
	// ������¼���������
	private EditText ET_password;
	// �������ڿ���������
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*���ڿ��ҹ���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// ����ҽ����ƬͼƬ��ؼ�
	private ImageView iv_doctorPhoto;
	private Button btn_doctorPhoto;
	protected int REQ_CODE_SELECT_IMAGE_doctorPhoto = 1;
	private int REQ_CODE_CAMERA_doctorPhoto = 2;
	// ����ѧ�������
	private EditText ET_education;
	// ������Ժ���ڿؼ�
	private DatePicker dp_inDate;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ����ÿ�ճ�����������
	private EditText ET_visiteTimes;
	// ����������Ϣ�����
	private EditText ET_memo;
	protected String carmera_path;
	/*Ҫ�����ҽ����Ϣ*/
	Doctor doctor = new Doctor();
	/*ҽ������ҵ���߼���*/
	private DoctorService doctorService = new DoctorService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ҽ��");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.doctor_add); 
		ET_doctorNo = (EditText) findViewById(R.id.ET_doctorNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// ��ȡ���е����ڿ���
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// ����ѡ������ArrayAdapter��������
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// ���������б�ķ��
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				doctor.setDepartmentObj(departmentList.get(arg2).getDepartmentId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		iv_doctorPhoto = (ImageView) findViewById(R.id.iv_doctorPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_doctorPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DoctorAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_doctorPhoto);
			}
		});
		btn_doctorPhoto = (Button) findViewById(R.id.btn_doctorPhoto);
		btn_doctorPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_doctorPhoto);  
			}
		});
		ET_education = (EditText) findViewById(R.id.ET_education);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_visiteTimes = (EditText) findViewById(R.id.ET_visiteTimes);
		ET_memo = (EditText) findViewById(R.id.ET_memo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ҽ����ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡҽ�����*/
					if(ET_doctorNo.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "ҽ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_doctorNo.setFocusable(true);
						ET_doctorNo.requestFocus();
						return;
					}
					doctor.setDoctorNo(ET_doctorNo.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					doctor.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					doctor.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					doctor.setSex(ET_sex.getText().toString());
					if(doctor.getDoctorPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						DoctorAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String doctorPhoto = HttpUtil.uploadFile(doctor.getDoctorPhoto());
						DoctorAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						doctor.setDoctorPhoto(doctorPhoto);
					} else {
						doctor.setDoctorPhoto("upload/noimage.jpg");
					}
					/*��֤��ȡѧ��*/ 
					if(ET_education.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "ѧ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_education.setFocusable(true);
						ET_education.requestFocus();
						return;	
					}
					doctor.setEducation(ET_education.getText().toString());
					/*��ȡ��������*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					doctor.setInDate(new Timestamp(inDate.getTime()));
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					doctor.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡÿ�ճ������*/ 
					if(ET_visiteTimes.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "ÿ�ճ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_visiteTimes.setFocusable(true);
						ET_visiteTimes.requestFocus();
						return;	
					}
					doctor.setVisiteTimes(Integer.parseInt(ET_visiteTimes.getText().toString()));
					/*��֤��ȡ������Ϣ*/ 
					if(ET_memo.getText().toString().equals("")) {
						Toast.makeText(DoctorAddActivity.this, "������Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_memo.setFocusable(true);
						ET_memo.requestFocus();
						return;	
					}
					doctor.setMemo(ET_memo.getText().toString());
					/*����ҵ���߼����ϴ�ҽ����Ϣ*/
					DoctorAddActivity.this.setTitle("�����ϴ�ҽ����Ϣ���Ե�...");
					String result = doctorService.AddDoctor(doctor);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�ҽ���������*/ 
					Intent intent = new Intent();
					intent.setClass(DoctorAddActivity.this, DoctorListActivity.class);
					startActivity(intent); 
					DoctorAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_doctorPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_doctorPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_doctorPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_doctorPhoto.setImageBitmap(booImageBm);
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.doctor.setDoctorPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_doctorPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_doctorPhoto.setImageBitmap(bm); 
				this.iv_doctorPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			doctor.setDoctorPhoto(filename); 
		}
	}
}
