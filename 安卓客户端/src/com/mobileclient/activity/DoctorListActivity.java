package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Doctor;
import com.mobileclient.service.DoctorService;
import com.mobileclient.util.DoctorSimpleAdapter;
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

public class DoctorListActivity extends Activity {
	DoctorSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String doctorNo;
	/* ҽ������ҵ���߼������ */
	DoctorService doctorService = new DoctorService();
	/*�����ѯ����������ҽ������*/
	private Doctor queryConditionDoctor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--ҽ���б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---ҽ���б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionDoctor = (Doctor)extras.getSerializable("queryConditionDoctor");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new DoctorSimpleAdapter(this, list,
					R.layout.doctor_list_item,
					new String[] { "doctorNo","departmentObj","name","sex","doctorPhoto","education","inDate","visiteTimes" },
					new int[] { R.id.tv_doctorNo,R.id.tv_departmentObj,R.id.tv_name,R.id.tv_sex,R.id.iv_doctorPhoto,R.id.tv_education,R.id.tv_inDate,R.id.tv_visiteTimes,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(doctorListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String doctorNo = list.get(arg2).get("doctorNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(DoctorListActivity.this, DoctorDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("doctorNo", doctorNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener doctorListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) DoctorListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭ҽ����Ϣ"); 
				menu.add(0, 1, 0, "ɾ��ҽ����Ϣ");
			} 
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ҽ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡҽ�����
			doctorNo = list.get(position).get("doctorNo").toString();
			Intent intent = new Intent();
			intent.setClass(DoctorListActivity.this, DoctorEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("doctorNo", doctorNo);
			intent.putExtras(bundle);
			startActivity(intent);
			DoctorListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ��ҽ����Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡҽ�����
			doctorNo = list.get(position).get("doctorNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(DoctorListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = doctorService.DeleteDoctor(doctorNo);
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
			/* ��ѯҽ����Ϣ */
			List<Doctor> doctorList = doctorService.QueryDoctor(queryConditionDoctor);
			for (int i = 0; i < doctorList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("doctorNo", doctorList.get(i).getDoctorNo());
				map.put("departmentObj", doctorList.get(i).getDepartmentObj());
				map.put("name", doctorList.get(i).getName());
				map.put("sex", doctorList.get(i).getSex());
				byte[] doctorPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ doctorList.get(i).getDoctorPhoto());// ��ȡͼƬ����
				BitmapFactory.Options doctorPhoto_opts = new BitmapFactory.Options();  
				doctorPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(doctorPhoto_data, 0, doctorPhoto_data.length, doctorPhoto_opts); 
				doctorPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(doctorPhoto_opts, -1, 100*100); 
				doctorPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap doctorPhoto = BitmapFactory.decodeByteArray(doctorPhoto_data, 0, doctorPhoto_data.length, doctorPhoto_opts);
					map.put("doctorPhoto", doctorPhoto);
				} catch (OutOfMemoryError err) { }
				map.put("education", doctorList.get(i).getEducation());
				map.put("inDate", doctorList.get(i).getInDate());
				map.put("visiteTimes", doctorList.get(i).getVisiteTimes());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare) DoctorListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			menu.add(0, 1, 1, "���ҽ��");
			menu.add(0, 2, 2, "��ѯҽ��");
			menu.add(0, 3, 3, "����������");
		} else {
			menu.add(0, 1, 1, "��ѯҽ��");
			menu.add(0, 2, 2, "����������");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) DoctorListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) {
			if (item.getItemId() == 1) {
				// ���ҽ����Ϣ
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, DoctorAddActivity.class);
				startActivity(intent);
				DoctorListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯҽ����Ϣ*/
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, DoctorQueryActivity.class);
				startActivity(intent);
				DoctorListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				DoctorListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*��ѯҽ����Ϣ*/
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, DoctorQueryActivity.class);
				startActivity(intent);
				DoctorListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(DoctorListActivity.this, MainMenuUserActivity.class);
				startActivity(intent);
				DoctorListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
