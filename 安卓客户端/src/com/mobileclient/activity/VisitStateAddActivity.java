package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;
public class VisitStateAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ��������״̬�����
	private EditText ET_visitStateName;
	protected String carmera_path;
	/*Ҫ����ĳ���״̬��Ϣ*/
	VisitState visitState = new VisitState();
	/*����״̬����ҵ���߼���*/
	private VisitStateService visitStateService = new VisitStateService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-��ӳ���״̬");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.visitstate_add); 
		ET_visitStateName = (EditText) findViewById(R.id.ET_visitStateName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӳ���״̬��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����״̬*/ 
					if(ET_visitStateName.getText().toString().equals("")) {
						Toast.makeText(VisitStateAddActivity.this, "����״̬���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_visitStateName.setFocusable(true);
						ET_visitStateName.requestFocus();
						return;	
					}
					visitState.setVisitStateName(ET_visitStateName.getText().toString());
					/*����ҵ���߼����ϴ�����״̬��Ϣ*/
					VisitStateAddActivity.this.setTitle("�����ϴ�����״̬��Ϣ���Ե�...");
					String result = visitStateService.AddVisitState(visitState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�����״̬�������*/ 
					Intent intent = new Intent();
					intent.setClass(VisitStateAddActivity.this, VisitStateListActivity.class);
					startActivity(intent); 
					VisitStateAddActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
