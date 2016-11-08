package com.common.dbhelp;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


 

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Layout;
import android.text.TextPaint;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WndAssistant 
{
	public static final int COLOR2_Old = Color.parseColor("#009a44");//29a1fb
	public static final int COLOR2 = Color.parseColor("#ffffff");//29a1fb
	public static final int HighCOLOR= Color.parseColor("#009a44");//��ѹ��ʾֵ
	public static final int NormalCOLOR= Color.parseColor("#019858");//��Ѫѹ��ʾ��ɫֵ
	public static final int LowerCOLOR= Color.parseColor("#FFD306");//��Ѫѹ��ʾ��ɫֵ
	
	
	private static Boolean HideParent_Global=true;
	private static Activity Parent_Global=null;
	
 	
	public static double[]   Max_XValues;					//�����洢����-����ѹ
	public static double[]   Min_XValues;					//�����洢����-����ѹ
	public static String[]   BeatCount;						//�������
	public static String[]   TimeStr;						//����ʱ��
	
	
	
	public static int       DataCount=0;
	
	public static double[]   Max_YValues;					//�����洢����-����ѹ
	public static double[]   Min_YValues;					//�����洢����-����ѹ
	
	public static Boolean    HasData=false;						//�Ƿ���Ѫѹ��ݸ���
	
	public static String GetDate="";
	
	public static Boolean LoginState=false;
	
	public static String    LoginUserName="";
	
 
	

	
	
	public static void SetWindowNoTitle(Activity ThisWnd)
	{
		
		ThisWnd.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        	    WindowManager.LayoutParams.FLAG_FULLSCREEN); 
         
		ThisWnd.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		ThisWnd.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   
	    
	}
	
	public static void MakeTelephone(String PhoneNo,Activity MainWnd)
	{
		Intent CallIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+PhoneNo.trim()));
		MainWnd.startActivity(CallIntent);		
	}
	
	public static void StartToast(String Content, int TTime,Activity MainWnd) 
  	{
		Toast Toa = Toast.makeText(MainWnd.getApplicationContext(),
				Content, TTime);
		Toa.show();
	}
	
	// ����bmp����
	public static Bitmap GetBmpByName(String BmpName,Activity Parent) 
	{
			Bitmap Ico = null;
			ByteArrayOutputStream bos = null;
			String AppIco = "" + BmpName;
			try 
			{
				InputStream InputIco = Parent.getAssets().open(AppIco);
				bos = new ByteArrayOutputStream();
				BufferedInputStream bis = new BufferedInputStream(InputIco);

				Ico = BitmapFactory.decodeStream(bis);
				bis.close();
				InputIco.close();

			} 
			catch (IOException e) 
			{
				// Should never happen!
				throw new RuntimeException(e);
			}
			return Ico;
	}
	
	public static void showHintDialog(Activity WorkWnd,String HintCaption,String HintContent,Boolean HideParent)
	{
    	String updateMsg=HintContent;
		AlertDialog.Builder builder = new Builder(WorkWnd);
		builder.setTitle(HintCaption);
		builder.setMessage(updateMsg);
		Parent_Global=WorkWnd;
		HideParent_Global=HideParent;
		
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() 
		{			
		
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();	
				if(HideParent_Global)
				{
					if(Parent_Global!=null)
					{
						Parent_Global.finish();
					}
					
				}
			}
		});
		
		AlertDialog noticeDialog = builder.create();
		noticeDialog.setCancelable(false);
		noticeDialog.show();
	}
	
	//��ȡ���������ʱ�䴮
	public static String GetTodayString()
	{
		String TotalStr="";
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8")); 
    	SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	
    	dspFmt.setTimeZone(TimeZone.getDefault());  
    	TotalStr = dspFmt.format(System.currentTimeMillis());  
		
		return TotalStr;
		
	}
	
	//�ж��ֻ�����Ƿ�Ϸ�
	public static boolean isMobileNO(String mobiles) 
	{
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        //System.out.println(m.matches() + "---");
        return m.matches();
    }
	//�ж����֤���Ƿ�Ϸ�
	public static boolean personIdValidation(String text) 
	{
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
  }
	
	public static String GetNowTimeString()
	{
		
		String convertTime;  
    	
    	
    	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8")); 
    	SimpleDateFormat dspFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	
    	dspFmt.setTimeZone(TimeZone.getDefault());  
        convertTime = dspFmt.format(System.currentTimeMillis());  
        return convertTime;
	}
	
	public static String GetDateStr(Calendar Thatday)
	{
		String RetString="";
		int Year=0,Month=0,Day=0;
		if(Thatday!=null)
    	{
    		
    		
    		Year = Thatday.get(Calendar.YEAR);
            Month = Thatday.get(Calendar.MONTH)+1;
            Day = Thatday.get(Calendar.DAY_OF_MONTH);
    	}
		RetString=String.format("%04d-%02d-%02d", Year,Month,Day);
		
		return RetString;
		
	}
	public static double GetDivResult(int DivTotal,int ByDiv)
	{
		  BigDecimal bd = new BigDecimal(DivTotal);  
		  // ������  
		  BigDecimal bd2 = new BigDecimal(ByDiv);  
		  // ���г�����,����200λС��,ĩλʹ���������뷽ʽ,���ؽ��  
		  BigDecimal result = bd.divide(bd2, 200, BigDecimal.ROUND_HALF_DOWN);  
		   
		  //System.out.println("double          : " + result.doubleValue());  
		  return result.doubleValue();
		
	}
	
	//��������ÿ��ҳ��ı������֣�ʹ֮���ж���
	public static void LayoutCaptionText(TextView CaptionObj,String StrContent,Activity MainWnd,int CaptionContain_Height)
	{
		
		int   Text_Width=0,Text_Height=0;
		int   ScreenWidth=0,ScreenHeight=0;
		int   LeftMargin=0,TopMargin=0;
		
    	
    	DensityUtil ScreenCtrl=new DensityUtil(MainWnd);
    	ScreenWidth=ScreenCtrl.ScreenWidth;
		ScreenHeight=ScreenCtrl.ScreenHeight;
		
		
		if(CaptionContain_Height<=0)
			CaptionContain_Height=120;
		
		if(CaptionObj!=null)
		{
			
			Paint pFont = new Paint();
			Rect rect = new Rect();
			pFont.setTextSize(22);
			pFont.getTextBounds(StrContent, 0, StrContent.length(), rect);
			
			CaptionObj.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
			
			{
				Text_Width=CaptionObj.getMeasuredWidth();
				
				Text_Height=CaptionObj.getMeasuredHeight();
				
				
				LeftMargin=(ScreenWidth-Text_Width)/2;
				
				TopMargin=(CaptionContain_Height-Text_Height)/2;
				
				RelativeLayout.LayoutParams	TopParam=new RelativeLayout.LayoutParams(Text_Width,Text_Height);
	    		
	    		
	    		
	    		 
	    		if(TopParam!=null)
	    		{
	    			TopParam.leftMargin=LeftMargin;
	    			TopParam.topMargin=TopMargin;
	    			CaptionObj.setLayoutParams(TopParam);	    			
	    		}
				
			}
			
		}
		
		
	}
	
	
	public static Boolean GetWeekRange(String[] WeekCache)
	{
		Boolean 	Ret=true;
		int 		mYear,mMonth,mDay;
		
		String		mWay;
		String      StartTime,EndTime="";
		int    		WeekFlag=0;
		
		final Calendar Today = Calendar.getInstance(); 
		 
		Today.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
				 
		Calendar StartDate= Calendar.getInstance(); ;
		Calendar EndDate= Calendar.getInstance(); ;
		
		
		
		 mWay = String.valueOf(Today.get(Calendar.DAY_OF_WEEK)); 
		 
		 WeekFlag=Integer.parseInt(mWay);
				
		 switch(WeekFlag)
		 {
		 	case 1:			//������
		 		StartDate.add(Calendar.DATE, -6);
		 		
		 		break;
		 	case 2:
		 		EndDate.add(Calendar.DATE, 6);
		 		break;
		 	case 3:				//���ڶ�
		 		StartDate.add(Calendar.DATE, -1);
		 		EndDate.add(Calendar.DATE, 5);
		 		break;	
		 	case 4:
		 		StartDate.add(Calendar.DATE, -2);
		 		EndDate.add(Calendar.DATE, 4);
		 		break;
		 	case 5:
		 		StartDate.add(Calendar.DATE, -3);
		 		EndDate.add(Calendar.DATE, 3);
		 		break;	
		 		
		 	case 6:
		 		StartDate.add(Calendar.DATE, -4);
		 		EndDate.add(Calendar.DATE, 2);
		 		break;
		 	case 7:			//������
		 		StartDate.add(Calendar.DATE, -3);
		 		EndDate.add(Calendar.DATE, 1);
		 		break;
		 }
				 
		 StartTime=	 GetDateStr(StartDate);
		        
		 EndTime=GetDateStr(EndDate);
		 WeekCache[0]=StartTime;
		 WeekCache[1]=EndTime;
		 return Ret;
		
		
	}
	
	public static int GetDeviceWidthOrHdeight(Activity MainWnd,int ParaType)
	{
		DensityUtil ScreenCtrl;
		if(MainWnd==null)
			ScreenCtrl=new DensityUtil(Parent_Global);
		else
			ScreenCtrl=new DensityUtil(MainWnd);
			
    	int ScreenWidth=ScreenCtrl.ScreenWidth;
		int ScreenHeight=ScreenCtrl.ScreenHeight;
		
		if(ParaType==1)
		{
			
			return ScreenWidth;
		}
		else			
			return ScreenHeight;
	}
	
	//��ȡ��ǰ�ĵ�¼״̬
	public static Boolean GetLoginState()
	{
		return LoginState;
	}
	
}
