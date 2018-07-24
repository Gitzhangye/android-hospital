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
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_leaveWordId;
	// ��������ؼ�
	private TextView TV_title;
	// �����������ݿؼ�
	private TextView TV_content;
	// ��������ʱ��ؼ�
	private TextView TV_addTime;
	// ���������˿ؼ�
	private TextView TV_userObj;
	// �����ظ����ݿؼ�
	private TextView TV_replyContent;
	// �����ظ�ʱ��ؼ�
	private TextView TV_replyTime;
	// �����ظ���ҽ���ؼ�
	private TextView TV_replyDoctor;
	/* Ҫ�����������Ϣ */
	LeaveWord leaveWord = new LeaveWord(); 
	/* ���Թ���ҵ���߼��� */
	private LeaveWordService leaveWordService = new LeaveWordService();
	private UserInfoService userInfoService = new UserInfoService();
	private int leaveWordId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.leaveword_detail);
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
