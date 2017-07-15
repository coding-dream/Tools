package com.less.tools;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

public class RepeatCheck {
	private static String encoding = "UTF-8";
	
	public static void main(String[] args) throws Exception {
		File file = new File("F://xx.txt");
		List<String> lines = loadFile(file);
		writeFile(lines,file);
		
	}
	

	private static List loadFile(File file) {
		try {
			List<String> lines = FileUtils.readLines(file,encoding );
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static void writeFile(List<String>lines, File file) throws Exception {
		Set<String> set = new HashSet<>();
		for(String line:lines){
			set.add(line);
		}
		
		Iterator<String> iterator = set.iterator();
		StringBuilder builder = new StringBuilder();
		while(iterator.hasNext()){
			String line = iterator.next();
			builder.append(line+"\r\n");
		}
		
		FileUtils.writeStringToFile(file, builder.toString(), encoding);
		System.out.println("======= end ======");
		
	}
	

}
