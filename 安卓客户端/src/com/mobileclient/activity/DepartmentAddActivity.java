package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
public class DepartmentAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明科室名称输入框
	private EditText ET_departmentName;
	protected String carmera_path;
	/*要保存的科室信息*/
	Department department = new Department();
	/*科室管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-添加科室");
		// 设置当前Activity界面布局
		setContentView(R.layout.department_add); 
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加科室按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取科室名称*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentAddActivity.this, "科室名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*调用业务逻辑层上传科室信息*/
					DepartmentAddActivity.this.setTitle("正在上传科室信息，稍等...");
					String result = departmentService.AddDepartment(department);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*操作完成后返回到科室管理界面*/ 
					Intent intent = new Intent();
					intent.setClass(DepartmentAddActivity.this, DepartmentListActivity.class);
					startActivity(intent); 
					DepartmentAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
