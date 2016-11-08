package com.common.dbhelp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;




//�׶α�����ݶ���

public class WeekDataObject 
{
	public static double[]   Max_XValues;					//�����洢����-����ѹ
	public static double[]   Min_XValues;					//�����洢����-����ѹ
	public static String[]   BeatCount;						//�������
	public static String[]   TimeStr;						//����ʱ��
	
	public static Boolean	HasData;					//
	
	public static int       DataCount=0;
	
	public static double[]   Max_YValues;					//�����洢����-����ѹ
	public static double[]   Min_YValues;					//�����洢����-����ѹ
	
	
	public static String BeginStr;
	public static String EndStr;
	
	
	
	
	//��ȡ����Ϊ���ĵ�7�����ʼ����
	public static Boolean GetWeekRange(String[] WeekCache)
	{
		Boolean 	Ret=true;
		int 		mYear,mMonth,mDay;
		
		String		mWay;
		String      StartTime,EndTime="";
		int    		WeekFlag=0;
		
		final Calendar Today = Calendar.getInstance(); 
		 
		Today.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
				 
		Calendar StartDate= Calendar.getInstance();  
		Calendar EndDate= Calendar.getInstance();  
		
		
		
		
				
		 StartDate.add(Calendar.DATE, -6);
		 
		 //EndDate.add(Calendar.DATE, 3);
		
				 
		 StartTime=	 WndAssistant.GetDateStr(StartDate);
		        
		 EndTime=WndAssistant.GetDateStr(EndDate);
		 WeekCache[0]=StartTime;
		 WeekCache[1]=EndTime;
		 return Ret;
		
		
	}
	//��ȡ����Ϊ���ĵ�7�����ʼ����
		public static Boolean GetMonthRange(String[] WeekCache)
		{
			Boolean 	Ret=true;
			int 		mYear,mMonth,mDay;
			
			String		mWay;
			String      StartTime,EndTime="";
			int    		WeekFlag=0;
			
			final Calendar Today = Calendar.getInstance(); 
			 
			Today.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
					 
			Calendar StartDate= Calendar.getInstance();  
			Calendar EndDate= Calendar.getInstance();  
			
			
			
			
					
			 StartDate.add(Calendar.DATE, -29);
			 
			 //EndDate.add(Calendar.DATE, 15);
			
					 
			 StartTime=	 WndAssistant.GetDateStr(StartDate);
			        
			 EndTime=WndAssistant.GetDateStr(EndDate);
			 WeekCache[0]=StartTime;
			 WeekCache[1]=EndTime;
			 return Ret;
			
			
		}
	//����TodayStr����ʼʱ��֮��������
	public static int GetDateFactor(String TodayStr)
    {
    	int Factor=0;
    	
    	Date Fromdate=GetDateByStr(BeginStr);  
    	Calendar calendar=Calendar.getInstance();  
    	calendar.setTime(Fromdate); 
    	
    	
    	
    	Date Enddate=GetDateByStr(TodayStr);  
    	Calendar calendarEnd=Calendar.getInstance();  
    	calendarEnd.setTime(Enddate); 
    	
    	
    	
    	
    	
    	long gap = (calendarEnd.getTimeInMillis()-calendar.getTimeInMillis())/(1000*3600*24);//�Ӽ�������ɼ������</strong> 
    	
    	Factor=(int) gap;
    	
    	return Factor;
    	
    }
	private static int GetIntervalDays(Calendar From,Calendar End)
	{
		int Ret=0;
		Calendar BeginCal,EndCal;
		
		if(From.compareTo(End)>0)
		{
			EndCal=End;
			BeginCal=From;
			
		}
		else
		{
			EndCal=From;
			BeginCal=From;			
		}
		
		while(BeginCal.compareTo(EndCal)<0)
		{
			
			BeginCal.add(Calendar.DATE, 1);
			Ret++;
		}
		
		return Ret;
		
		
	}
	
	public static Date GetDateByStr(String DateStr)
	{
		String RetDate;
		String dateStr=DateStr;  
		String pattern="yyyy-MM-dd";  
		SimpleDateFormat  dateFormat=new SimpleDateFormat(pattern);  
		
		Date date=null;
		try 
		{
			date = dateFormat.parse(dateStr);
		} 
		catch (ParseException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return date;
		
	}
	public static Calendar GetStrCal(String DateStr)
	{
		Date Fromdate=GetDateByStr(DateStr);  
    	Calendar calendar=Calendar.getInstance();  
    	calendar.setTime(Fromdate); 
		return calendar;
	}
	//�����ַ��Ӧ�������ֵ
    public static float GetTimeHour_Extern(String TimeStr)
    {
    	int Hour=0;
    	int Minutes=0;
    	int Factor=0;
    	
    	String DateStr="";
    	
    	String[] DTArray=TimeStr.split(" ");
		
		if(DTArray.length>1)
		{
			DateStr=DTArray[0];
			TimeStr=DTArray[1];
    	
		}
    	
		Factor=WeekDataObject.GetDateFactor(DateStr);
		
		
    	String[] TimeSp=TimeStr.split(":");
    	float    HunNum=0;
    	
    	
    	
    	
    	if(TimeSp.length>=2)
    	{
    		
    		Hour=Integer.parseInt(TimeSp[0]);
    		Minutes=Integer.parseInt(TimeSp[1]);
    		
    		float Temp=(float)Minutes/60;
    		
    		HunNum=Float.parseFloat(String.format("%.2f",Temp));
    		
    	}
    	
    	return Factor*24+Hour+HunNum;
    	
    	
    }
    public static void InitDataCache(int DataCount)
    {
    	
    	WeekDataObject.Max_XValues=new double[DataCount];
    	WeekDataObject.Min_XValues=new double[DataCount];
    	
    	WeekDataObject.Max_YValues=new double[DataCount];
    	WeekDataObject.Min_YValues=new double[DataCount];
    	
    	WeekDataObject.BeatCount=new String[DataCount];
    	WeekDataObject.TimeStr=new String[DataCount];
    	
    	WeekDataObject.DataCount=DataCount;
    	
    	if(DataCount>0)
    		WeekDataObject.HasData=true;
    	else
    		WeekDataObject.HasData=false;
    	
    }
	
}
