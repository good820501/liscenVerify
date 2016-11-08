package com.example.homenet;

 


 

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;


import com.common.dbhelp.DensityUtil;
import com.common.dbhelp.WndAssistant;

import android.R.string;
import android.os.Bundle;
import android.os.Handler;

import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	private static String SEND_ACTIOIN = "SMS_SEND_ACTION";
	private TimerInfo mReceiver01;
	private static int TimeCount;
	public  boolean ShowSysSet=false;
	 String ServiceUrl="http://192.168.220.70:8080/ReceiveMobleApp/ReceivePort?wsdl";
	 String DevNo="";
	Handler handler=new Handler();
	Runnable runnable=new Runnable() 
		{
		     
		    public void run() 
		    {
		        // TODO Auto-generated method stub
		        //Ҫ��������
		    	TimeCount++;
				 if(TimeCount>8)
				 {
					 	 Intent intent=new Intent(MainActivity.this,SalesActivity.class);
							 
							 if(ShowSysSet)
								 intent.putExtra("ShowSet", "1");
							 else
								 intent.putExtra("ShowSet", "0");
							 
							 startActivity(intent);
							 handler.removeCallbacks(runnable);
							 finish();
				        
					 /*DispMainActivity();*/
				 }
				 else
					 handler.postDelayed(runnable, 100);
		    }
		};
	private void InitHandle()
	{
		 
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        TimeCount=0;
        WndAssistant.SetWindowNoTitle(this);
        setContentView(R.layout.activity_main);
        InitWindow();
         
        DispProgressBar();
        InitDataBase();		//��ʼ����ݿ�
    }
    
    //��ݷֱ��ʴ�С������沼��
    private void InitWindow()
    {
    	int ScreenWidth=0,ScreenHeight=0;
    	
    	DensityUtil ScreenCtrl=new DensityUtil(this);
    	
    	if(ScreenCtrl!=null)
    	{
    		ScreenWidth=ScreenCtrl.ScreenWidth;
    		ScreenHeight=ScreenCtrl.ScreenHeight;
    		
    		if(ScreenWidth!=0 && ScreenHeight!=0)
    		{    			
				MakeControls(ScreenWidth,ScreenHeight);
    		}
    		
    		
    	}
    	
    }

    private void MakeControls(int ScreenWidth,int ScreenHeight)
    {
    	MakeLogo(ScreenWidth,ScreenHeight);
    	MakeCorpLogo(ScreenWidth,ScreenHeight);
    	
    }
    private void MakeLogo(int ScreenWidth,int ScreenHeight)
    {


    }

    private void MakeCorpLogo(int ScreenWidth,int ScreenHeight)
    {
    	
    	
    	
    	int   CommonTop;
    	int   ImgHeight=120;
    	int   ImgWidth;
    	
    	int   TextHeight=120;
    	int   TextWidth;
    	
    	double WidthDHeight=0;
    	
    	RelativeLayout CorpNameContainer; 
    	
    	
    	
    	WidthDHeight=WndAssistant.GetDivResult(415,30);
    	CommonTop=(int)(ScreenHeight*0.9);
    	
    	ImgWidth=(int)(ScreenWidth*0.618);
    	
    	if(WidthDHeight!=0)
    	{
    		
    		ImgHeight=(int)(ImgWidth/WidthDHeight);
    	}
    	
    	
    	
    	CorpNameContainer=(RelativeLayout)this.findViewById(R.id.corpname);
    	if(CorpNameContainer!=null)
    	{
    		RelativeLayout.LayoutParams LogoParam;
    		LogoParam=new RelativeLayout.LayoutParams(ScreenWidth,ImgHeight);
    		
    		LogoParam.leftMargin=0;
    		LogoParam.topMargin=CommonTop;
    		if(LogoParam!=null)
    		{
    			CorpNameContainer.setLayoutParams(LogoParam);    			
    		}
    		
    	}
    	
    	ImageView CorpLogo=(ImageView )findViewById(R.id.imageView4);
    	if(CorpLogo!=null)
    	{
    		RelativeLayout.LayoutParams Corp_LogoParam;
    		Corp_LogoParam=new RelativeLayout.LayoutParams(ImgWidth,ImgHeight);
    		
    		Corp_LogoParam.leftMargin=(ScreenWidth-ImgWidth)/2;
    		Corp_LogoParam.topMargin=0;
    		if(Corp_LogoParam!=null)
    		{
    			CorpLogo.setLayoutParams(Corp_LogoParam);    			
    		}    		
    	}
    	
    }
    

  	public  void InitDataBase()
  	{
  		String FileName="/data/data/"+getPackageName()+"/databases/history";
  		File file=new File(FileName);
  		String SDevNo;
  		//SDevNo=GetThisPhone();
  		SDevNo="";
  		
  		if(!file.exists())
  		{
  			try
  			{
  				createDatabase();
  				StartToast("拷贝数据文件成功",Toast.LENGTH_LONG);
  			}
  			catch(IOException exp)
  			{
  				StartToast("拷贝数据文件失败:"+exp.getMessage(),Toast.LENGTH_LONG);
  				
  			} 			
  			
  		}
  		
  		
  	}
  	
  	
  	
  	private void showDialog(String mess)
    {
    	try
    	{
    		/*AlertDialog   ShowInfo;
    		
    		 ShowInfo= new AlertDialog.Builder(SalesActivity.this).create();
    		 
    		 if(ShowInfo!=null)
    		 {
    			 
    			 
    			 
    		 }*/
	      new AlertDialog.Builder(MainActivity.this).setTitle("�����ٱ�")
	       .setMessage(mess)
	       .setNegativeButton("ȷ��",new DialogInterface.OnClickListener()
	       {
	         public void onClick(DialogInterface dialog, int which)
	         {          
	         }
	       })
	       .show();
    	}
    	catch(Exception Exp)
    	{
    		
    		
    		String ErrorInfo=Exp.getMessage();
    	}
    }
  	
  	@SuppressWarnings("unused")
  	public String GetThisPhone()
  	{
  		
  		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

			 

			//��ȡ�ֻ����

			String phoneId = tm.getLine1Number();
			String imei = tm.getSimSerialNumber();
			return phoneId;
  		
  		
  	}
  	
	 
  	
  	
  	public void StartToast(String Content, int TTime) 
  	{
		Toast Toa = Toast.makeText(getApplicationContext(),
				Content, TTime);
		Toa.show();
	}
  	public void createDatabase() throws IOException
  	{
  		String FileName="/data/data/"+getPackageName()+"/databases/history";
  		String DbDir="/data/data/"+getPackageName()+"/databases/";
  		
  		File dir = new File(DbDir);  
  		if (!dir.exists())   
            dir.mkdir();   
  	    InputStream assetsDB = getAssets().open("history.s3db");
  	    OutputStream dbOut = new FileOutputStream(FileName);
  	 
  	    byte[] buffer = new byte[1024];
  	    int length;
  	    while ((length = assetsDB.read(buffer))>0)
  	    {
  	      dbOut.write(buffer, 0, length);
  	    }
  	 
  	    dbOut.flush();
  	    dbOut.close();
  	    assetsDB.close();
  	}

    private void DispProgressBar()
    {

    	if(handler!=null)
    	{
    		handler.post(runnable);
    	}
    	
    }
    @Override
    protected void onResume() 
    {
       // TODO Auto-generated method stub
    	/*
       IntentFilter mFilter01;
       mFilter01 = new IntentFilter(SEND_ACTIOIN);
       mReceiver01 = new TimerInfo();
       registerReceiver(mReceiver01, mFilter01);*/       
       super.onResume();
    }
    @Override
    protected void onPause() 
    {
       // TODO Auto-generated method stub
       //unregisterReceiver(mReceiver01);
       super.onPause();
    }
    public class TimerInfo extends BroadcastReceiver 
    {
    	private static final int PERIOD = 100; // 1 minutes
    	@Override
    	public void onReceive(Context arg0, Intent arg1) 
    	{
    		// TODO Auto-generated method stub
    		 Log.i("֪ͨ", "�յ��˹㲥");  
    		 //startActivity(arg1);
    		 if (arg1.getAction().equals(SEND_ACTIOIN))
    		 {
    			 TimeCount++;
    			 if(TimeCount>8)
    			 {
    				 
    				 Intent intent=new Intent(MainActivity.this,MainPage.class);
    				 startActivity(intent);
    				 finish();
    				 /*DispMainActivity();*/
    			 }
    			 else
    			 {
    				 scheduleAlarms(arg0);
    			 }
    		 }
    	}
    	
    	public  void scheduleAlarms(Context ctxt) 
    	 {
    	       AlarmManager mgr = (AlarmManager) ctxt.getSystemService(Context.ALARM_SERVICE);
    	       Intent i = new Intent(ctxt, TimerInfo.class);
    	       i.setAction(SEND_ACTIOIN);
    	       PendingIntent pi = PendingIntent.getBroadcast(ctxt, 0, i, 0);
    	       mgr.cancel(pi);
    	       mgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+PERIOD, pi);

    	 }
    	

    }
}
