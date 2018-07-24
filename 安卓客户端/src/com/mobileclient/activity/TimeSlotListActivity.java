package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.TimeSlot;
import com.mobileclient.service.TimeSlotService;
import com.mobileclient.util.TimeSlotSimpleAdapter;
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

public class TimeSlotListActivity extends Activity {
	TimeSlotSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int timeSlotId;
	/* 时间段操作业务逻辑层对象 */
	TimeSlotService timeSlotService = new TimeSlotService();
	/*保存查询参数条件的时间段对象*/
	private TimeSlot queryConditionTimeSlot;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeslot_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("当前位置--时间段列表");
		} else {
			setTitle("您好：" + username + "   当前位置---时间段列表");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionTimeSlot = (TimeSlot)extras.getSerializable("queryConditionTimeSlot");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new TimeSlotSimpleAdapter(this, list,
					R.layout.timeslot_list_item,
					new String[] { "timeSlotId","timeSlotName" },
					new int[] { R.id.tv_timeSlotId,R.id.tv_timeSlotName,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// 添加长按点击
		lv.setOnCreateContextMenuListener(timeSlotListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int timeSlotId = Integer.parseInt(list.get(arg2).get("timeSlotId").toString());
            	Intent intent = new Intent();
            	intent.setClass(TimeSlotListActivity.this, TimeSlotDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("timeSlotId", timeSlotId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener timeSlotListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑时间段信息"); 
			menu.add(0, 1, 0, "删除时间段信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑时间段信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取时间段id
			timeSlotId = Integer.parseInt(list.get(position).get("timeSlotId").toString());
			Intent intent = new Intent();
			intent.setClass(TimeSlotListActivity.this, TimeSlotEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("timeSlotId", timeSlotId);
			intent.putExtras(bundle);
			startActivity(intent);
			TimeSlotListActivity.this.finish();
		} else if (item.getItemId() == 1) {// 删除时间段信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取时间段id
			timeSlotId = Integer.parseInt(list.get(position).get("timeSlotId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(TimeSlotListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = timeSlotService.DeleteTimeSlot(timeSlotId);
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
			/* 查询时间段信息 */
			List<TimeSlot> timeSlotList = timeSlotService.QueryTimeSlot(queryConditionTimeSlot);
			for (int i = 0; i < timeSlotList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timeSlotId", timeSlotList.get(i).getTimeSlotId());
				map.put("timeSlotName", timeSlotList.get(i).getTimeSlotName());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "添加时间段");
		menu.add(0, 2, 2, "查询时间段");
		menu.add(0, 3, 3, "返回主界面");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// 添加时间段信息
			Intent intent = new Intent();
			intent.setClass(TimeSlotListActivity.this, TimeSlotAddActivity.class);
			startActivity(intent);
			TimeSlotListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*查询时间段信息*/
			Intent intent = new Intent();
			intent.setClass(TimeSlotListActivity.this, TimeSlotQueryActivity.class);
			startActivity(intent);
			TimeSlotListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*返回主界面*/
			Intent intent = new Intent();
			intent.setClass(TimeSlotListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			TimeSlotListActivity.this.finish();
		}
		return true; 
	}
}
