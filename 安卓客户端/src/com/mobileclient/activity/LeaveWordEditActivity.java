package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class LeaveWordEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_leaveWordId;
	// �������������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	// ��������ʱ�������
	private EditText ET_addTime;
	// ����������������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null;
	/*�����˹���ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// �����ظ����������
	private EditText ET_replyContent;
	// �����ظ�ʱ�������
	private EditText ET_replyTime;
	// �����ظ���ҽ�������
	private EditText ET_replyDoctor;
	protected String carmera_path;
	/*Ҫ�����������Ϣ*/
	LeaveWord leaveWord = new LeaveWord();
	/*���Թ���ҵ���߼���*/
	private LeaveWordService leaveWordService = new LeaveWordService();

	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸�����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.leaveword_edit); 
		TV_leaveWordId = (TextView) findViewById(R.id.TV_leaveWordId);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е�������
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount];
		for(int i=0;i<userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i).getName();
		}
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ����ͼ����������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveWord.setUserObj(userInfoList.get(arg2).getUser_name()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		ET_replyTime = (EditText) findViewById(R.id.ET_replyTime);
		ET_replyDoctor = (EditText) findViewById(R.id.ET_replyDoctor);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		leaveWordId = extras.getInt("leaveWordId");
		initViewData();
		/*�����޸����԰�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					leaveWord.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					leaveWord.setContent(ET_content.getText().toString());
					/*��֤��ȡ����ʱ��*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "����ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					leaveWord.setAddTime(ET_addTime.getText().toString());
					/*��֤��ȡ�ظ�����*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "�ظ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					leaveWord.setReplyContent(ET_replyContent.getText().toString());
					/*��֤��ȡ�ظ�ʱ��*/ 
					if(ET_replyTime.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "�ظ�ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyTime.setFocusable(true);
						ET_replyTime.requestFocus();
						return;	
					}
					leaveWord.setReplyTime(ET_replyTime.getText().toString());
					/*��֤��ȡ�ظ���ҽ��*/ 
					if(ET_replyDoctor.getText().toString().equals("")) {
						Toast.makeText(LeaveWordEditActivity.this, "�ظ���ҽ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyDoctor.setFocusable(true);
						ET_replyDoctor.requestFocus();
						return;	
					}
					leaveWord.setReplyDoctor(ET_replyDoctor.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					LeaveWordEditActivity.this.setTitle("���ڸ���������Ϣ���Ե�...");
					String result = leaveWordService.UpdateLeaveWord(leaveWord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����Թ������*/ 
					Intent intent = new Intent();
					intent.setClass(LeaveWordEditActivity.this, LeaveWordListActivity.class);
					startActivity(intent); 
					LeaveWordEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    leaveWord = leaveWordService.GetLeaveWord(leaveWordId);
		this.TV_leaveWordId.setText(leaveWordId+"");
		this.ET_title.setText(leaveWord.getTitle());
		this.ET_content.setText(leaveWord.getContent());
		this.ET_addTime.setText(leaveWord.getAddTime());
		for (int i = 0; i < userInfoList.size(); i++) {
			if (leaveWord.getUserObj().equals(userInfoList.get(i).getUser_name())) {
				this.spinner_userObj.setSelection(i);
				break;
			}
		}
		this.ET_replyContent.setText(leaveWord.getReplyContent());
		this.ET_replyTime.setText(leaveWord.getReplyTime());
		this.ET_replyDoctor.setText(leaveWord.getReplyDoctor());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
