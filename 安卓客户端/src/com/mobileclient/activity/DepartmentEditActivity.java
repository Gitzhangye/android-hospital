package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class DepartmentEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_departmentId;
	// �����������������
	private EditText ET_departmentName;
	protected String carmera_path;
	/*Ҫ����Ŀ�����Ϣ*/
	Department department = new Department();
	/*���ҹ���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();

	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸Ŀ���");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.department_edit); 
		TV_departmentId = (TextView) findViewById(R.id.TV_departmentId);
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		departmentId = extras.getInt("departmentId");
		initViewData();
		/*�����޸Ŀ��Ұ�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					DepartmentEditActivity.this.setTitle("���ڸ��¿�����Ϣ���Ե�...");
					String result = departmentService.UpdateDepartment(department);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص����ҹ������*/ 
					Intent intent = new Intent();
					intent.setClass(DepartmentEditActivity.this, DepartmentListActivity.class);
					startActivity(intent); 
					DepartmentEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId);
		this.TV_departmentId.setText(departmentId+"");
		this.ET_departmentName.setText(department.getDepartmentName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
