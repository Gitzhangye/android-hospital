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
	// 声明返回按钮
	private Button btnReturn;
	// 声明状态id控件
	private TextView TV_visitStateId;
	// 声明出诊状态控件
	private TextView TV_visitStateName;
	/* 要保存的出诊状态信息 */
	VisitState visitState = new VisitState(); 
	/* 出诊状态管理业务逻辑层 */
	private VisitStateService visitStateService = new VisitStateService();
	private int visitStateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置标题
		setTitle("手机客户端-查看出诊状态详情");
		// 设置当前Activity界面布局
		setContentView(R.layout.visitstate_detail);
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    visitState = visitStateService.GetVisitState(visitStateId); 
		this.TV_visitStateId.setText(visitState.getVisitStateId() + "");
		this.TV_visitStateName.setText(visitState.getVisitStateName());
	} 
}
