package com.mobileclient.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.OrderInfo;
import com.mobileclient.service.OrderInfoService;
import com.mobileclient.util.OrderInfoSimpleAdapter;
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

public class OrderInfoDoctorListActivity extends Activity {
	OrderInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int orderId;
	/* ԤԼ����ҵ���߼������ */
	OrderInfoService orderInfoService = new OrderInfoService();
	/*�����ѯ����������ԤԼ����*/
	private OrderInfo queryConditionOrderInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--ԤԼ�б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---ԤԼ�б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionOrderInfo = (OrderInfo)extras.getSerializable("queryConditionOrderInfo");
		else {
			queryConditionOrderInfo = new OrderInfo();
			queryConditionOrderInfo.setUesrObj("");
			queryConditionOrderInfo.setDoctor(username);
			queryConditionOrderInfo.setOrderDate(null);
			queryConditionOrderInfo.setTimeSlotObj(0);
			queryConditionOrderInfo.setVisiteStateObj(0); 
		}
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new OrderInfoSimpleAdapter(this, list,
					R.layout.orderinfo_list_item,
					new String[] { "uesrObj","doctor","orderDate","timeSlotObj","visiteStateObj" },
					new int[] { R.id.tv_uesrObj,R.id.tv_doctor,R.id.tv_orderDate,R.id.tv_timeSlotObj,R.id.tv_visiteStateObj,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(orderInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int orderId = Integer.parseInt(list.get(arg2).get("orderId").toString());
            	Intent intent = new Intent();
            	intent.setClass(OrderInfoDoctorListActivity.this, OrderInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("orderId", orderId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener orderInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "����ԤԼ��Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ԤԼ��Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ԤԼ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡԤԼid
			orderId = Integer.parseInt(list.get(position).get("orderId").toString());
			Intent intent = new Intent();
			intent.setClass(OrderInfoDoctorListActivity.this, OrderInfoDoctorEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("orderId", orderId);
			intent.putExtras(bundle);
			startActivity(intent);
			OrderInfoDoctorListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ��ԤԼ��Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡԤԼid
			orderId = Integer.parseInt(list.get(position).get("orderId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(OrderInfoDoctorListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = orderInfoService.DeleteOrderInfo(orderId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯԤԼ��Ϣ */
			List<OrderInfo> orderInfoList = orderInfoService.QueryOrderInfo(queryConditionOrderInfo);
			for (int i = 0; i < orderInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderId",orderInfoList.get(i).getOrderId());
				map.put("uesrObj", orderInfoList.get(i).getUesrObj());
				map.put("doctor", orderInfoList.get(i).getDoctor());
				map.put("orderDate", orderInfoList.get(i).getOrderDate());
				map.put("timeSlotObj", orderInfoList.get(i).getTimeSlotObj());
				map.put("visiteStateObj", orderInfoList.get(i).getVisiteStateObj());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {  
		menu.add(0, 1, 1, "��ѯԤԼ");
		menu.add(0, 2, 2, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			/*��ѯԤԼ��Ϣ*/
			Intent intent = new Intent();
			intent.setClass(OrderInfoDoctorListActivity.this, OrderInfoDoctorQueryActivity.class);
			startActivity(intent);
			OrderInfoDoctorListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(OrderInfoDoctorListActivity.this, MainMenuDoctorActivity.class);
			startActivity(intent);
			OrderInfoDoctorListActivity.this.finish();
		}
		return true; 
	}
}
