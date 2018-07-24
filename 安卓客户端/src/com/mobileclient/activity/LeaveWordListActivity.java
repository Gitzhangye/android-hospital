package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.LeaveWord;
import com.mobileclient.service.LeaveWordService;
import com.mobileclient.util.LeaveWordSimpleAdapter;
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

public class LeaveWordListActivity extends Activity {
	LeaveWordSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int leaveWordId;
	/* ���Բ���ҵ���߼������ */
	LeaveWordService leaveWordService = new LeaveWordService();
	/*�����ѯ�������������Զ���*/
	private LeaveWord queryConditionLeaveWord;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leaveword_list);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		if (username == null) {
			setTitle("��ǰλ��--�����б�");
		} else {
			setTitle("���ã�" + username + "   ��ǰλ��---�����б�");
		}
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) 
			queryConditionLeaveWord = (LeaveWord)extras.getSerializable("queryConditionLeaveWord");
		setViews();
	}

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		list = getDatas();
		try {
			adapter = new LeaveWordSimpleAdapter(this, list,
					R.layout.leaveword_list_item,
					new String[] { "leaveWordId","title","addTime","userObj","replyTime" },
					new int[] { R.id.tv_leaveWordId,R.id.tv_title,R.id.tv_addTime,R.id.tv_userObj,R.id.tv_replyTime,});
			lv.setAdapter(adapter);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		// ��ӳ������
		lv.setOnCreateContextMenuListener(leaveWordListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int leaveWordId = Integer.parseInt(list.get(arg2).get("leaveWordId").toString());
            	Intent intent = new Intent();
            	intent.setClass(LeaveWordListActivity.this, LeaveWordDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("leaveWordId", leaveWordId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener leaveWordListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭������Ϣ"); 
			menu.add(0, 1, 0, "ɾ��������Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			leaveWordId = Integer.parseInt(list.get(position).get("leaveWordId").toString());
			Intent intent = new Intent();
			intent.setClass(LeaveWordListActivity.this, LeaveWordEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("leaveWordId", leaveWordId);
			intent.putExtras(bundle);
			startActivity(intent);
			LeaveWordListActivity.this.finish();
		} else if (item.getItemId() == 1) {// ɾ��������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			leaveWordId = Integer.parseInt(list.get(position).get("leaveWordId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(LeaveWordListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = leaveWordService.DeleteLeaveWord(leaveWordId);
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
			/* ��ѯ������Ϣ */
			List<LeaveWord> leaveWordList = leaveWordService.QueryLeaveWord(queryConditionLeaveWord);
			for (int i = 0; i < leaveWordList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("leaveWordId", leaveWordList.get(i).getLeaveWordId());
				map.put("title", leaveWordList.get(i).getTitle());
				map.put("addTime", leaveWordList.get(i).getAddTime());
				map.put("userObj", leaveWordList.get(i).getUserObj());
				map.put("replyTime", leaveWordList.get(i).getReplyTime());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 1, "�������");
		menu.add(0, 2, 2, "��ѯ����");
		menu.add(0, 3, 3, "����������");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			// ���������Ϣ
			Intent intent = new Intent();
			intent.setClass(LeaveWordListActivity.this, LeaveWordAddActivity.class);
			startActivity(intent);
			LeaveWordListActivity.this.finish();
		} else if (item.getItemId() == 2) {
			/*��ѯ������Ϣ*/
			Intent intent = new Intent();
			intent.setClass(LeaveWordListActivity.this, LeaveWordQueryActivity.class);
			startActivity(intent);
			LeaveWordListActivity.this.finish();
		} else if (item.getItemId() == 3) {
			/*����������*/
			Intent intent = new Intent();
			intent.setClass(LeaveWordListActivity.this, MainMenuActivity.class);
			startActivity(intent);
			LeaveWordListActivity.this.finish();
		}
		return true; 
	}
}
