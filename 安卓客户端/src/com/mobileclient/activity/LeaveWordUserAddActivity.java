package com.mobileclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;
public class LeaveWordUserAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������������
	private EditText ET_title;
	// �����������������
	private EditText ET_content;
	 
	protected String carmera_path;
	/*Ҫ�����������Ϣ*/
	LeaveWord leaveWord = new LeaveWord();
	/*���Թ���ҵ���߼���*/
	private LeaveWordService leaveWordService = new LeaveWordService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.leaveword_user_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*����������԰�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(LeaveWordUserAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					leaveWord.setTitle(ET_title.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(LeaveWordUserAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					leaveWord.setContent(ET_content.getText().toString());
					/*����ʱ��*/  
					leaveWord.setAddTime("");
					/*�ظ�����*/  
					leaveWord.setReplyContent("--");
					/*�ظ�ʱ��*/ 
					leaveWord.setReplyTime("--");
					/*�ظ���ҽ��*/  
					leaveWord.setReplyDoctor("--");
					
					//��ȡ������
					Declare declare = (Declare) LeaveWordUserAddActivity.this.getApplication(); 
					leaveWord.setUserObj(declare.getUserName());
					
					/*����ҵ���߼����ϴ�������Ϣ*/
					LeaveWordUserAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = leaveWordService.AddLeaveWord(leaveWord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����Թ������*/ 
					Intent intent = new Intent();
					intent.setClass(LeaveWordUserAddActivity.this, LeaveWordUserListActivity.class);
					startActivity(intent); 
					LeaveWordUserAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
