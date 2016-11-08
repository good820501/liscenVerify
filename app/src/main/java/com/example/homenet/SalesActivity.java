package com.example.homenet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;


import com.aliyun.api.gateway.demo.Client;
import com.aliyun.api.gateway.demo.Request;
import com.aliyun.api.gateway.demo.constant.Constants;
import com.aliyun.api.gateway.demo.constant.ContentType;
import com.aliyun.api.gateway.demo.constant.HttpHeader;
import com.aliyun.api.gateway.demo.constant.HttpSchema;
import com.aliyun.api.gateway.demo.enums.Method;
import com.aliyun.api.gateway.demo.util.HttpUtil;
import com.aliyun.api.gateway.demo.util.MessageDigestUtil;
import com.chart.control.AutoResizeTextView;
import com.common.dbhelp.WebAssistant;
import com.common.dbhelp.WndAssistant;
import com.common.dbhelp.dbHelper;


import android.app.ExpandableListActivity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
 
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.album.util.AlbumActivity; 
import com.album.util.MyApplication;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import org.json.JSONException;
import org.json.JSONObject;


public class SalesActivity extends ActivityGroup implements android.view.View.OnClickListener, OnItemClickListener
{
	
	private ArrayList<String> dataList = new ArrayList<String>();


	//APP KEY
	private final static String APP_KEY = "23445228";
	// APP密钥
	private final static String APP_SECRET = "16a5ae365fc02eb180c2cdaa3cacff51";
	//API域名
	private final static String HOST = "dm-53.data.aliyun.com";



	
	String DevNo="13236005878";
	
	private View vNewsMain = null;
	
	private RelativeLayout rlNewsMain = null;
	private LayoutParams params = null;
	
	Boolean SendAllSuccess;
	
	private boolean InfoType_New;//
	
	public String HintStr="";
	
	public  boolean DeleteRet=false;
	
	private int CMD_DELETEMUSIC=1;
	private int CMD_DELETEVIDEO=2;
	private int CMD_CLEARINPUT=3;
	
	
	private boolean IsUploading;
	
	private int flag=1;
	private int VoiceFlag=0;
	private final int MENU_GENDER_MALE = 0; 
    private final int MENU_GENDER_FEMALE = 1;
    private final int MENU_HOBBY1 = 2; 
    private final int MENU_HOBBY2 = 3;
    private final int MENU_HOBBY3 = 4; 
    
    private final int MENU_OK = 5; 
    
    private final int MENU_GENDER = 6;
    private final int MENU_HOBBY = 7; 
    
    private final int GENDER_GROUP = 0;
    private final int HOBBY_GROUP = 1; 
    private final int MAIN_GROUP = 2; 
	
    private Button bntRecord;
	
	
	List<Map<String, Object>> mData;
	
	List<String> PicList;
	
	private ListView CommuList;
	
	ImageView Appendix;
	 String uploadFile ;
	TextView DetailCaption;
	ImageButton Return;
	ImageButton SelImg;
	
	ImageButton SaleBtn;
	ImageButton ClearBtn;
	ImageButton RecordBtn;
	
	ImageButton VideoBtn;
	ImageButton VoiceRec;
	ImageButton GotoHistory;
	//EditText    SendContent;
	 
	
	
	ImageButton DelMusic;
	
	ImageButton DelVideo;
	
	ImageButton InputText;
	ImageButton OpenSpnner;
	ImageButton SysSet;
	
	private ProgressDialog progressDialog;


	TextView tcount;
	TextView succ_rate;

	TextView time_rate;
	
	private String actionUrl = "http://qxw1001970778.my3w.com/upload.aspx";
	private static final int TIME_OUT = 10*1000;   //��ʱʱ��
	ImageButton SearchBtn;

	private Cursor myCursor;

	
	private final String IMAGE_TYPE = "image/*";
	private final String VOICE_TYPE = "audio/amr";
	private final int IMAGE_CODE = 0; 	//ͼƬѡ��
	private final int GPS_SET=1;
	
	private final int GETVOICE=2;		//¼���ļ�ѡȡ
	
	private final int REQUEST_CODE_TAKE_PICTURE=3;		//����
	
	private final int REQUEST_CODE_SELECT_VIDEO=4;		//ѡ����Ƶ
	
	private final int REQUEST_CODE_REAL_VIDEO=5;		//ѡ����Ƶ
	public final int  GetHisID=6; //���ڻ�ȡѡ������ʷ��ݵķ���ֵ
	
	private final int NETSATATE_SET=7;//�趨����״̬
	
	
	
    private String SelectedImgPath="";			//ѡ����ͼƬ��·��
	
    private String VoiceFilePath;		//¼���ļ���·��
    private String VideoFIlePath;
    private String UnifiedNo_Global;    //ѡ������ʧ�ܵ���Դ��ʾ��
    
    String longtitude_Str="";
    String latitude_Str="";
    
    
    
    private View rcChat_popup;
	private Handler mHandler = new Handler();
	private long startVoiceT, endVoiceT;
	private String voiceName;
	private SoundMeter mSensor;
	private static final int POLL_INTERVAL = 300;
	private ImageView  volume;
	private LinearLayout voice_rcd_hint_rcding,sound_file,video_file;
	private TextView txtName;
	private ImageButton use_bnt;
	public static boolean isUsed=false;
	private Chronometer timedown;

	private SharedPreferences sp;
	private long timeTotalInS = 0;
	private long timeLeftInS = 0;
	private MediaPlayer player ;

	RelativeLayout  BottomOhj;

	dbHelper db;

	Handler handler;


	private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();

