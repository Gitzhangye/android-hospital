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
	// �������ذ�ť
	private Button btnReturn;
	// ����ʱ���id�ؼ�
	private TextView TV_timeSlotId;
	// ����ʱ������ƿؼ�
	private TextView TV_timeSlotName;
	/* Ҫ�����ʱ�����Ϣ */
	TimeSlot timeSlot = new TimeSlot(); 
	/* ʱ��ι���ҵ���߼��� */
	private TimeSlotService timeSlotService = new TimeSlotService();
	private int timeSlotId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴ʱ�������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.timeslot_detail);
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    timeSlot = timeSlotService.GetTimeSlot(timeSlotId); 
		this.TV_timeSlotId.setText(timeSlot.getTimeSlotId() + "");
		this.TV_timeSlotName.setText(timeSlot.getTimeSlotName());
	} 
}
