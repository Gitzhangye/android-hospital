package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class VisitStateEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明状态idTextView
	private TextView TV_visitStateId;
	// 声明出诊状态输入框
	private EditText ET_visitStateName;
	protected String carmera_path;
	/*要保存的出诊状态信息*/
	VisitState visitState = new VisitState();
	/*出诊状态管理业务逻辑层*/
	private VisitStateService visitStateService = new VisitStateService();

	private int visitStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-修改出诊状态");
		// 设置当前Activity界面布局
		setContentView(R.layout.visitstate_edit); 
		TV_visitStateId = (TextView) findViewById(R.id.TV_visitStateId);
		ET_visitStateName = (EditText) findViewById(R.id.ET_visitStateName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		visitStateId = extras.getInt("visitStateId");
		initViewData();
		/*单击修改出诊状态按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取出诊状态*/ 
					if(ET_visitStateName.getText().toString().equals("")) {
						Toast.makeText(VisitStateEditActivity.this, "出诊状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_visitStateName.setFocusable(true);
						ET_visitStateName.requestFocus();
						return;	
					}
					visitState.setVisitStateName(ET_visitStateName.getText().toString());
					/*调用业务逻辑层上传出诊状态信息*/
					VisitStateEditActivity.this.setTitle("正在更新出诊状态信息，稍等...");
					String result = visitStateService.UpdateVisitState(visitState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到出诊状态管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(VisitStateEditActivity.this, VisitStateListActivity.class);
					startActivity(intent); 
					VisitStateEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    visitState = visitStateService.GetVisitState(visitStateId);
		this.TV_visitStateId.setText(visitStateId+"");
		this.ET_visitStateName.setText(visitState.getVisitStateName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
