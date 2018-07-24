package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
import com.mobileclient.util.VisitStateSimpleAdapter;
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

public class VisitStateListActivity extends Activity {
	VisitStateSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int visitStateId;
	/* 出诊状态操作业务逻辑层对象 */
	VisitStateService visitStateService = new VisitStateService();
	/*保存查询参数条件的出诊状态对象*/
	private VisitState queryConditionVisitState;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visitstate_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--出诊状态列表");
		} else {
			setTitle("您好：" + username + "   当前位置---出诊状态列表");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionVisitState = (VisitState)extras.getSerializable("queryConditionVisitState");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new VisitStateSimpleAdapter(this, list,
					R.layout.visitstate_list_item,
					new String[] { "visitStateId","visitStateName" },
					new int[] { R.id.tv_visitStateId,R.id.tv_visitStateName,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 添加长按点击
		lv.setOnCreateContextMenuListener(visitStateListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int visitStateId = Integer.parseInt(list.get(arg2).get("visitStateId").toString());
            	Intent intent = new Intent();
            	intent.setClass(VisitStateListActivity.this, VisitStateDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("visitStateId", visitStateId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener visitStateListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑出诊状态信息"); 
			menu.add(0, 1, 0, "删除出诊状态信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑出诊状态信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取状态id
			visitStateId = Integer.parseInt(list.get(position).get("visitStateId").toString());
			Intent intent = new Intent();
			intent.setClass(VisitStateListActivity.this, VisitStateEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("visitStateId", visitStateId);
			intent.putExtras(bundle);
			startActivity(intent);
			VisitStateListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除出诊状态信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取状态id
			visitStateId = Integer.parseInt(list.get(position).get("visitStateId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(VisitStateListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = visitStateService.DeleteVisitState(visitStateId);
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
			/* 查询出诊状态信息 */
			List<VisitState> visitStateList = visitStateService.QueryVisitState(queryConditionVisitState);
			for (int i = 0; i < visitStateList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("visitStateId", visitStateList.get(i).getVisitStateId());
				map.put("visitStateName", visitStateList.get(i).getVisitStateName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "添加出诊状态");
		menu.add(0, 2, 2, "查询出诊状态");
		menu.add(0, 3, 3, "返回主界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// 添加出诊状态信息
			Intent intent = new Intent();
			intent.setClass(VisitStateListActivity.this, VisitStateAddActivity.class);
			startActivity(intent);
			VisitStateListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*查询出诊状态信息*/
			Intent intent = new Intent();
			intent.setClass(VisitStateListActivity.this, VisitStateQueryActivity.class);
			startActivity(intent);
			VisitStateListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*返回主界面*/
			Intent intent = new Intent();
			intent.setClass(VisitStateListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			VisitStateListActivity.this.finish();
		}
		return true; 
	}
}
