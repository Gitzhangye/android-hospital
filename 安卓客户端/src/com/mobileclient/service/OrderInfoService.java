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

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.handler.OrderInfoListHandler;
import com.mobileclient.util.HttpUtil;

/*ԤԼ����ҵ���߼���*/
public class OrderInfoService {
	/* ���ԤԼ */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("uesrObj", orderInfo.getUesrObj());
		params.put("doctor", orderInfo.getDoctor());
		params.put("orderDate", orderInfo.getOrderDate().toString());
		params.put("timeSlotObj", orderInfo.getTimeSlotObj() + "");
		params.put("visiteStateObj", orderInfo.getVisiteStateObj() + "");
		params.put("introduce", orderInfo.getIntroduce());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ��ѯԤԼ */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&uesrObj=" + URLEncoder.encode(queryConditionOrderInfo.getUesrObj(), "UTF-8") + "";
			urlString += "&doctor=" + URLEncoder.encode(queryConditionOrderInfo.getDoctor(), "UTF-8") + "";
			if(queryConditionOrderInfo.getOrderDate() != null) {
				urlString += "&orderDate=" + URLEncoder.encode(queryConditionOrderInfo.getOrderDate().toString(), "UTF-8");
			}
			urlString += "&timeSlotObj=" + queryConditionOrderInfo.getTimeSlotObj();
			urlString += "&visiteStateObj=" + queryConditionOrderInfo.getVisiteStateObj();
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;
	}
	/* ����ԤԼ */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("uesrObj", orderInfo.getUesrObj());
		params.put("doctor", orderInfo.getDoctor());
		params.put("orderDate", orderInfo.getOrderDate().toString());
		params.put("timeSlotObj", orderInfo.getTimeSlotObj() + "");
		params.put("visiteStateObj", orderInfo.getVisiteStateObj() + "");
		params.put("introduce", orderInfo.getIntroduce());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ɾ��ԤԼ */
	public String DeleteOrderInfo(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "ԤԼ��Ϣɾ��ʧ��!";
		}
	}
	/* ����ԤԼid��ȡԤԼ���� */
	public OrderInfo GetOrderInfo(int orderId)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUesrObj(object.getString("uesrObj"));
				orderInfo.setDoctor(object.getString("doctor"));
				orderInfo.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				orderInfo.setTimeSlotObj(object.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(object.getInt("visiteStateObj"));
				orderInfo.setIntroduce(object.getString("introduce"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
