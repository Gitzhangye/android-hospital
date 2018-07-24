package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.VisitState;
import com.mobileclient.service.VisitStateService;
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

public class VisitStateDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����״̬id�ؼ�
	private TextView TV_visitStateId;
	// ��������״̬�ؼ�
	private TextView TV_visitStateName;
	/* Ҫ����ĳ���״̬��Ϣ */
	VisitState visitState = new VisitState(); 
	/* ����״̬����ҵ���߼��� */
	private VisitStateService visitStateService = new VisitStateService();
	private int visitStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-�鿴����״̬����");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.visitstate_detail);
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_visitStateId = (TextView) findViewById(R.id.TV_visitStateId);
		TV_visitStateName = (TextView) findViewById(R.id.TV_visitStateName);
		Bundle extras = this.getIntent().getExtras();
		visitStateId = extras.getInt("visitStateId");
		initViewData();
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				VisitStateDetailActivity.this.finish();
			}
		}); 
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    visitState = visitStateService.GetVisitState(visitStateId); 
		this.TV_visitStateId.setText(visitState.getVisitStateId() + "");
		this.TV_visitStateName.setText(visitState.getVisitStateName());
	} 
}
