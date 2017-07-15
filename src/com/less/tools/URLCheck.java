package com.less.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class URLCheck {
	private static URL url;
	private static HttpURLConnection con;
	private static int state = -1;
	
	private interface Callback {
		void success(String url);
	}

	/**
	 * ���ܣ���⵱ǰURL�Ƿ�����ӻ��Ƿ���Ч, ����������������� 5 ��, ��� 5 �ζ����ɹ�����Ϊ�õ�ַ������
	 * 
	 * @param urlStr
	 *            ָ��URL�����ַ
	 * @return URL
	 */
	public static synchronized URL isConnect(String urlStr,Callback callback) {
//		System.out.println("==========> ���ڲ���  "+urlStr);
		int counts = 0;
		if (urlStr == null || urlStr.length() <= 0) {
			return null;
		}
		
		while (counts < 2) {
			try {
				url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(3000);
				con.setReadTimeout(3000);
				state = con.getResponseCode();
//				System.out.println("Times: " +counts + " state: " + state);
				if (state == 200) {
					callback.success(urlStr);
				}
				break;
			} catch (Exception ex) {
				counts++;
//				System.out.println("URL�����ã����ӵ� " + counts + " ��");
				continue;
			}
		}
		return url;
	}

	private static Map<String,String> loadUrls() throws IOException {
		String URLCHECK_PATH = getProperty("URLCHECK_PATH");
		
		Map<String,String> map = new HashMap<>();
		List<String> lines = FileUtils.readLines(new File(URLCHECK_PATH), "utf-8");
		String regex = "\\|\\|";
		for(String line : lines){
			try {
				String [] name_value = line.split(regex);
				String name = name_value[0];
				String value = name_value[1];
				map.put(name, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static void print(Map<String,String> map){
		System.out.println(map);
	}
	
	public static String getProperty(String key){
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File("local.properties")));
			String value = properties.getProperty(key,null);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		Map<String,String> map = loadUrls();
		for(String key : map.keySet()){
			String url = map.get(key);
			URLCheck.isConnect(url,new Callback() {
				@Override
				public void success(String url) {
					System.out.println(" ###################### "+key + " : "+ url + "  ����  ######################");
				}
			});			
			
		}
	}
}