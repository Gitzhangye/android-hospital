package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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
public class UserInfoRegisterActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����û��������
	private EditText ET_user_name;
	// ������¼���������
	private EditText ET_password;
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// �����û���ƬͼƬ��ؼ�
	private ImageView iv_userPhoto;
	private Button btn_userPhoto;
	protected int REQ_CODE_SELECT_IMAGE_userPhoto = 1;
	private int REQ_CODE_CAMERA_userPhoto = 2;
	// ����������ڿؼ�
	private DatePicker dp_birthday;
	// �������������
	private EditText ET_jiguan;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ������ͥ��ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ������û���Ϣ*/
	UserInfo userInfo = new UserInfo();
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-����û�");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_add); 
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_userPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserInfoRegisterActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_userPhoto);
			}
		});
		btn_userPhoto = (Button) findViewById(R.id.btn_userPhoto);
		btn_userPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_userPhoto);  
			}
		});
		dp_birthday = (DatePicker)this.findViewById(R.id.dp_birthday);
		ET_jiguan = (EditText) findViewById(R.id.ET_jiguan);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*��������û���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�û���*/
					if(ET_user_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�û������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_user_name.setFocusable(true);
						ET_user_name.requestFocus();
						return;
					}
					userInfo.setUser_name(ET_user_name.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					userInfo.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					userInfo.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					userInfo.setSex(ET_sex.getText().toString());
					if(userInfo.getUserPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						UserInfoRegisterActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String userPhoto = HttpUtil.uploadFile(userInfo.getUserPhoto());
						UserInfoRegisterActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						userInfo.setUserPhoto(userPhoto);
					} else {
						userInfo.setUserPhoto("upload/noimage.jpg");
					}
					/*��ȡ��������*/
					Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
					userInfo.setBirthday(new Timestamp(birthday.getTime()));
					/*��֤��ȡ����*/ 
					if(ET_jiguan.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_jiguan.setFocusable(true);
						ET_jiguan.requestFocus();
						return;	
					}
					userInfo.setJiguan(ET_jiguan.getText().toString());
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					userInfo.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(UserInfoRegisterActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					userInfo.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ��û���Ϣ*/
					UserInfoRegisterActivity.this.setTitle("�����ϴ��û���Ϣ���Ե�...");
					String result = userInfoService.AddUserInfo(userInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					 
					UserInfoRegisterActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_userPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_userPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_userPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_userPhoto.setImageBitmap(booImageBm);
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.userInfo.setUserPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_userPhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_userPhoto.setImageBitmap(bm); 
				this.iv_userPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			userInfo.setUserPhoto(filename); 
		}
	}
}
