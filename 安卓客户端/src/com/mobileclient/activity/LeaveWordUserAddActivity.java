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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明标题输入框
	private EditText ET_title;
	// 声明留言内容输入框
	private EditText ET_content;
	 
	protected String carmera_path;
	/*要保存的留言信息*/
	LeaveWord leaveWord = new LeaveWord();
	/*留言管理业务逻辑层*/
	private LeaveWordService leaveWordService = new LeaveWordService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加留言");
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveword_user_add); 
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加留言按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(LeaveWordUserAddActivity.this, "标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					leaveWord.setTitle(ET_title.getText().toString());
					/*验证获取留言内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(LeaveWordUserAddActivity.this, "留言内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					leaveWord.setContent(ET_content.getText().toString());
					/*留言时间*/  
					leaveWord.setAddTime("");
					/*回复内容*/  
					leaveWord.setReplyContent("--");
					/*回复时间*/ 
					leaveWord.setReplyTime("--");
					/*回复的医生*/  
					leaveWord.setReplyDoctor("--");
					
					//获取留言人
					Declare declare = (Declare) LeaveWordUserAddActivity.this.getApplication(); 
					leaveWord.setUserObj(declare.getUserName());
					
					/*调用业务逻辑层上传留言信息*/
					LeaveWordUserAddActivity.this.setTitle("正在上传留言信息，稍等...");
					String result = leaveWordService.AddLeaveWord(leaveWord);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到留言管理界面*/ 
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
