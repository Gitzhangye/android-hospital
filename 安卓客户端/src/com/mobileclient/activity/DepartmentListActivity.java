package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
import com.mobileclient.util.DepartmentSimpleAdapter;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DepartmentListActivity extends Activity {
	DepartmentSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int departmentId;
	/* 科室操作业务逻辑层对象 */
	DepartmentService departmentService = new DepartmentService();
	/*保存查询参数条件的科室对象*/
	private Department queryConditionDepartment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.department_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--科室列表");
		} else {
			setTitle("您好：" + username + "   当前位置---科室列表");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionDepartment = (Department)extras.getSerializable("queryConditionDepartment");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new DepartmentSimpleAdapter(this, list,
					R.layout.department_list_item,
					new String[] { "departmentId","departmentName" },
					new int[] { R.id.tv_departmentId,R.id.tv_departmentName,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 添加长按点击
		lv.setOnCreateContextMenuListener(departmentListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int departmentId = Integer.parseInt(list.get(arg2).get("departmentId").toString());
            	Intent intent = new Intent();
            	intent.setClass(DepartmentListActivity.this, DepartmentDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("departmentId", departmentId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener departmentListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) DepartmentListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑科室信息"); 
				menu.add(0, 1, 0, "删除科室信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑科室信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取科室id
			departmentId = Integer.parseInt(list.get(position).get("departmentId").toString());
			Intent intent = new Intent();
			intent.setClass(DepartmentListActivity.this, DepartmentEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("departmentId", departmentId);
			intent.putExtras(bundle);
			startActivity(intent);
			DepartmentListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除科室信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取科室id
			departmentId = Integer.parseInt(list.get(position).get("departmentId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(DepartmentListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = departmentService.DeleteDepartment(departmentId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询科室信息 */
			List<Department> departmentList = departmentService.QueryDepartment(queryConditionDepartment);
			for (int i = 0; i < departmentList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("departmentId", departmentList.get(i).getDepartmentId());
				map.put("departmentName", departmentList.get(i).getDepartmentName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare) DepartmentListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			menu.add(0, 1, 1, "添加科室"); 
			menu.add(0, 2, 2, "返回主界面");
		} else {
			menu.add(0, 1, 1, "返回主界面");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) DepartmentListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// 添加科室信息
				Intent intent = new Intent();
				intent.setClass(DepartmentListActivity.this, DepartmentAddActivity.class);
				startActivity(intent);
				DepartmentListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*返回主界面*/
				Intent intent = new Intent();
				intent.setClass(DepartmentListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				DepartmentListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*返回主界面*/
				Intent intent = new Intent();
				if(declare.getIdentify().equals("doctor"))
					intent.setClass(DepartmentListActivity.this, MainMenuDoctorActivity.class);
				else if(declare.getIdentify().equals("user"))
					intent.setClass(DepartmentListActivity.this, MainMenuUserActivity.class);
	
				startActivity(intent);
				DepartmentListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
