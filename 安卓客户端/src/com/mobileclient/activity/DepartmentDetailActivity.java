package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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

public class DepartmentDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_departmentId;
	// �����������ƿؼ�
	private TextView TV_departmentName;
	/* Ҫ����Ŀ�����Ϣ */
	Department department = new Department(); 
	/* ���ҹ���ҵ���߼��� */
	private DepartmentService departmentService = new DepartmentService();
	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴��������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.department_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_departmentId = (TextView) findViewById(R.id.TV_departmentId);
		TV_departmentName = (TextView) findViewById(R.id.TV_departmentName);
		Bundle extras = this.getIntent().getExtras();
		departmentId = extras.getInt("departmentId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DepartmentDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId); 
		this.TV_departmentId.setText(department.getDepartmentId() + "");
		this.TV_departmentName.setText(department.getDepartmentName());
	} 
}
