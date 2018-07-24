package com.mobileclient.service;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.mobileclient.domain.VisitState;
import com.mobileclient.handler.VisitStateListHandler;
import com.mobileclient.util.HttpUtil;

/*����״̬����ҵ���߼���*/
public class VisitStateService {
	/* ��ӳ���״̬ */
	public String AddVisitState(VisitState visitState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitState.getVisitStateId() + "");
		params.put("visitStateName", visitState.getVisitStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯ����״̬ */
	public List<VisitState> QueryVisitState(VisitState queryConditionVisitState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "VisitStateServlet?action=query";
		if(queryConditionVisitState != null) {
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		VisitStateListHandler visitStateListHander = new VisitStateListHandler();
		xr.setContentHandler(visitStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<VisitState> visitStateList = visitStateListHander.getVisitStateList();
		return visitStateList;
	}
	/* ���³���״̬ */
	public String UpdateVisitState(VisitState visitState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitState.getVisitStateId() + "");
		params.put("visitStateName", visitState.getVisitStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ������״̬ */
	public String DeleteVisitState(int visitStateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitStateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "����״̬��Ϣɾ��ʧ��!";
		}
	}
	/* ����״̬id��ȡ����״̬���� */
	public VisitState GetVisitState(int visitStateId)  {
		List<VisitState> visitStateList = new ArrayList<VisitState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitStateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				VisitState visitState = new VisitState();
				visitState.setVisitStateId(object.getInt("visitStateId"));
				visitState.setVisitStateName(object.getString("visitStateName"));
				visitStateList.add(visitState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = visitStateList.size();
		if(size>0) return visitStateList.get(0); 
		else return null; 
	}
}
