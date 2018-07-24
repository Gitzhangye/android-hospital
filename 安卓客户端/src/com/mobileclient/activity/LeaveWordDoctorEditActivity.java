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
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明留言idTextView
	private TextView TV_leaveWordId;
	// 声明标题文本框
	private TextView TV_title;
	// 声明留言内容文本框
	private TextView TV_content;
	// 声明留言时间文本框
	private TextView TV_addTime;
	// 声明留言人文本框
	private TextView TV_userObj;
	 
	// 声明回复内容输入框
	private EditText ET_replyContent;
	 
	protected String carmera_path;
	/*要保存的留言信息*/
	LeaveWord leaveWord = new LeaveWord();
	/*留言管理业务逻辑层*/
	private LeaveWordService leaveWordService = new LeaveWordService();

	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改留言");
		// 设置当前Activity界面布局
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
		/*单击修改留言按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					 
					  
					/*验证获取回复内容*/ 
					if(ET_replyContent.getText().toString().equals("")) {
						Toast.makeText(LeaveWordDoctorEditActivity.this, "回复内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_replyContent.setFocusable(true);
						ET_replyContent.requestFocus();
						return;	
					}
					leaveWord.setReplyContent(ET_replyContent.getText().toString()); 
					 
					Declare declare = (Declare) LeaveWordDoctorEditActivity.this.getApplication(); 
					leaveWord.setReplyDoctor(declare.getUserName());
					/*调用业务逻辑层上传留言信息*/
					LeaveWordDoctorEditActivity.this.setTitle("正在回复留言信息，稍等...");
					String result = leaveWordService.UpdateLeaveWord(leaveWord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到留言管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(LeaveWordDoctorEditActivity.this, LeaveWordDoctorListActivity.class);
					startActivity(intent); 
					LeaveWordDoctorEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
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
