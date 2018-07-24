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

import com.mobileclient.domain.LeaveWord;
import com.mobileclient.handler.LeaveWordListHandler;
import com.mobileclient.util.HttpUtil;

/*¡Ù—‘π‹¿Ì“µŒÒ¬ﬂº≠≤„*/
public class LeaveWordService {
	/* ÃÌº”¡Ù—‘ */
	public String AddLeaveWord(LeaveWord leaveWord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWord.getLeaveWordId() + "");
		params.put("title", leaveWord.getTitle());
		params.put("content", leaveWord.getContent());
		params.put("addTime", leaveWord.getAddTime());
		params.put("userObj", leaveWord.getUserObj());
		params.put("replyContent", leaveWord.getReplyContent());
		params.put("replyTime", leaveWord.getReplyTime());
		params.put("replyDoctor", leaveWord.getReplyDoctor());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveWordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* ≤È—Ø¡Ù—‘ */
	public List<LeaveWord> QueryLeaveWord(LeaveWord queryConditionLeaveWord) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeaveWordServlet?action=query";
		if(queryConditionLeaveWord != null) {
			urlString += "&title=" + URLEncoder.encode(queryConditionLeaveWord.getTitle(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionLeaveWord.getUserObj(), "UTF-8") + "";
			urlString += "&replyTime=" + URLEncoder.encode(queryConditionLeaveWord.getReplyTime(), "UTF-8") + "";
			urlString += "&replyDoctor=" + URLEncoder.encode(queryConditionLeaveWord.getReplyDoctor(), "UTF-8") + "";
		}
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeaveWordListHandler leaveWordListHander = new LeaveWordListHandler();
		xr.setContentHandler(leaveWordListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LeaveWord> leaveWordList = leaveWordListHander.getLeaveWordList();
		return leaveWordList;
	}
	/* ∏¸–¬¡Ù—‘ */
	public String UpdateLeaveWord(LeaveWord leaveWord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWord.getLeaveWordId() + "");
		params.put("title", leaveWord.getTitle());
		params.put("content", leaveWord.getContent());
		params.put("addTime", leaveWord.getAddTime());
		params.put("userObj", leaveWord.getUserObj());
		params.put("replyContent", leaveWord.getReplyContent());
		params.put("replyTime", leaveWord.getReplyTime());
		params.put("replyDoctor", leaveWord.getReplyDoctor());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveWordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/* …æ≥˝¡Ù—‘ */
	public String DeleteLeaveWord(int leaveWordId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWordId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveWordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "¡Ù—‘–≈œ¢…æ≥˝ ß∞‹!";
		}
	}
	/* ∏˘æ›¡Ù—‘idªÒ»°¡Ù—‘∂‘œÛ */
	public LeaveWord GetLeaveWord(int leaveWordId)  {
		List<LeaveWord> leaveWordList = new ArrayList<LeaveWord>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveWordId", leaveWordId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveWordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveWord leaveWord = new LeaveWord();
				leaveWord.setLeaveWordId(object.getInt("leaveWordId"));
				leaveWord.setTitle(object.getString("title"));
				leaveWord.setContent(object.getString("content"));
				leaveWord.setAddTime(object.getString("addTime"));
				leaveWord.setUserObj(object.getString("userObj"));
				leaveWord.setReplyContent(object.getString("replyContent"));
				leaveWord.setReplyTime(object.getString("replyTime"));
				leaveWord.setReplyDoctor(object.getString("replyDoctor"));
				leaveWordList.add(leaveWord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leaveWordList.size();
		if(size>0) return leaveWordList.get(0); 
		else return null; 
	}
}
