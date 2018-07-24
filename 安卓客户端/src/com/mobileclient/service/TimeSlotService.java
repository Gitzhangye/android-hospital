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

import com.mobileclient.domain.TimeSlot;
import com.mobileclient.handler.TimeSlotListHandler;
import com.mobileclient.util.HttpUtil;

/*时间段管理业务逻辑层*/
public class TimeSlotService {
	/* 添加时间段 */
	public String AddTimeSlot(TimeSlot timeSlot) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeSlotId", timeSlot.getTimeSlotId() + "");
		params.put("timeSlotName", timeSlot.getTimeSlotName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSlotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 查询时间段 */
	public List<TimeSlot> QueryTimeSlot(TimeSlot queryConditionTimeSlot) throws Exception {
		String urlString = HttpUtil.BASE_URL + "TimeSlotServlet?action=query";
		if(queryConditionTimeSlot != null) {
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		TimeSlotListHandler timeSlotListHander = new TimeSlotListHandler();
		xr.setContentHandler(timeSlotListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<TimeSlot> timeSlotList = timeSlotListHander.getTimeSlotList();
		return timeSlotList;
	}
	/* 更新时间段 */
	public String UpdateTimeSlot(TimeSlot timeSlot) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeSlotId", timeSlot.getTimeSlotId() + "");
		params.put("timeSlotName", timeSlot.getTimeSlotName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSlotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* 删除时间段 */
	public String DeleteTimeSlot(int timeSlotId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeSlotId", timeSlotId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSlotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "时间段信息删除失败!";
		}
	}
	/* 根据时间段id获取时间段对象 */
	public TimeSlot GetTimeSlot(int timeSlotId)  {
		List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeSlotId", timeSlotId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "TimeSlotServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setTimeSlotId(object.getInt("timeSlotId"));
				timeSlot.setTimeSlotName(object.getString("timeSlotName"));
				timeSlotList.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = timeSlotList.size();
		if(size>0) return timeSlotList.get(0); 
		else return null; 
	}
}
