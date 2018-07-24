package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
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

public class UserInfoDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����û����ؼ�
	private TextView TV_user_name;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����û���ƬͼƬ��
	private ImageView iv_userPhoto;
	// �����������ڿؼ�
	private TextView TV_birthday;
	// ��������ؼ�
	private TextView TV_jiguan;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ������ͥ��ַ�ؼ�
	private TextView TV_address;
	/* Ҫ������û���Ϣ */
	UserInfo userInfo = new UserInfo(); 
	/* �û�����ҵ���߼��� */
	private UserInfoService userInfoService = new UserInfoService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴�û�����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.userinfo_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		iv_userPhoto = (ImageView) findViewById(R.id.iv_userPhoto); 
		TV_birthday = (TextView) findViewById(R.id.TV_birthday);
		TV_jiguan = (TextView) findViewById(R.id.TV_jiguan);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserInfoDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    userInfo = userInfoService.GetUserInfo(user_name); 
		this.TV_user_name.setText(userInfo.getUser_name());
		this.TV_password.setText(userInfo.getPassword());
		this.TV_name.setText(userInfo.getName());
		this.TV_sex.setText(userInfo.getSex());
		byte[] userPhoto_data = null;
		try {
			// ��ȡͼƬ����
			userPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + userInfo.getUserPhoto());
			Bitmap userPhoto = BitmapFactory.decodeByteArray(userPhoto_data, 0,userPhoto_data.length);
			this.iv_userPhoto.setImageBitmap(userPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date birthday = new Date(userInfo.getBirthday().getTime());
		String birthdayStr = (birthday.getYear() + 1900) + "-" + (birthday.getMonth()+1) + "-" + birthday.getDate();
		this.TV_birthday.setText(birthdayStr);
		this.TV_jiguan.setText(userInfo.getJiguan());
		this.TV_telephone.setText(userInfo.getTelephone());
		this.TV_address.setText(userInfo.getAddress());
	} 
}
