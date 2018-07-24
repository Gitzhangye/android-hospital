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
	// 声明返回按钮
	private Button btnReturn;
	// 声明科室id控件
	private TextView TV_departmentId;
	// 声明科室名称控件
	private TextView TV_departmentName;
	/* 要保存的科室信息 */
	Department department = new Department(); 
	/* 科室管理业务逻辑层 */
	private DepartmentService departmentService = new DepartmentService();
	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看科室详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.department_detail);
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId); 
		this.TV_departmentId.setText(department.getDepartmentId() + "");
		this.TV_departmentName.setText(department.getDepartmentName());
	} 
}
