package com.less.tools;

import java.io.File;

import org.apache.commons.io.FileUtils;


public class TransEncode {
	private static String dir = "F:\\xx\\image\\java";
	public static void main(String[] args) throws Exception {
		File folder = new File(dir);
		list(folder);
	}
	
	public static void list(File parent) throws Exception{
		for(File file:parent.listFiles()){
			if(file.isDirectory()){
				list(file);
			}else{
				transFile(file,null,"UTF-8");
			}
		}
	}

	private static void transFile(File file,String fromCharset,String toCharset) throws Exception {
		String data = FileUtils.readFileToString(file, fromCharset);
		System.out.println(data);
		
		FileUtils.writeStringToFile(file, new String(data.getBytes("GBK")),"utf-8");
	}
	
}
