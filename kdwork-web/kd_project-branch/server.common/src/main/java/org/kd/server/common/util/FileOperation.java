package org.kd.server.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class FileOperation {
	/**
	 * 创建文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File fileName) throws Exception {
		System.out.println(fileName.getAbsolutePath());
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("是否新建文件:" + flag);
		return flag;
	}

	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readTxtFile(File fileName) throws Exception {
		String result = "";
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				while ((read = bufferedReader.readLine()) != null) {
					result = result + read + "\r\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		System.out.println("读取出来的文件内容是：" + "\r\n" + result);
		return result;
	}
	
	/**
	 * 读环信token的TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	public static Map<String,Object> readEaseMobTokenTxtFile(File fileName) throws Exception {
		Map<String,Object> retMap = new HashMap<String, Object>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(fileReader);
			try {
				String read = "";
				while ((read = bufferedReader.readLine()) != null) {
					if(read.indexOf(":")!=-1){
						String[] kyVl  = read.split(":");
						System.out.println(read);
						retMap.put(kyVl[0], kyVl[1]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (fileReader != null) {
				fileReader.close();
			}
		}
		System.out.println("读取出来的文件内容是：" + "\r\n" + retMap.toString());
		return retMap;
	}

	public static boolean writeTxtFile(String content, File fileName)
			throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(fileName);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	public static void contentToTxt(String filePath, String content) {
		String str = new String(); // 原有txt内容
		String s1 = new String();// 内容更新
		try {
			File f = new File(filePath);
			if (f.exists()) {
				System.out.println("文件存在");
			} else {
				System.out.println("文件不存在");
				f.createNewFile();// 不存在则创建
			}
			BufferedReader input = new BufferedReader(new FileReader(f));

			while ((str = input.readLine()) != null) {
				s1 += str + "\n";
			}
			System.out.println("原内容:" + s1);
			input.close();
			s1 += content;

			BufferedWriter output = new BufferedWriter(new FileWriter(f));
			output.write(s1);
			output.close();

			System.out.println("新内容:" + s1);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String[] args) throws Exception {
		FileOperation.createFile(new File("/easeMob_access_token.txt"));
		FileOperation.writeTxtFile("Hello World\r\nBeautiful\r\nForever", new File(
				"/easeMob_access_token.txt"));
		FileOperation.readTxtFile(new File("/easeMob_access_token.txt"));
		FileOperation.contentToTxt("/easeMob_access_token.txt",
				"LIKE VERY MUCH");
	}
}
