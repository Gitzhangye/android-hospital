package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.TimeSlotService;
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

public class TimeSlotDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明时间段id控件
	private TextView TV_timeSlotId;
	// 声明时间段名称控件
	private TextView TV_timeSlotName;
	/* 要保存的时间段信息 */
	TimeSlot timeSlot = new TimeSlot(); 
	/* 时间段管理业务逻辑层 */
	private TimeSlotService timeSlotService = new TimeSlotService();
	private int timeSlotId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看时间段详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.timeslot_detail);
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_timeSlotId = (TextView) findViewById(R.id.TV_timeSlotId);
		TV_timeSlotName = (TextView) findViewById(R.id.TV_timeSlotName);
		Bundle extras = this.getIntent().getExtras();
		timeSlotId = extras.getInt("timeSlotId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TimeSlotDetailActivity.this.finish();
			}
		}); 
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    timeSlot = timeSlotService.GetTimeSlot(timeSlotId); 
		this.TV_timeSlotId.setText(timeSlot.getTimeSlotId() + "");
		this.TV_timeSlotName.setText(timeSlot.getTimeSlotName());
	} 
}
