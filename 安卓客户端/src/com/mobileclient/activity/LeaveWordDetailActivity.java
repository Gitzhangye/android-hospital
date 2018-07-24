package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;
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

public class LeaveWordDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明留言id控件
	private TextView TV_leaveWordId;
	// 声明标题控件
	private TextView TV_title;
	// 声明留言内容控件
	private TextView TV_content;
	// 声明留言时间控件
	private TextView TV_addTime;
	// 声明留言人控件
	private TextView TV_userObj;
	// 声明回复内容控件
	private TextView TV_replyContent;
	// 声明回复时间控件
	private TextView TV_replyTime;
	// 声明回复的医生控件
	private TextView TV_replyDoctor;
	/* 要保存的留言信息 */
	LeaveWord leaveWord = new LeaveWord(); 
	/* 留言管理业务逻辑层 */
	private LeaveWordService leaveWordService = new LeaveWordService();
	private UserInfoService userInfoService = new UserInfoService();
	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看留言详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveword_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_leaveWordId = (TextView) findViewById(R.id.TV_leaveWordId);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		TV_userObj = (TextView) findViewById(R.id.TV_userObj);
		TV_replyContent = (TextView) findViewById(R.id.TV_replyContent);
		TV_replyTime = (TextView) findViewById(R.id.TV_replyTime);
		TV_replyDoctor = (TextView) findViewById(R.id.TV_replyDoctor);
		Bundle extras = this.getIntent().getExtras();
		leaveWordId = extras.getInt("leaveWordId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LeaveWordDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    leaveWord = leaveWordService.GetLeaveWord(leaveWordId); 
		this.TV_leaveWordId.setText(leaveWord.getLeaveWordId() + "");
		this.TV_title.setText(leaveWord.getTitle());
		this.TV_content.setText(leaveWord.getContent());
		this.TV_addTime.setText(leaveWord.getAddTime());
		UserInfo userObj = userInfoService.GetUserInfo(leaveWord.getUserObj());
		this.TV_userObj.setText(userObj.getName());
		this.TV_replyContent.setText(leaveWord.getReplyContent());
		this.TV_replyTime.setText(leaveWord.getReplyTime());
		this.TV_replyDoctor.setText(leaveWord.getReplyDoctor());
	} 
}
