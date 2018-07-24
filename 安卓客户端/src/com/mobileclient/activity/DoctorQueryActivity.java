package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Doctor;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class DoctorQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ����ҽ����������
	private EditText ET_doctorNo;
	// �������ڿ���������
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null; 
	/*���ҹ���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();
	// �������������
	private EditText ET_name;
	// ����ѧ�������
	private EditText ET_education;
	// ��Ժ���ڿؼ�
	private DatePicker dp_inDate;
	private CheckBox cb_inDate;
	/*��ѯ�����������浽���������*/
	private Doctor queryConditionDoctor = new Doctor();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ñ���
		setTitle("�ֻ��ͻ���-���ò�ѯҽ������");
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.doctor_query);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_doctorNo = (EditText) findViewById(R.id.ET_doctorNo);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// ��ȡ���еĿ���
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount+1];
		departmentObj_ShowText[0] = "������";
		for(int i=1;i<=departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i-1).getDepartmentName();
		} 
		// ����ѡ������ArrayAdapter��������
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// �������ڿ��������б�ķ��
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionDoctor.setDepartmentObj(departmentList.get(arg2-1).getDepartmentId()); 
				else
					queryConditionDoctor.setDepartmentObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_education = (EditText) findViewById(R.id.ET_education);
		dp_inDate = (DatePicker) findViewById(R.id.dp_inDate);
		cb_inDate = (CheckBox) findViewById(R.id.cb_inDate);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionDoctor.setDoctorNo(ET_doctorNo.getText().toString());
					queryConditionDoctor.setName(ET_name.getText().toString());
					queryConditionDoctor.setEducation(ET_education.getText().toString());
					if(cb_inDate.isChecked()) {
						/*��ȡ��Ժ����*/
						Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
						queryConditionDoctor.setInDate(new Timestamp(inDate.getTime()));
					} else {
						queryConditionDoctor.setInDate(null);
					} 
					/*������ɺ󷵻ص�ҽ���������*/ 
					Intent intent = new Intent();
					intent.setClass(DoctorQueryActivity.this, DoctorListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("queryConditionDoctor", queryConditionDoctor);
					intent.putExtras(bundle);
					startActivity(intent);  
					DoctorQueryActivity.this.finish();
				} catch (Exception e) {}
			}
			});
	}
}
