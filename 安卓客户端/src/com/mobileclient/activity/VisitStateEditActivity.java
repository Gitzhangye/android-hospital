package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class VisitStateEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ����״̬idTextView
	private TextView TV_visitStateId;
	// ��������״̬�����
	private EditText ET_visitStateName;
	protected String carmera_path;
	/*Ҫ����ĳ���״̬��Ϣ*/
	VisitState visitState = new VisitState();
	/*����״̬����ҵ���߼���*/
	private VisitStateService visitStateService = new VisitStateService();

	private int visitStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�޸ĳ���״̬");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.visitstate_edit); 
		TV_visitStateId = (TextView) findViewById(R.id.TV_visitStateId);
		ET_visitStateName = (EditText) findViewById(R.id.ET_visitStateName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		visitStateId = extras.getInt("visitStateId");
		initViewData();
		/*�����޸ĳ���״̬��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����״̬*/ 
					if(ET_visitStateName.getText().toString().equals("")) {
						Toast.makeText(VisitStateEditActivity.this, "����״̬���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_visitStateName.setFocusable(true);
						ET_visitStateName.requestFocus();
						return;	
					}
					visitState.setVisitStateName(ET_visitStateName.getText().toString());
					/*����ҵ���߼����ϴ�����״̬��Ϣ*/
					VisitStateEditActivity.this.setTitle("���ڸ��³���״̬��Ϣ���Ե�...");
					String result = visitStateService.UpdateVisitState(visitState);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					/*������ɺ󷵻ص�����״̬�������*/ 
					Intent intent = new Intent();
					intent.setClass(VisitStateEditActivity.this, VisitStateListActivity.class);
					startActivity(intent); 
					VisitStateEditActivity.this.finish();
				} catch (Exception e) {}
			}
		});
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    visitState = visitStateService.GetVisitState(visitStateId);
		this.TV_visitStateId.setText(visitStateId+"");
		this.ET_visitStateName.setText(visitState.getVisitStateName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
