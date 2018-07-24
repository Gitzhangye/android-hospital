package com.mobileclient.activity;

import java.net.URLEncoder;

import com.mobileclient.app.Declare;
import com.mobileclient.util.HttpUtil;

 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// ������¼��ȡ����ť
	private Button cancelBtn,loginBtn,exitBtn,registerBtn;
	// �����û��������������
	private EditText userEditText,pwdEditText;
	
	//�û����ѡ��������
	private Spinner Spinner_identify;
	private ArrayAdapter<String> identify_adapter;
	private static  String[] identify_ShowText  = null;
		
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.login_system);
		// ͨ��findViewById����ʵ�������
		cancelBtn = (Button)findViewById(R.id.cancelButton);
		// ͨ��findViewById����ʵ�������
		loginBtn = (Button)findViewById(R.id.loginButton);
		exitBtn = (Button)findViewById(R.id.exitButton);
		registerBtn = (Button)findViewById(R.id.registerButton);
		// ͨ��findViewById����ʵ�������
		userEditText = (EditText)findViewById(R.id.userEditText);
		// ͨ��findViewById����ʵ�������
		pwdEditText = (EditText)findViewById(R.id.pwdEditText);
		
		
		this.Spinner_identify = (Spinner) findViewById(R.id.Spinner_identify);
		identify_ShowText = new String[] {"�û�","ҽ��","����Ա"};
		// ����ѡ������ArrayAdapter��������
		identify_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, identify_ShowText);
		// ���������б�ķ��
		identify_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		Spinner_identify.setAdapter(identify_adapter);
		// ����¼�Spinner�¼�����
		Spinner_identify.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				//breakTaskInfo.setBreakTypeObj(breakTypeList.get(arg2).getBreakTypeId()); 
				Declare declare = (Declare)LoginActivity.this.getApplication();
				switch(arg2) {
					case 0:
						declare.setIdentify("user");
						break;
					case 1:
						declare.setIdentify("doctor");
						break;  
					case 2:
						declare.setIdentify("admin");
						break;  
					default:
						break;
				} 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
			
			});
			// ����Ĭ��ֵ
		Spinner_identify.setVisibility(View.VISIBLE);
		
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userEditText.setText("");
				pwdEditText.setText("");
			}
		});
		
		exitBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.exit(0);   
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,UserInfoRegisterActivity.class);
				startActivity(intent); 
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Declare declare = (Declare) getApplicationContext();
				try {
					String url = HttpUtil.BASE_URL
							+ "LoginServlet?userName="
							+ URLEncoder.encode(
									URLEncoder.encode(userEditText.getText().toString(), "UTF-8"), "UTF-8")+"&password="
									+ URLEncoder.encode(
									URLEncoder.encode(pwdEditText.getText().toString(), "UTF-8"), "UTF-8")
									+"&identify=" + declare.getIdentify();
					// ��ѯ���ؽ��
					String result = HttpUtil.queryStringForPost(url);
					System.out.println("=========================  "+result);
					if(!result.equals("0")){
						System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
						
						//declare.setId(Integer.parseInt(result));
						 
						declare.setUserName(userEditText.getText().toString());
						Toast.makeText(getApplicationContext(), "����ɹ�", 1).show();
						Intent intent = new Intent();
						if(declare.getIdentify().equals("admin"))
							intent.setClass(LoginActivity.this,MainMenuActivity.class);
						else if(declare.getIdentify().equals("doctor"))
							intent.setClass(LoginActivity.this,MainMenuDoctorActivity.class);
						else if(declare.getIdentify().equals("user"))
							intent.setClass(LoginActivity.this,MainMenuUserActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
						
					}else{
						Toast.makeText(getApplicationContext(), "����ʧ��", 1).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("LoginActivity",e.toString());
				}
				
			}
		});
	}
}