package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;

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

public class LeaveWordDoctorQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �������������
	private EditText ET_title;
	// ����������������
	private Spinner spinner_userObj;
	private ArrayAdapter<String> userObj_adapter;
	private static  String[] userObj_ShowText  = null;
	private List<UserInfo> userInfoList = null; 
	/*�û�����ҵ���߼���*/
	private UserInfoService userInfoService = new UserInfoService();
	// �����ظ�ʱ�������
	private EditText ET_replyTime;
	 
	/*��ѯ�����������浽���������*/
	private LeaveWord queryConditionLeaveWord = new LeaveWord();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯ��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.leaveword_doctor_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_title = (EditText) findViewById(R.id.ET_title);
		spinner_userObj = (Spinner) findViewById(R.id.Spinner_userObj);
		// ��ȡ���е��û�
		try {
			userInfoList = userInfoService.QueryUserInfo(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int userInfoCount = userInfoList.size();
		userObj_ShowText = new String[userInfoCount+1];
		userObj_ShowText[0] = "������";
		for(int i=1;i<=userInfoCount;i++) { 
			userObj_ShowText[i] = userInfoList.get(i-1).getName();
		} 
		// ����ѡ������ArrayAdapter��������
		userObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, userObj_ShowText);
		// ���������������б�ķ��
		userObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_userObj.setAdapter(userObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_userObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionLeaveWord.setUserObj(userInfoList.get(arg2-1).getUser_name()); 
				else
					queryConditionLeaveWord.setUserObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_userObj.setVisibility(View.VISIBLE);
		ET_replyTime = (EditText) findViewById(R.id.ET_replyTime);
		 
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionLeaveWord.setTitle(ET_title.getText().toString());
					queryConditionLeaveWord.setReplyTime(ET_replyTime.getText().toString());
					queryConditionLeaveWord.setReplyDoctor("");
					/*������ɺ󷵻ص����Թ������*/ 
					Intent intent = new Intent();
					intent.setClass(LeaveWordDoctorQueryActivity.this, LeaveWordDoctorListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionLeaveWord", queryConditionLeaveWord);
					intent.putExtras(bundle);
					startActivity(intent);  
					LeaveWordDoctorQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