	static {
		CUSTOM_HEADERS_TO_SIGN_PREFIX.add("Custom");
	}


	
	private MyApplication ThisApp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());  
         StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()  
        .detectLeakedSqlLiteObjects()  
        .detectLeakedClosableObjects()  
        .penaltyLog()  
        .penaltyDeath()  
        .build()); 
    	
        super.onCreate(savedInstanceState);
        WndAssistant.SetWindowNoTitle(this);
        setContentView(R.layout.activity_sales);
        InfoType_New=true;
        ThisApp=(MyApplication)getApplicationContext();
        

        InitControls();


		Handler.Callback handleMessage = new Handler.Callback()
		{

			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub

				if(msg.arg1 == 1)//启动录像
				{
					try {
						if(CommuList!=null)
							InitCommList(CommuList);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else //停止录像
				{

				}
				return false;
			}
		};
		handler = new Handler(handleMessage);


    }
    //

    

    private void GetImgExtInfo(String ImgPath)
    {
    	 Bitmap bitmap=BitmapFactory.decodeFile(ImgPath);
         try 
         {
             //img.setImageBitmap(bitmap);
         } 
         catch (Exception e) {
             e.printStackTrace();
         }

         
         try 
         {

             ExifInterface exifInterface=new ExifInterface(ImgPath);
             String smodel=exifInterface.getAttribute(ExifInterface.TAG_MODEL);
             String width=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
             String height=exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
             Toast.makeText(SalesActivity.this, smodel+"  "+width+"*"+height, Toast.LENGTH_LONG).show();
         } 
         catch (Exception e) 
         {
             e.printStackTrace();
         }
    	
    	
    }
    
    private void ShowInputD()
    {

    }
    


    


    private void StartUpload()
    {
    	

        progressDialog = new ProgressDialog(SalesActivity.this);
        progressDialog.setTitle("图片识别");
        progressDialog.setMessage("正在识别，请稍后......");
         progressDialog.setCancelable(false);
         progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) 
            { 

            	if(!IsUploading)
            	{try
        		{
            		Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
        		}
                catch (Exception e)
                {
                 e.printStackTrace();   
                }
            		dialog.dismiss(); 
            	}
            	else
            	{
            		try
            		{
	                    Field field = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
	                    field.setAccessible(true);
	                    field.set(dialog, false);
            		}
	                catch (Exception e)
	                {
	                 e.printStackTrace();   
	                }
            		
            	}
            	
            } 
        }); 
        //progressDialog.show();
        new UploadImageTask().execute(0);
    }
    
    //�������״̬
    private void CheckNetState()
    {
    	boolean Is3G=false;
    	
    	Is3G=WebAssistant.CheckNetState(this);
    	
    	if(!Is3G)//��ʾ��������	
    	{
    		MakeSureDlg("当前是3G网络",NETSATATE_SET);
    		
    		 
    		
    		
    	}
    }
    
    private int uploadFile()
    {
      String end = "\r\n";
      String twoHyphens = "--";
      String boundary = "*****";
      int    RetNum=1;
     
      SendAllSuccess=true;
      
      boolean SendPic=false;
      boolean SendTxt=false;
      
      boolean SendAll_Success=false;

		db=new dbHelper(this);
      
      if(PicList!=null && PicList.size()>0)
      {
    	  int Succ_Count=0,ErrCount=0;
    	  for(int i=0;i<PicList.size();i++)
    	  {
    		  
    		  //SendPic=WSUpload(PicList.get(i),uuid,DevNo);


			  try{
				  SendPic=UploadACommon(PicList.get(i));
				  if(SendPic)
					  Succ_Count++;
				  else
				  {
					  ErrCount++;
					  SendAllSuccess=false;
				  }

			  }catch (Exception exp){


				  String error=exp.getMessage();
				  if(error!=null)
				  {


				  }



			  }



			  try {
				  Thread.sleep(1500);
			  } catch (InterruptedException e) {
				  e.printStackTrace();
			  }
		  }
    	   HintStr+="成功："+String.format("%d",Succ_Count)+"，失败："+String.format("%d",ErrCount)+";";
      }
      else
      {
    	  HintStr+="请选择图片";
    	  
      }


		if(db!=null){

			db.close();

		}

		Message msg = handler.obtainMessage();
		//将进度值作为消息的参数包装进去，进度自加1
		msg.arg1 = 1;
		//将消息发送给主线程的Handler
		handler.sendMessage(msg);


      return RetNum;
      
    }

	@Override
	protected void onDestroy(){
		if(db!=null)
			db.close();
		super.onDestroy();
	}








	/* ��ʾDialog��method */
    private void showDialog(String mess)
    {
    	try
    	{
    		/*AlertDialog   ShowInfo;
    		
    		 ShowInfo= new AlertDialog.Builder(SalesActivity.this).create();
    		 
    		 if(ShowInfo!=null)
    		 {
    			 
    			 
    			 
    		 }*/
	      new AlertDialog.Builder(SalesActivity.this).setTitle("OCR识别")
	       .setMessage(mess)
	       .setNegativeButton("确定",new DialogInterface.OnClickListener()
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_sales, menu);
        return true;
    }

    
    private void InitControls()
    {
    	

    	PicList=new ArrayList<String>();
    	
    	
    	
    	ColorDrawable drawable = new ColorDrawable(Color.WHITE);
  	  
		this.getWindow().setBackgroundDrawable(drawable);
        CommuList=(ListView)findViewById(R.id.listView1);
        if(CommuList!=null)
        {
        	CommuList.setOnItemClickListener(this);
        }
    	
         Appendix=(ImageView)findViewById(R.id.imgappendex);
      

          
          
          DelMusic=(ImageButton)findViewById(R.id.imageButton2);
          
          if(DelMusic!=null)
          {
        	  DelMusic.setOnClickListener(this);
        	  
          }
          

          DelVideo=(ImageButton)findViewById(R.id.imageButton12);
          
          if(DelVideo!=null)
          {
        	  DelVideo.setOnClickListener(this);
        	  
          }
          

          
          SaleBtn=(ImageButton)findViewById(R.id.imageButton4);
          if(SaleBtn!=null)
          {   		
        	  SaleBtn.setOnClickListener(this);
          }
           
          SaleBtn.setOnTouchListener(new View.OnTouchListener()
          {            
        	    public boolean onTouch(View v, MotionEvent event) {               
        	            if(event.getAction() == MotionEvent.ACTION_DOWN)
        	            {       
        	               //�������ð���ʱ�ı���ͼƬ  
        	               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.send_focus));                              
        	            }
        	            else if(event.getAction() == MotionEvent.ACTION_UP)
        	            {       
        	                //���޸�Ϊ̧��ʱ����ͼƬ  
        	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.send_normal));     
        	            }  
        	            return false;       
        	    }       
        	});  
          ClearBtn=(ImageButton)findViewById(R.id.imageButton5);
          if(ClearBtn!=null)
          {   		
        	  ClearBtn.setOnClickListener(this);
          }
          ClearBtn.setOnTouchListener(new View.OnTouchListener()
          {            
        	    public boolean onTouch(View v, MotionEvent event) {               
        	            if(event.getAction() == MotionEvent.ACTION_DOWN)
        	            {       
        	               //�������ð���ʱ�ı���ͼƬ  
        	               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.clear_focus));                              
        	            }
        	            else if(event.getAction() == MotionEvent.ACTION_UP)
        	            {       
        	                //���޸�Ϊ̧��ʱ����ͼƬ  
        	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.clear_normal));     
        	            }  
        	            return false;       
        	    }       
        	});  
          

          
          RecordBtn=(ImageButton)findViewById(R.id.imgBtnSel1);
          
          
          sound_file=(LinearLayout)this.findViewById(R.id.sound_file);
          video_file=(LinearLayout)this.findViewById(R.id.video_file);
          
          
         
    	
    	SelImg=(ImageButton)findViewById(R.id.imgBtnSel);
    	if(SelImg!=null)
    	{   		
    		SelImg.setOnClickListener(this);
    	}
    	registerForContextMenu(SelImg); 
    	
    	SelImg.setOnTouchListener(new View.OnTouchListener()
        {            
      	    public boolean onTouch(View v, MotionEvent event) {               
      	            if(event.getAction() == MotionEvent.ACTION_DOWN)
      	            {       
      	               //�������ð���ʱ�ı���ͼƬ  
      	               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.p_press));                              
      	            }
      	            else if(event.getAction() == MotionEvent.ACTION_UP)
      	            {       
      	                //���޸�Ϊ̧��ʱ����ͼƬ  
      	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.p_normal));     
      	            }  
      	            return false;       
      	    }       
      	});  
        
    	
    	
    	//HosDescripe=(TextView)findViewById(R.id.textView2);

    	DetailCaption=(TextView)findViewById(R.id.textView1);
    	
    	if(DetailCaption!=null)
    	{
    		DetailCaption.setTextColor(WndAssistant.COLOR2);
    		DetailCaption.setTextSize(20);     
    		WndAssistant.LayoutCaptionText(DetailCaption,"�����ٱ�", this, 100);
    		
    	}
    	
    	
    	rcChat_popup = this.findViewById(R.id.rcChat_popup);
		volume = (ImageView) this.findViewById(R.id.volume);
		
		voice_rcd_hint_rcding = (LinearLayout)this.findViewById(R.id.voice_rcd_hint_rcding);
		//sound_file=(LinearLayout)this.findViewById(R.id.sound_file);
		//txtName=(TextView)findViewById(R.id.show_sound);
		timedown=(Chronometer)findViewById(R.id.timedown);
		//use_bnt=(ImageButton)findViewById(R.id.used_bnt);
		voiceName="MySound.mp3";
		mSensor = new SoundMeter();
    	
		
		LoadPicGalaxy();
		
		BottomOhj=(RelativeLayout)this.findViewById(R.id.bottom);
		/*if(BottomOhj!=null)
		{
			BottomOhj.setLongClickable(true);
			BottomOhj.setOnLongClickListener(new OnLongClickListener()
			{

		        @Override
		        public boolean onLongClick(View v) 
		        {
		        	ShowPasswordInput();
		            //ADD HERE ABOUT CUT COPY PASTE  
		            // TODO Auto-generated method stub
		            return false;
		        }
		    });
			
			
			
		}
		*/

		tcount=(TextView)this.findViewById(R.id.tcount);
		succ_rate=(TextView)this.findViewById(R.id.succ_rate);
		time_rate=(TextView)this.findViewById(R.id.time_rate);

		CommuList=(ListView)findViewById(R.id.listView);
		if(CommuList!=null)
			InitCommList(CommuList);







    }
    

    private void LoadPicGalaxy()
    {
    	rlNewsMain = (RelativeLayout) findViewById(R.id.addition);
    	if(PicList!=null && PicList.size()>0)
    	{
    		/*
    		if(Appendix!=null)
    			Appendix.setVisibility(View.INVISIBLE);
    		*/
	    	Intent intent = new Intent(SalesActivity.this, com.chart.control.TopicNews.class);
	    	intent.putStringArrayListExtra("PIC_LIST", (ArrayList<String>) PicList);
	    	
	    	getLocalActivityManager().removeAllActivities();
	    	
	    	vNewsMain = getLocalActivityManager().startActivity("TopicNews", intent).getDecorView();
	    	
	    	params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	    	
	    	
	    	rlNewsMain.removeAllViews();
	    	rlNewsMain.addView(vNewsMain, params);
    	}
    	else
    	{
    		rlNewsMain.removeAllViews();
    		
    		
			 
    		
    		if(Appendix!=null)
    		{
    			//params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    			
    			
    			Appendix.setImageResource(R.drawable.backshape);
    			rlNewsMain.addView(Appendix);
    			
    			//Appendix.setVisibility(View.VISIBLE);
    			 
    		}
    		
    	}
    	
    }
    private boolean MakeSureDlg(String HintString,final int CmdType)
    {
    	DeleteRet=false;
    	
    	 AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("系统提示").setNegativeButton("取消", null);
         builder.setMessage(HintString);
         builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

             public void onClick(DialogInterface dialog, int which) 
             {
            	 DeleteRet=true;
            	 
            	 if(CmdType==CMD_DELETEMUSIC)
            	 {
            		 VoiceFilePath="";
             		 sound_file.setVisibility(View.GONE);
            		 
            	 }
            	 else if(CmdType==CMD_DELETEVIDEO)
            	 {
            		 VideoFIlePath="";
             		 video_file.setVisibility(View.GONE);
            		 
            		 
            	 }
            	 else if(CmdType==CMD_CLEARINPUT)//�������
            	 {
            		 ClearAll();
            	     
            	 }
            	 else if(CmdType==NETSATATE_SET)//�������
            	 {
            		 
            		 startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS),NETSATATE_SET); 
            	 }
            	 
              }
         });
         builder.show();
    	
    	 return DeleteRet;
    	
    }
    
    private void ClearAll()
    {

		 

		 
		 SelectedImgPath="";
		 
		 if(PicList!=null)
			 PicList.clear();
		 if(dataList!=null)
			 dataList.clear();
		 LoadPicGalaxy();
		 
		 /*if(Appendix!=null)
			 Appendix.setImageBitmap(null);
    	*/
    	
    	
    }
    private void DisplayVoice(String path)
    {
    	final String FullName=path;
    	long FileLength;
    	if((path.equals(null)) || (path.length()<0))
    	{
    		
    		return;
    		
    	}
    	File file=new File(path);
		if(file.exists())
		{
			
			FileLength=file.length();
			if(FileLength<1500)
			{
				
				showDialog("¼��ʱ��̫�̣�");
				return;
			}
			/*
			if(VoiceFlag==0)
			{
				
				file.delete();
				sound_file.setVisibility(View.GONE);
				VoiceFlag=1;
				return;
			}*/
			sound_file.setBackgroundColor(Color.WHITE);
			sound_file.setVisibility(View.VISIBLE);
			String soundName=file.getName();
			//txtName.setText(soundName);
			//��������ļ���������
			sound_file.setOnTouchListener(new OnTouchListener() 
			{
				@Override
				public boolean onTouch(View view, MotionEvent event)
				{
					if(event.getAction()==MotionEvent.ACTION_DOWN)
					{
						sound_file.setBackgroundColor(getResources().getColor(R.color.bule));
						player= new MediaPlayer();
			              try 
			              {
							player.setDataSource(FullName); 
							player.prepare();
				            player.start();
			              } 
			              catch (IllegalArgumentException e) 
			              {
							// TODO Auto-generated catch block
							e.printStackTrace();
			              } 
			              catch (SecurityException e) 
			              {
							// TODO Auto-generated catch block
							e.printStackTrace();
			              } 
			              catch (IllegalStateException e) 
			              {
							// TODO Auto-generated catch block
							e.printStackTrace();
			              } 
			              catch (IOException e) 
			              {
							// TODO Auto-generated catch block
							e.printStackTrace();
			              }
					}
					else if(event.getAction()==MotionEvent.ACTION_UP)
					{
						sound_file.setBackgroundColor(getResources().getColor(R.color.white));
					}
					return true;
				}
			});

			
			}
		else
		{
			VoiceFlag=1;
		}
    }
    
    private void soundUse(String fileName){
		//�ж�sd�����Ƿ��������ļ����еĻ�����ʾ��Ʋ�����
    	
    	
    	
		final String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/hq_100/"+voiceName;
		VoiceFilePath=path;
		DisplayVoice(path);
	}
    
    //������Ƶ
   	public void PlayVideo(String videoFileName) throws IOException {
   			
   			// TODO Auto-generated method stub
   		        String videoPath; 

   			    Bundle bundle = new Bundle();
   				bundle.putString("VideoFileName", videoFileName);
   		}
   	

    private Runnable mSleepTask = new Runnable() {
		public void run() {
			stop();
		}
	};
	private Runnable mPollTask = new Runnable(){
		public void run() {
			double amp = mSensor.getAmplitude();
			updateDisplay(amp);
			mHandler.postDelayed(mPollTask, POLL_INTERVAL);
		}
	};
	private void start(String name) {
		mSensor.start(name);
		mHandler.postDelayed(mPollTask, POLL_INTERVAL);
	}
	
	private void stop() {
		mHandler.removeCallbacks(mSleepTask);
		mHandler.removeCallbacks(mPollTask);
		mSensor.stop();
		volume.setImageResource(R.drawable.amp1);
	}
	
    private void updateDisplay(double signalEMA) {
		
		switch ((int) signalEMA) {
		case 0:
		case 1:
			volume.setImageResource(R.drawable.amp1);
			break;
		case 2:
		case 3:
			volume.setImageResource(R.drawable.amp2);
			break;
		case 4:
		case 5:
			volume.setImageResource(R.drawable.amp3);
			break;
		case 6:
		case 7:
			volume.setImageResource(R.drawable.amp4);
			break;
		case 8:
		case 9:
			volume.setImageResource(R.drawable.amp5);
			break;
		case 10:
		case 11:
			volume.setImageResource(R.drawable.amp6);
			break;
		default:
			volume.setImageResource(R.drawable.amp7);
			break;
		}
	}
    
    /**
	 * ��ʼ����ʱ������ʱ����ͨ��widget.Chronometer��ʵ�ֵ�
	 * @param total һ��������
	 */
	private void initTimer(long total) {
		this.timeTotalInS = total;
		this.timeLeftInS = total;
		timedown.setOnChronometerTickListener(new OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer chronometer) {
				if (timeLeftInS <= 0) 
				{
					Toast.makeText(SalesActivity.this, "¼��ʱ�䵽", Toast.LENGTH_SHORT).show();
					timedown.stop();
					//¼��ֹͣ
					stop();
					rcChat_popup.setVisibility(View.GONE);
					timedown.setVisibility(View.GONE);
					VoiceRec.setImageDrawable(getResources().getDrawable(R.drawable.o_normal));  
  	                
					return;
				}
				timeLeftInS--;
				refreshTimeLeft();
			}
		});
	}
	private void refreshTimeLeft() {
		this.timedown.setText("¼��ʱ��ʣ�ࣺ" + timeLeftInS);
		//TODO ��ʽ���ַ�
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if(player!=null){
			player.stop();
			player.release();
		}
	}
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo)
    {
        // TODO Auto-generated method stub
    	
    	try
    	{
	        if(v.getId()==R.id.imgBtnSel)
	        {
	            menu.clear();
	            menu.setHeaderTitle("图片");
	            menu.add(0, 1, Menu.NONE, "拍摄");
	            menu.add(0, 2, Menu.NONE, "图库选择");
	            
	        }

    	}
        catch(Exception Exp)
        {
        	String ErrorInfo;
        	ErrorInfo=Exp.getMessage();
        
        }
        
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {  //��Ӧ�����Ĳ˵�
        // TODO Auto-generated method stub
       // t1.setText(item.getTitle());
    	String SelText=(String) item.getTitle();
    	 
    	if(SelText.equals("图库选择"))
    	{
    		OpenPictureSle();
    		
    		
    	}
    	else if(SelText.equals("拍摄"))
    	{
    		OpenCameraForPicture();
    	}

    	
    		 
        return true;
    }
    //
    private void GetRealVideo()
    {
    	String CatchFilePath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/hq_100/"+GetTimeStr(2)+".mp4";;
    	/*int durationLimit =3000;// getVideoCaptureDurationLimit(); //SystemProperties.getInt("ro.media.enc.lprof.duration", 60);

    	 Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

    	 intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
    	 startActivityForResult(intent, REQUEST_CODE_REAL_VIDEO);
    	*/
    	Intent intent = new Intent(SalesActivity.this,RecordActivity.class);
    	intent.putExtra("FiiePath", CatchFilePath);
    	startActivityForResult(intent, REQUEST_CODE_REAL_VIDEO);
    	
    }
    
    //ѡ��������Ƶ
    private void SelectVideo()
    {
    	
    	//��Ƶ

    	 Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT);

    	 innerIntent.setType("video/*"); //String VIDEO_UNSPECIFIED = "video/*";

    	 Intent wrapperIntent = Intent.createChooser(innerIntent, null);

    	  startActivityForResult(wrapperIntent, REQUEST_CODE_SELECT_VIDEO);
    	
    }
    
    
    private void OpenCameraForPicture()
    {
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //"android.media.action.IMAGE_CAPTURE";


    	intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
    	
    	startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
    	
    	
    	
    }

    public void onClick(View v) 
	{
		int BtnID = v.getId();
		int BtnCt = 0;
		String StrTemp;
		
		switch(BtnID)
		{
			

			case R.id.imageView3:
				//ShowRegisteWindow();
				break;
			case R.id.imgBtnSel:
				//OpenPictureSle();
				openContextMenu(SelImg);
				break;

			case R.id.imageButton4:
				//uploadFile();
				 if(PicList!=null && PicList.size()>0)
			      {
					 StartUpload();
			      }
			      else
			      {			    	  
			    	  this.showDialog("请选择图片或者拍摄图片");
			      }
				
				break;
			case R.id.imageButton5:
				ClearInput();
				break;	
				
			case R.id.imgBtnSel1:
				//OpenRecord();
				OpenAddMusic();
				break;
			case R.id.ImageButton3:
				OpenHistory();
				break;
			case R.id.imageButton2:
				DeleteMusic();
				break;	
			case R.id.imageButton12:
				DeleteVideo();
				break;
				


		}
	}

    private void ClearInput()
    {
    	MakeSureDlg("确定要清空输入内容吗？",CMD_CLEARINPUT);
    	
    }
    
    private void DeleteMusic()
    {
    	

    	
    	
    }
    private void DeleteVideo()
    {

    	
    }
    
    private void OpenHistory()
    {

    }
    
    

    private void OpenRecord()
    {
    	Intent getAlbum = new Intent(SalesActivity.this,RecorderTrack.class);
    	 
    	startActivityForResult(getAlbum, GETVOICE);
    	
    	
    	
    }
    
    

    private void GetLocationInfo()
    {
    	String serviceString=Context.LOCATION_SERVICE; 
        LocationManager locationManager=(LocationManager)getSystemService(serviceString); 
        String provider=LocationManager.GPS_PROVIDER; 
        Location location=locationManager.getLastKnownLocation(provider); 
        getLocationInfo(location); 
        locationManager.requestLocationUpdates(provider, 2000, 0, locationListener); 
    } 

    private void getLocationInfo(Location location)
    { 

    } 
    private final LocationListener locationListener =new LocationListener()
    {        
        @Override 
        public void onStatusChanged(String provider, int status, Bundle extras) { 
            // TODO Auto-generated method stub 
             
        }        
        @Override 
        public void onProviderEnabled(String provider) { 
            getLocationInfo(null); 
             
        }        
        @Override 
        public void onProviderDisabled(String provider) { 
            getLocationInfo(null);           
        }        
        @Override 
        public void onLocationChanged(Location location) 
        { 
            getLocationInfo(location); 
            //Toast.makeText(SalesActivity.this, "λ�øı���::::::::::::", 3000).show(); 
        } 
    }; 


    private void OpenPictureSle()
    {

    	Intent intent = new Intent(SalesActivity.this,
				AlbumActivity.class);
		Bundle bundle = new Bundle();
		// intent.putArrayListExtra("dataList", dataList);
		bundle.putStringArrayList("dataList",getIntentArrayList(dataList));
		intent.putExtras(bundle);
		startActivityForResult(intent, IMAGE_CODE);
    }
    private ArrayList<String> getIntentArrayList(ArrayList<String> dataList) {

		ArrayList<String> tDataList = new ArrayList<String>();

		for (String s : dataList) {
			if (!s.contains("default")) {
				tDataList.add(s);
			}
		}

		return tDataList;

	}
    private void OpenAddMusic()
    {
    	//�����Ƶ

    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

    	 intent.setType(VOICE_TYPE); //String AUDIO_AMR = "audio/amr";

    	 intent.setClassName("com.android.soundrecorder","com.android.soundrecorder.SoundRecorder");

    	  startActivityForResult(intent, GETVOICE);
    	
    	
    }
    
    /**
    * ���ر���ͼƬ
    * http://bbs.3gstdy.com
    * @param url
    * @return
    */
    public static Bitmap getLoacalBitmap(String url) 
    {
    	Bitmap ReturnImg=null;
         try 
         {
        	 BitmapFactory.Options opt = new BitmapFactory.Options();  
        	 opt.inPreferredConfig = Bitmap.Config.RGB_565;   
        	 opt.inPurgeable = true;  
        	 opt.inInputShareable = true;  

        	  
        	 
              FileInputStream fis = new FileInputStream(url);
              
              ReturnImg=BitmapFactory.decodeStream(fis,null,opt);
              fis.close();
              return ReturnImg;
         } 
         catch (FileNotFoundException e) 
         {
              e.printStackTrace();
              return null;
         } 
         catch (IOException e) 
         {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    


    
   private String GetTimeStr(int CmdType)
    {
    	String StrReturn;
    	
    	Time t=new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�  
    	t.setToNow(); // ȡ��ϵͳʱ�䡣  
    	int year = t.year;  
    	int month = t.month;  
    	int date = t.monthDay;  
    	int hour = t.hour; // 0-23  
    	int minute = t.minute;  
    	int second = t.second;  
    	 
    	long time=System.currentTimeMillis();	
    	if(CmdType==1)
    	
    		StrReturn=String.format("%04d%02d%02d%02d", year,month,date,hour);
    	else
    		StrReturn=String.format("%d",time);
    	return StrReturn;
    }
    

    public boolean UploadACommon(String ImgFileName)
    { 
    	String psDate="20150209";
    	String ExtName="";
    	String SecondBuffer;
    	byte[] FirBase64;
    	
    	psDate=GetTimeStr(1);
    	long FileLength=0;
    	boolean Ret=false;
        try
        {  

            String fileName = "abcd.jpg";
        	
            if(ImgFileName.equals(""))
            	return Ret;
        	File PicObj=new File(ImgFileName);
        	fileName=PicObj.getName();
        	
        	if(fileName.length()>0)
        	{
        		ExtName=fileName.substring(fileName.lastIndexOf('.')+1);
        		ExtName=ExtName.toLowerCase();
        		
        	}
        	
        	if(PicObj!=null)
        	{
        		FileLength=PicObj.length();
        		if(FileLength>15*1024*1000)
        		{
					showDialog("文件过大！");
					return false;
        		}
        	}
        	
            FileInputStream fis = new FileInputStream(ImgFileName);  
            ByteArrayOutputStream baos = new ByteArrayOutputStream();




             byte[] buffer = new byte[15*1024*1000];
             int count = 0;  
             int offset=0;
             boolean TransFirst=true;
             
            try
            {

            	
	            if((count = fis.read(buffer)) >= 0)
	            {  
	                baos.write(buffer, 0, count);
					fis.close();
	                //String NewBuffer=GZipcompressString(baos);
	                String uploadBuffer = Base64.encodeToString(baos.toByteArray(),Base64.NO_WRAP);

			        if(ExtName.equals("png"))
			        {
			            	uploadBuffer =uploadBuffer.replace('_', '/');
			            	uploadBuffer =uploadBuffer.replace('-', '+');
			         }
					Ret=WebService_Comm("uploadFile",fileName, uploadBuffer);
	            }
				else
				{
					fis.close();

				}
	            
            	
            }
            catch(IOException IEXP)
            {
            	String Error=IEXP.getMessage();
            	fis.close();
            	this.showDialog("传输输入失败:"+IEXP.getMessage());
            	
            }

        }
        catch(Exception e)
        {  
            e.printStackTrace();  
            return false;
        }


        return Ret;
    }  
    private boolean WebService_Comm(String methodName,String fileName, String imageBuffer)
    {   
    	String UploadType="0";


		long timeTicket_begin=0;
		long timeTicket_end=0;

		//请求URL
		String url = "/rest/160601/ocr/ocr_vehicle.json";
		//https://dm-53.data.aliyun.com/rest/160601/ocr/ocr_vehicle.json
		int success=0;
    	
    	String JsonStr="";
    	

		JsonStr="{\"inputs\":[";

		JsonStr+="{";
    	JsonStr+="\"image\":{ ";
    	JsonStr+="\"dataType\": 50,";
    	JsonStr+="\"dataValue\": \""+imageBuffer+"\"}";

    	
    	JsonStr+="}]}";

		String body = JsonStr;

		Map<String, String> headers = new HashMap<String, String>();
		//（必填）根据期望的Response内容类型设置
		headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
		//（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
		//headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
		//（POST/PUT请求必选）请求Body内容格式
		headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);

		Request request = new Request(Method.POST_STRING, HttpSchema.HTTPS + HOST + url, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
		request.setHeaders(headers);
		request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
		request.setStringBody(body);

		//调用服务端
		HttpResponse response = null;
		try {

			timeTicket_begin=new Date().getTime();
			response = Client.execute(request);
			//response= HttpUtil.getNewHttpClient().execute(request)

			timeTicket_end=new Date().getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(response!=null){

			try {
				String ret_str=readStreamAsStr(response.getEntity().getContent());
				if(!ret_str.equals("")){

					if(ret_str!=null){




						try {
							JSONObject ret=new JSONObject(ret_str);

							if(ret!=null){

								JSONArray outputs=ret.getJSONArray("outputs");


								if(outputs!=null && outputs.length()>0){

									JSONObject first=outputs.getJSONObject(0);

									JSONObject outputValue=first.getJSONObject("outputValue");
									if(outputValue!=null){

										String dataStr=outputValue.getString("dataValue");

										JSONObject dataValue=new JSONObject(dataStr);
										if(dataValue!=null){

											if(dataValue.getString("success").equals("true")){

												success=1;

											}
											else
											{

												success=0;

											}

											db.insertResult(fileName,success,(int)(timeTicket_end-timeTicket_begin),ret_str);




										}


									}


								}
								//db.insertResult

							}


						} catch (JSONException e) {
							e.printStackTrace();
						}




					}


				}


			} catch (IOException e) {
				e.printStackTrace();
			}




		}




        return true;  
    }


	public String  print(HttpResponse response) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(response.getStatusLine().getStatusCode()).append(Constants.LF);
		for (Header header : response.getAllHeaders()) {
			sb.append(MessageDigestUtil.iso88591ToUtf8(header.getValue())).append(Constants.LF);
		}
		sb.append(readStreamAsStr(response.getEntity().getContent())).append(Constants.LF);
		return (sb.toString());
	}

	/**
	 * 将流转换为字符串
	 *
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String readStreamAsStr(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		WritableByteChannel dest = Channels.newChannel(bos);
		ReadableByteChannel src = Channels.newChannel(is);
		ByteBuffer bb = ByteBuffer.allocate(4096);

		while (src.read(bb) != -1) {
			bb.flip();
			dest.write(bb);
			bb.clear();
		}
		src.close();
		dest.close();

		return new String(bos.toByteArray(), Constants.ENCODING);
	}



    public  String GZipcompressString(ByteArrayOutputStream rstBao) throws IOException 
    {
    	  ByteArrayOutputStream ZiprstBao = new ByteArrayOutputStream();
    	  GZIPOutputStream zos = new GZIPOutputStream(ZiprstBao);
    	  zos.write(rstBao.toByteArray() );
    	  //IOUtils.closeQuietly(zos);
    	  zos.close();
    	  byte[] bytes = ZiprstBao.toByteArray();
    	  return new String(Base64.encode(bytes,ZiprstBao.size()));
    }
    
    //ѹ��
    public  String compressString(String srcTxt) throws IOException 
    {
    	  ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
    	  GZIPOutputStream zos = new GZIPOutputStream(rstBao);
    	  zos.write(srcTxt.getBytes());
    	  //IOUtils.closeQuietly(zos);
    	  zos.close();
    	  byte[] bytes = rstBao.toByteArray();
    	  return new String(Base64.encode(bytes,rstBao.size()));
    }
    //��ѹ��
    public  String uncompressString(String zippedBase64Str) throws IOException 
    {
    	final int BUFFER_SIZE = 512;
    	  String result = null;
    	  byte[] bytes = Base64.decode(zippedBase64Str, Base64.DEFAULT);
    	  GZIPInputStream zi = null;
    	  StringBuilder string;
    	  try
    	  {
    	    zi = new GZIPInputStream(new ByteArrayInputStream(bytes));
    	      string = new StringBuilder();
    	    byte[] data = new byte[BUFFER_SIZE];
    	    int bytesRead;
    	    while ((bytesRead = zi.read(data)) != -1) 
    	    {
    	        string.append(new String(data, 0, bytesRead));
    	    }
    	  }
    	  finally 
    	  {
    	    //IOUtils.closeQuietly(zi);
    		  zi.close();
    	  }
    	    return string.toString();
    	}
    //����webService��ʽ�ϴ�ͼƬ

    private void CompressImg(String ImgFileName)
    {
    	BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
          
        //��ȡ���ͼƬ�Ŀ�͸�  
        Bitmap bitmap = BitmapFactory.decodeFile(ImgFileName,options);//��ʱ����bmΪ��  
        options.inJustDecodeBounds =false;  
        //�������ű�  
        int be = (int)(options.outHeight / (float)200);  
        if(be <= 0)  
             be =1;  
        options.inSampleSize =be;  
        //���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds��ΪfalseŶ  
        bitmap = BitmapFactory.decodeFile(ImgFileName,options);  
        int w = bitmap.getWidth();  
        int h=bitmap.getHeight();  
        System.out.println(w+" "+h);  
         
          
          
        //������sdCard  
        File file2= new File(ImgFileName);  
        try {  
         FileOutputStream out = new FileOutputStream(file2);  
         if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){  
             out.flush();  
             out.close();  
         }  
     } catch (Exception e) {  
         // TODO: handle exception  
     }  
    	
    	
    }

    

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	String ImgPath="";
    	Bitmap bm = null;
    	
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if (resultCode != RESULT_OK) 
    	{
          return;
    	}
      
      
   
      
            
      if (requestCode == IMAGE_CODE)
      {
          try 
          {
        	  
        	  
        	  Bundle bundle = data.getExtras();
				ArrayList<String> tDataList = (ArrayList<String>) bundle
						.getSerializable("dataList");
				if (tDataList != null)
				{
					/*
					if (tDataList.size() < 8) 
					{
						tDataList.add("camera_default");
					}*/
					dataList.clear();
					dataList.addAll(tDataList);
					
					
					if(PicList!=null && dataList!=null)
					{
					
					  for(int i=0;i<dataList.size();i++)
					  {
						  String StrTemp=dataList.get(i);
						 int Index= PicList.indexOf(StrTemp);
						 if(Index<0)
							 PicList.add(StrTemp);
						  
						  
					  }
						

					}
				}
				
				
				LoadPicGalaxy();
 
          }
          catch (Exception e) 
          {

             Log.e("Lostinai",e.toString()); 
             this.showDialog("ѡ��ͼƬ����"+e.getMessage());
          }
          if(Appendix!=null && ImgPath.length()>0)
	  		{
        	  
	  			
	  		}
      }
      else if(requestCode==GPS_SET)
      {

    	  
    	  
      }
    else if(requestCode==GETVOICE)
      {
    	 
    	Uri uri=data.getData();
        Cursor cursor = getContentResolver().query(uri, null,  
                            null, null, null);
                            

        cursor.moveToFirst();  
        VoiceFilePath = cursor.getString(1); 
        cursor.close();
      }
      else if(requestCode==REQUEST_CODE_TAKE_PICTURE)//����
      {
    	  Uri uri=null;//data.getData();
    	  if (resultCode == Activity.RESULT_OK) 
    	  { 
    		  
    		  
    		  
    		  
              Bitmap cameraBitmap = (Bitmap) data.getExtras().get("data"); 
             if(cameraBitmap!=null)
              {
            	 File file = new File("/sdcard/tmp/");
            	 if (!file.exists())
                  
                      file.mkdir();
            	 
                    	 
            	 
            	  SelectedImgPath="/sdcard/tmp/"+GetTimeStr(2)+".jpg";
            	  
            	  File file2= new File(SelectedImgPath);
            	  
                  try 
                  {  
                	  if(!file2.exists())
                		  file2.createNewFile();
                	  
                   FileOutputStream out = new FileOutputStream(file2);  
                  
                   if(cameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, out))
                   {  
                       out.flush();  
                       out.close();  
                   } 
                   /* out.flush();  
                   out.close(); */ 
                   
                  }
                  catch(Exception Exp)
                  {
                	  
                	  
                	  
                  }
            	   
                  if(PicList!=null)
                	  PicList.add(SelectedImgPath);
                  LoadPicGalaxy();
                  
              }
              
    	  }
      }
      else if(requestCode==REQUEST_CODE_SELECT_VIDEO)//ѡ����Ƶ
      {
    	  Uri uriVideo = data.getData();

    	  Cursor cursor = getContentResolver().query(uriVideo, null,  
                  null, null, null);
                  
			//��һ�еڶ��б���·��strRingPath
			cursor.moveToFirst();  
			VideoFIlePath = cursor.getString(1); 
			
   	   	  	
   	   	  cursor.close();

      }
       else if(requestCode==REQUEST_CODE_REAL_VIDEO)//������Ƶ
      {
    	   
    	   
    	   
    	   Bundle b=data.getExtras();  //dataΪB�лش���Intent
    	   VideoFIlePath=b.getString("VideoFileName");//str��Ϊ�ش���ֵ"Hello, this is B speaking"

    	  
      }
       else if(requestCode==GetHisID)	//��ȡ��ѡȡ����ʷ���֮�󣬼���ָ�������
       {
    	   String UnifiedID = data.getStringExtra("UnifiedNo");  
    	   
    	   if(UnifiedID!=null && UnifiedID.length()>0)
    	   {
    		   InfoType_New=false;
    		   VoiceFilePath="";
    		   VideoFIlePath="";
    		   UnifiedNo_Global=UnifiedID;

    		   
    	   }
    	   
       }
      else if(requestCode==NETSATATE_SET)
      {
    	  CheckNetState();
    	  
    	  
      }
      
	}

    public boolean hasImageCaptureBug() {

        // list of known devices that have the bug
        ArrayList<String> devices = new ArrayList<String>();
        devices.add("android-devphone1/dream_devphone/dream");
        devices.add("generic/sdk/generic");
        devices.add("vodafone/vfpioneer/sapphire");
        devices.add("tmobile/kila/dream");
        devices.add("verizon/voles/sholes");
        devices.add("google_ion/google_ion/sapphire");

        return devices.contains(android.os.Build.BRAND + "/" + android.os.Build.PRODUCT + "/"
                + android.os.Build.DEVICE);

    }
    private void MakeSelMenu(int MenuType)
    {
    	
    	
    }


	public void InitCommList(ListView ThisList)
	{
		if(ThisList!=null)
		{
			//mData=getData();
			try
			{
				mData=LoadHosData();
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			MyCommunityAdapter NewAdapt=null;
			NewAdapt=new MyCommunityAdapter(this);
			ThisList.setAdapter(NewAdapt);
			//添加点击   事件描述

		}

	}
	private List<Map<String, Object>> LoadHosData() throws UnsupportedEncodingException
	{

		int 		RecordNums=0;
		String      Hos_Caption="";
		String      Hos_Address="";
		String      Hos_Class="";
		String      Hos_RowPic="";

		Bitmap 		bmpout;

		int imgCount=0;
		int succ_count=0;
		long totalTime=0;




		List<Map<String, Object>> list=null;
		db=new dbHelper(this);
		if(db!=null)
		{
			try
			{
				myCursor=db.selectActives();
				RecordNums=myCursor.getCount();

				list = new ArrayList<Map<String, Object>>();

				myCursor.moveToFirst();

				imgCount=RecordNums;


				for(int i=0;i<RecordNums;i++)
				{
					int ID=myCursor.getInt(0);

					byte[] val = myCursor.getBlob(myCursor.getColumnIndex("pic_Name"));
					Hos_Caption=new String(val,"GBK");

					int succ = myCursor.getInt(myCursor.getColumnIndex("success"));
					Hos_Class=String.format("%d",succ);

					if(succ==1)
						succ_count++;

					int uTime= myCursor.getInt(myCursor.getColumnIndex("uTime"));

					totalTime+=uTime;

					Hos_RowPic=String.format("%d",uTime);


					//bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);

					//ByteArrayInputStream stream = new ByteArrayInputStream(myCursor.getBlob(myCursor.getColumnIndex("Hos_Pic")));


					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", Hos_Caption);
					map.put("info", Hos_Class);
					//map.put("img", Hos_RowPic);
					map.put("price",Hos_RowPic);
					list.add(map);
					myCursor.moveToNext();


				}
				myCursor.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(myCursor != null)
				{
					myCursor.close();
				}
			}
		}
		if(db!=null)
			db.close();



		//计算统计数据
		if(  imgCount>0){

			tcount.setText("图片数:"+String.format("%d",imgCount)+"张");




			succ_rate.setText("成功率:"+accuracy(succ_count,imgCount,2));

			double aveTime=totalTime/imgCount;


			time_rate.setText("平均用时:"+String.format("%d",Math.round(aveTime))+"ms");



		}


		return list;

	}

	public static String accuracy(double num, double total, int scale){
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		//可以设置精确几位小数
		df.setMaximumFractionDigits(scale);
		//模式 例如四舍五入
		df.setRoundingMode(RoundingMode.HALF_UP);
		double accuracy_num = num / total * 100;
		return df.format(accuracy_num)+"%";
	}



	public void onItemClick(AdapterView parent, View v, int position, long id)
	{
		Intent itemintent = new Intent(this, SaleDetailActivity.class);
		Bundle b = new Bundle();
		String RecordID="";
	
		if (mData != null) 
		{
			RecordID=(String)mData.get(position).get("price");			
			b.putString("ActiveID", RecordID);
			itemintent.putExtras(b);
			startActivity(itemintent);
		}
	}
    public class MyCommunityAdapter extends BaseAdapter
	{
		public final class ViewHolder
		{

			public TextView title;
			public TextView info;
			public TextView price;
		}
		
		
		
		private LayoutInflater mInflater;
		 
		public MyCommunityAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}
		public int getCount() 
		{
			// TODO Auto-generated method stub
			return mData.size();
		}

		public Object getItem(int arg0) 
		{
			// TODO Auto-generated method stub
			return null;
		}

		public long getItemId(int arg0) 
		{
			// TODO Auto-generated method stub
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			String PicName="";
			
			ViewHolder holder = null;
			if (convertView == null) 
			{
		        
				holder=new ViewHolder();  
				//convertView = mInflater.inflate(R.layout.vlist2, null);
				convertView = mInflater.inflate(R.layout.hislist, null);
				
				
				

				holder.title = (TextView)convertView.findViewById(R.id.title);
				holder.info = (TextView)convertView.findViewById(R.id.info);
				holder.price = (TextView)convertView.findViewById(R.id.price);
				
				
				convertView.setTag(holder);
			}
			else 
			{
				holder = (ViewHolder)convertView.getTag();
			}
			
			//NetFileAssistant.getHttpBitmap(TempCata.pic_url)
			Bitmap DestBmp;

			String tempstr=(String)mData.get(position).get("title");
//			
			holder.title.setText(tempstr);
			holder.title.setTextSize(15);
			
			//holder.title.getBackground().setAlpha(0);//setBackgroundColor(R.string.sbg);

			if( mData.get(position).get("info").equals("1"))
				holder.info.setText("成功");
			else
				holder.info.setText("失败");


			holder.info.setTextColor(Color.GRAY);
			holder.price.setText((String)mData.get(position).get("price")+"ms");
			return convertView;
		}
		
	}
    //send thread
    private class UploadImageTask extends AsyncTask<Integer,Integer,String> 
    {
    	@Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            Button button = progressDialog.getButton(ProgressDialog.BUTTON1);
            if(button!=null)
            	button.setVisibility(View.INVISIBLE);
            IsUploading=true;
            progressDialog.show();
        }
    	@Override
		protected void onPostExecute(String UpState)
        {
            boolean UpRight=false;
    		String ShowDialog="";
           
           
           if(UpState.equals("1"))
           {
        	   ShowDialog="上传完毕";
        	   UpRight=true;
           }

           ShowDialog=HintStr;
           

           
           progressDialog.setMessage(ShowDialog);
           if(!UpRight)
           {
	           Button button = progressDialog.getButton(ProgressDialog.BUTTON1);
	           if(button!=null)
	           	button.setVisibility(View.VISIBLE);
	           IsUploading=false;
	           
           }
           else
        	   progressDialog.dismiss();
        }
         
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
		 
		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			int UpState=uploadFile();
			return String.format("%d", UpState);
			 
		}
    }
}
