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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����������������
	private EditText ET_departmentName;
	protected String carmera_path;
	/*Ҫ����Ŀ�����Ϣ*/
	Department department = new Department();
	/*���ҹ���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��ӿ���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.department_add); 
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӿ��Ұ�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					DepartmentAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = departmentService.AddDepartment(department);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����ҹ������*/ 
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
