package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.UserInfo;

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

public class UserInfoQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_user_name;
	// 声明姓名输入框
	private EditText ET_name;
	// 出生日期控件
	private DatePicker dp_birthday;
	private CheckBox cb_birthday;
	// 声明籍贯输入框
	private EditText ET_jiguan;
	/*查询过滤条件保存到这个对象中*/
	private UserInfo queryConditionUserInfo = new UserInfo();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-设置查询用户条件");
		// 设置当前Activity界面布局
		setContentView(R.layout.userinfo_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_user_name = (EditText) findViewById(R.id.ET_user_name);
		ET_name = (EditText) findViewById(R.id.ET_name);
		dp_birthday = (DatePicker) findViewById(R.id.dp_birthday);
		cb_birthday = (CheckBox) findViewById(R.id.cb_birthday);
		ET_jiguan = (EditText) findViewById(R.id.ET_jiguan);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionUserInfo.setUser_name(ET_user_name.getText().toString());
					queryConditionUserInfo.setName(ET_name.getText().toString());
					if(cb_birthday.isChecked()) {
						/*获取出生日期*/
						Date birthday = new Date(dp_birthday.getYear()-1900,dp_birthday.getMonth(),dp_birthday.getDayOfMonth());
						queryConditionUserInfo.setBirthday(new Timestamp(birthday.getTime()));
					} else {
						queryConditionUserInfo.setBirthday(null);
					} 
					queryConditionUserInfo.setJiguan(ET_jiguan.getText().toString());
					/*操作完成后返回到用户管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(UserInfoQueryActivity.this, UserInfoListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionUserInfo", queryConditionUserInfo);
					intent.putExtras(bundle);
					startActivity(intent);  
					UserInfoQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
