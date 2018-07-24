package com.mobileclient.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.Environment;

import com.mobileclient.activity.photoListActivity;

public class HttpUtil {
	// 基础URL
	public static final String BASE_URL = "http://192.168.1.104:8080/JavaWebProject/";
	public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mobileclient";
	

	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url) { 
		HttpGet request = new HttpGet(url);
		return request;
	}

	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url) {
		// 根据url获得HttpPost对象
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 发送Get请求，获得响应查询结果
	public static String queryStringForGet(String url) {
		// 获得HttpGet对象
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	public static boolean sendXML(String path, String xml) throws Exception {
		byte[] data = xml.getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			return true;
		}
		return false;
	}

	public static byte[] sendGetRequest(String path,
			Map<String, String> params, String enc) throws Exception {
		StringBuilder sb = new StringBuilder(path);
		sb.append('?');
		// ?method=save&title=435435435&timelength=89&
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), enc))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);

		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);

		if (conn.getResponseCode() == 200) {
			return readStream(conn.getInputStream());
		}
		return null;
	}

	public static boolean sendPostRequest(String path,
			Map<String, String> params, String enc) throws Exception {
		// title=dsfdsf&timelength=23&method=save
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue(), enc))
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] entitydata = sb.toString().getBytes();// 得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
		// Content-Type: application/x-www-form-urlencoded
		// Content-Length: 38
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			return true;
		}
		return false;
	}

	public static byte[] SendPostRequest(String path,
			Map<String, String> params, String enc) throws Exception {
		// title=dsfdsf&timelength=23&method=save
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue(), enc))
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] entitydata = sb.toString().getBytes();// 得到实体的二进制数据
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);// 如果通过post提交数据，必须设置允许对外输出数据
		// Content-Type: application/x-www-form-urlencoded
		// Content-Length: 38
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			return readStream(conn.getInputStream());
		}
		return null;
	}

	// SSL HTTPS Cookie
	public static boolean sendRequestFromHttpClient(String path,
			Map<String, String> params, String enc) throws Exception {
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				enc);// 得到经过编码过后的实体数据
		HttpPost post = new HttpPost(path); // form
		post.setEntity(entitydata);
		DefaultHttpClient client = new DefaultHttpClient(); // 浏览器
		HttpResponse response = client.execute(post);// 执行请求
		if (response.getStatusLine().getStatusCode() == 200) {
			return true;
		}
		return false;
	}

	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

	/* 上传文件至Server的方法 */
	public static String uploadFile(String filename) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		try {
			URL url = new URL(HttpUtil.BASE_URL + "UpPhotoServlet");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + filename + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			File file = new File(HttpUtil.FILE_PATH + "/" + filename);
			FileInputStream fStream = new FileInputStream(file);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */
			/* 关闭DataOutputStream */
			ds.close();
			return b.toString();
		} catch (Exception e) {
			return "";
		}

	}

}
