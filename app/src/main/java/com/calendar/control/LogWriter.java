package com.calendar.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class LogWriter {
	
	//����ģʽ
	private static LogWriter mLogFile;

	private static String mPath;
	
	private static Writer mWriter;
	
	private static SimpleDateFormat df;
	
	private LogWriter(String file_path) {
		this.mPath = file_path;
		this.mWriter = null;
		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
		TimeZone.setDefault(tz);
	}
	/**����open���������ʽ�����ļ���ָ��·��������ʼ��mWriter�ʹ�����־�ļ���ʱ��*/
	public static LogWriter open(String file_path) throws IOException {
		if (mLogFile == null) {
			mLogFile = new LogWriter(file_path);
		}
		File mFile = new File(mPath);
		mWriter = new BufferedWriter(new FileWriter(mPath,true),  2048);//new FileWriter(file,true)
		df = new SimpleDateFormat("[yyyy-MM-dd hh:mm:ss]: ");
		df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return mLogFile;
	}
	
	public void close() throws IOException {
		mWriter.close();
	}
	
	public void print(String log) throws IOException {
		mWriter.write(df.format(new Date()));
		mWriter.write(log);
		mWriter.write("\r\n");
		mWriter.flush();
	}
	
	//���ڲ鿴���ĸ���
	public void print(Class cls, String log) throws IOException { 
		
		mWriter.write(df.format(new Date()));
		mWriter.write(cls.getSimpleName() + " ");
		mWriter.write(log);
		mWriter.write("\r\n");
		mWriter.flush();
	}
}

