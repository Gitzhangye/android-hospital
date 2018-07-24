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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明时间段名称输入框
	private EditText ET_timeSlotName;
	protected String carmera_path;
	/*要保存的时间段信息*/
	TimeSlot timeSlot = new TimeSlot();
	/*时间段管理业务逻辑层*/
	private TimeSlotService timeSlotService = new TimeSlotService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加时间段");
		// 设置当前Activity界面布局
		setContentView(R.layout.timeslot_add); 
		ET_timeSlotName = (EditText) findViewById(R.id.ET_timeSlotName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加时间段按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取时间段名称*/ 
					if(ET_timeSlotName.getText().toString().equals("")) {
						Toast.makeText(TimeSlotAddActivity.this, "时间段名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_timeSlotName.setFocusable(true);
						ET_timeSlotName.requestFocus();
						return;	
					}
					timeSlot.setTimeSlotName(ET_timeSlotName.getText().toString());
					/*调用业务逻辑层上传时间段信息*/
					TimeSlotAddActivity.this.setTitle("正在上传时间段信息，稍等...");
					String result = timeSlotService.AddTimeSlot(timeSlot);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到时间段管理界面*/ 
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
