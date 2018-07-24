package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.UserInfo;
import com.mobileclient.service.UserInfoService;
import com.mobileclient.util.UserInfoSimpleAdapter;
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

public class UserInfoListActivity extends Activity {
	UserInfoSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String user_name;
	/* �û�����ҵ���߼������ */
	UserInfoService userInfoService = new UserInfoService();
	/*�����ѯ�����������û�����*/
	private UserInfo queryConditionUserInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfo_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--�û��б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---�û��б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionUserInfo = (UserInfo)extras.getSerializable("queryConditionUserInfo");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new UserInfoSimpleAdapter(this, list,
					R.layout.userinfo_list_item,
					new String[] { "user_name","name","sex","userPhoto","birthday","jiguan" },
					new int[] { R.id.tv_user_name,R.id.tv_name,R.id.tv_sex,R.id.iv_userPhoto,R.id.tv_birthday,R.id.tv_jiguan,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(userInfoListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String user_name = list.get(arg2).get("user_name").toString();
            	Intent intent = new Intent();
            	intent.setClass(UserInfoListActivity.this, UserInfoDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("user_name", user_name);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener userInfoListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) UserInfoListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "�༭�û���Ϣ"); 
				menu.add(0, 1, 0, "ɾ���û���Ϣ");
			}
			
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) { 
		if (item.getItemId() == 0) {  //�༭�û���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�û���
			user_name = list.get(position).get("user_name").toString();
			Intent intent = new Intent();
			intent.setClass(UserInfoListActivity.this, UserInfoEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("user_name", user_name);
			intent.putExtras(bundle);
			startActivity(intent);
			UserInfoListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ���û���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�û���
			user_name = list.get(position).get("user_name").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(UserInfoListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = userInfoService.DeleteUserInfo(user_name);
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
			/* ��ѯ�û���Ϣ */
			List<UserInfo> userInfoList = userInfoService.QueryUserInfo(queryConditionUserInfo);
			for (int i = 0; i < userInfoList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user_name", userInfoList.get(i).getUser_name());
				map.put("name", userInfoList.get(i).getName());
				map.put("sex", userInfoList.get(i).getSex());
				byte[] userPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ userInfoList.get(i).getUserPhoto());// ��ȡͼƬ����
				BitmapFactory.Options userPhoto_opts = new BitmapFactory.Options();  
				userPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(userPhoto_data, 0, userPhoto_data.length, userPhoto_opts); 
				userPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(userPhoto_opts, -1, 100*100); 
				userPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap userPhoto = BitmapFactory.decodeByteArray(userPhoto_data, 0, userPhoto_data.length, userPhoto_opts);
					map.put("userPhoto", userPhoto);
				} catch (OutOfMemoryError err) { }
				map.put("birthday", userInfoList.get(i).getBirthday());
				map.put("jiguan", userInfoList.get(i).getJiguan());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Declare declare = (Declare) UserInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) { 
			menu.add(0, 1, 1, "����û�");
			menu.add(0, 2, 2, "��ѯ�û�");
			menu.add(0, 3, 3, "����������");
		} else {
			menu.add(0, 1, 1, "��ѯ�û�");
			menu.add(0, 2, 2, "����������");
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Declare declare = (Declare) UserInfoListActivity.this.getApplication();
		if(declare.getIdentify().equals("admin")) { 
			if (item.getItemId() == 1) {
				// ����û���Ϣ
				Intent intent = new Intent();
				intent.setClass(UserInfoListActivity.this, UserInfoAddActivity.class);
				startActivity(intent);
				UserInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*��ѯ�û���Ϣ*/
				Intent intent = new Intent();
				intent.setClass(UserInfoListActivity.this, UserInfoQueryActivity.class);
				startActivity(intent);
				UserInfoListActivity.this.finish();
			} else if (item.getItemId() == 3) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(UserInfoListActivity.this, MainMenuActivity.class);
				startActivity(intent);
				UserInfoListActivity.this.finish();
			}
		} else {
			if (item.getItemId() == 1) {
				/*��ѯ�û���Ϣ*/
				Intent intent = new Intent();
				intent.setClass(UserInfoListActivity.this, UserInfoQueryActivity.class);
				startActivity(intent);
				UserInfoListActivity.this.finish();
			} else if (item.getItemId() == 2) {
				/*����������*/
				Intent intent = new Intent();
				intent.setClass(UserInfoListActivity.this, MainMenuDoctorActivity.class);
				startActivity(intent);
				UserInfoListActivity.this.finish();
			}
		}
		
		return true; 
	}
}
