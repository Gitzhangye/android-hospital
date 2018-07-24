package com.mobileclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;

public class LeaveWordDoctorEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_leaveWordId;
	// ���������ı���
	private TextView TV_title;
	// �������������ı���
	private TextView TV_content;
	// ��������ʱ���ı���
	private TextView TV_addTime;
	// �����������ı���
	private TextView TV_userObj;
	 
	// �����ظ����������
	private EditText ET_replyContent;
	 
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
		setContentView(R.layout.leaveword_doctor_edit); 
		TV_leaveWordId = (TextView) findViewById(R.id.TV_leaveWordId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		  
		ET_replyContent = (EditText) findViewById(R.id.ET_replyContent);
		 
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		leaveWordId = extras.getInt("leaveWordId");
		initViewData();
		/*�����޸����԰�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					 
					  
					/*��֤��ȡ�ظ�����*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(LeaveWordDoctorEditActivity.this, "�ظ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					leaveWord.setReplyContent(ET_replyContent.getText().toString()); 
					 
					Declare declare = (Declare) LeaveWordDoctorEditActivity.this.getApplication(); 
					leaveWord.setReplyDoctor(declare.getUserName());
					/*����ҵ���߼����ϴ�������Ϣ*/
					LeaveWordDoctorEditActivity.this.setTitle("���ڻظ�������Ϣ���Ե�...");
					String result = leaveWordService.UpdateLeaveWord(leaveWord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����Թ������*/ 
					Intent intent = new Intent();
					intent.setClass(LeaveWordDoctorEditActivity.this, LeaveWordDoctorListActivity.class);
					startActivity(intent); 
					LeaveWordDoctorEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    leaveWord = leaveWordService.GetLeaveWord(leaveWordId);
		this.TV_leaveWordId.setText(leaveWordId+"");
		this.TV_title.setText(leaveWord.getTitle());
		this.TV_content.setText(leaveWord.getContent());
		this.TV_addTime.setText(leaveWord.getAddTime());
		this.TV_userObj.setText(leaveWord.getUserObj());
		this.ET_replyContent.setText("");
		 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
