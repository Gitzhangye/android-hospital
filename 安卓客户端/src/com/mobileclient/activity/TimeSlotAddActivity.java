package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.TimeSlotService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class TimeSlotAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����ʱ������������
	private EditText ET_timeSlotName;
	protected String carmera_path;
	/*Ҫ�����ʱ�����Ϣ*/
	TimeSlot timeSlot = new TimeSlot();
	/*ʱ��ι���ҵ���߼���*/
	private TimeSlotService timeSlotService = new TimeSlotService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ʱ���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.timeslot_add); 
		ET_timeSlotName = (EditText) findViewById(R.id.ET_timeSlotName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ʱ��ΰ�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡʱ�������*/ 
					if(ET_timeSlotName.getText().toString().equals("")) {
						Toast.makeText(TimeSlotAddActivity.this, "ʱ����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_timeSlotName.setFocusable(true);
						ET_timeSlotName.requestFocus();
						return;	
					}
					timeSlot.setTimeSlotName(ET_timeSlotName.getText().toString());
					/*����ҵ���߼����ϴ�ʱ�����Ϣ*/
					TimeSlotAddActivity.this.setTitle("�����ϴ�ʱ�����Ϣ���Ե�...");
					String result = timeSlotService.AddTimeSlot(timeSlot);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�ʱ��ι������*/ 
					Intent intent = new Intent();
					intent.setClass(TimeSlotAddActivity.this, TimeSlotListActivity.class);
					startActivity(intent); 
					TimeSlotAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
