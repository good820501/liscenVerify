package com.example.homenet;


 
import java.io.File;
import java.io.FileOutputStream;


import com.common.dbhelp.DensityUtil;
import com.common.dbhelp.WebAssistant;
import com.common.dbhelp.WndAssistant;
import com.common.dbhelp.dbHelper;
import com.hosinfo.update.UpdateManager;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
 
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@TargetApi(9)
public class MainPage extends Activity implements OnClickListener
{
	private UpdateManager 	mUpdateManager;
	private ImageButton 	BookOnLine;
	private ImageButton 	BookActivity;
	private ImageButton 	MyDoctor;
	private ImageButton 	HealthEvaluate;
	private ImageButton 	SystemSet;
	
	private ImageButton 	SaleOnLine;
	
	ImageView 				LeafFirst;
	ImageView 				LeafSecond;
	ImageView 				LeafThird;
	
	public static String TEST_IMAGE;
    private static final String FILE_NAME = "/pic.jpg";
	
	private int LeafOne_Width=0,LeafTwoWidth=0;
	
	private int TopHeight;//��������ĸ߶�
	private int ButtonListOneHeight;
	
    
	 
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
       
        WndAssistant.SetWindowNoTitle(this); 
        //����״̬��   
        setContentView(R.layout.activity_main_page);    
        
        if(Build.VERSION.SDK_INT >= 11)
        {
        	
        	CallHighApi();
        }
        initImagePath();
        
        WebAssistant.CommonNetTest=this;
        
       
        
        	
        	
        	OpenBookList();
        
        
      //���������汾�Ƿ���Ҫ����
        //mUpdateManager = new UpdateManager(this);
        //mUpdateManager.checkUpdateInfo();
        /*InitButtons();
        InitWindow();
        AutoLogin();
        */
        
        
    }
	protected void onDestroy() 
	{
		
		super.onDestroy();
	}
	@TargetApi(11)
    private void CallHighApi()
    {
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()  
	            .detectDiskWrites().detectNetwork().penaltyLog().build());  
	    StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()  
	            .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        
    	
    }
    
    //ϵͳ�Զ���¼-����Ƿ��ڵ�¼״̬�����ѳ�ʱ�����Զ����µ�¼
    private void AutoLogin()
    {
    	dbHelper  ThisDB=new dbHelper(this);
    	String[] ReturnParams=new String[3];
    	String UserName="",UserPass="";
    	
		if(ThisDB.GetLoginState(ReturnParams))
		{
			UserName=ReturnParams[0];
			UserPass=ReturnParams[1];
			
			if(!WebAssistant.GetTimeOutState())
			{
			
				if(UserName.equals("")||UserPass.equals(""))
				{
					
					WndAssistant.LoginState=false;
					ThisDB.SetLoginState(false, "", "");
					return;
				}
				
				if(WebAssistant.UserLogin(UserName, UserPass, ReturnParams))
				{
					WndAssistant.LoginState=true;
					WndAssistant.LoginUserName=UserName;
					
				}
				else
				{
					WndAssistant.LoginState=false;
				}
			}
			else
			{
				WndAssistant.LoginState=true;
				WndAssistant.LoginUserName=UserName;
			}
		}
    	
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
  //��̬�ı�ؼ��Ĵ�С��λ��
    private void MakeControls(int ScreenWidth,int ScreenHeight)
    {    	
    	MakeTopPic(ScreenWidth,ScreenHeight); 	
    	MakeLeafButtons(ScreenWidth,ScreenHeight);
    	MakeRoundButtons(ScreenWidth,ScreenHeight);
    }
    
    //����Բ�ΰ�ť����
    private void MakeRoundButtons(int ScreenWidth,int ScreenHeight)
    {
    	MakeButtonListOne(ScreenWidth,ScreenHeight);    	
    	MakeButtonListTwo(ScreenWidth,ScreenHeight);
    }
    //�����һ��Բ�ΰ�ť
    private void MakeButtonListOne(int ScreenWidth,int ScreenHeight)
    {
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	ButtonListOne;  	
    	
    	
    	ButtonListOne=(RelativeLayout)this.findViewById(R.id.ButtonList);
    	
    	if(ButtonListOne!=null)
    	{
    		RelativeLayout.LayoutParams ListParam;
    		int NewWidth=(int) (ScreenWidth*1);
    		int NewHeight=(int)(NewWidth/3);
    		
    		
    		ListParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=0;
    		MarginTop=(int)(ScreenHeight*0.618*0.8);
    		ButtonListOneHeight=MarginTop+NewHeight;
    		if(ListParam!=null)
    		{
    			ListParam.leftMargin=MarginLeft;
    			ListParam.topMargin=MarginTop;
    			
    			ButtonListOne.setLayoutParams(ListParam);	    
    			LayoutFirRowButtons(NewWidth);
    		}
    	}
    	
    }
    //����ڶ���Բ�ΰ�ť
    private void MakeButtonListTwo(int ScreenWidth,int ScreenHeight)
    {
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	ButtonListTwo;  	
    	
    	
    	ButtonListTwo=(RelativeLayout)this.findViewById(R.id.ButtonListSec);
    	
    	if(ButtonListTwo!=null)
    	{
    		RelativeLayout.LayoutParams ListParam;
    		int NewWidth=(int) (ScreenWidth*1);
    		int NewHeight=(int)(NewWidth/3);
    		
    		
    		ListParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=0;
    		MarginTop=ButtonListOneHeight;
    		
    		if(ListParam!=null)
    		{
    			ListParam.leftMargin=MarginLeft;
    			ListParam.topMargin=MarginTop;
    			ButtonListTwo.setLayoutParams(ListParam);	 
    			LayoutSecRowButtons(NewWidth);
    		}
    	}
    	
    }
    
    //���е�һ�鰴ť
    private void LayoutFirRowButtons(int RowLength)
    {
    	int		ColumnWidth=RowLength/3;		//�п�
    	int 	ColumnHeight=0;
    	
    	RelativeLayout.LayoutParams 	ButtonParam;
    	
    	RelativeLayout ButtonFirst,ButtonSecond,ButtonThird;
    	
    	
    	ButtonFirst=(RelativeLayout)this.findViewById(R.id.ButtonFirst);
    	if(ButtonFirst!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=0;
    			ButtonParam.topMargin=0;
    			ButtonFirst.setLayoutParams(ButtonParam);	    
    			ButtonFirst.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    	
    	ButtonSecond=(RelativeLayout)this.findViewById(R.id.ButtonSecond);
    	if(ButtonSecond!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=ColumnWidth;
    			ButtonParam.topMargin=0;
    			ButtonSecond.setLayoutParams(ButtonParam);	
    			ButtonSecond.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    	
    	ButtonThird=(RelativeLayout)this.findViewById(R.id.ButtonThird);
    	if(ButtonThird!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=ColumnWidth*2;
    			ButtonParam.topMargin=0;
    			ButtonThird.setLayoutParams(ButtonParam);	  
    			ButtonThird.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    }
  //���е�һ�鰴ť
    private void LayoutSecRowButtons(int RowLength)
    {
    	int		ColumnWidth=RowLength/3;		//�п�
    	int 	ColumnHeight=0;
    	
    	RelativeLayout.LayoutParams 	ButtonParam;
    	
    	RelativeLayout ButtonFirstSec,ButtonSecondSec,ButtonThirdSec;
    	
    	
    	ButtonFirstSec=(RelativeLayout)this.findViewById(R.id.ButtonFirstSec);
    	if(ButtonFirstSec!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=0;
    			ButtonParam.topMargin=0;
    			ButtonFirstSec.setLayoutParams(ButtonParam);	    
    			ButtonFirstSec.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    	
    	ButtonSecondSec=(RelativeLayout)this.findViewById(R.id.ButtonSecondSec);
    	if(ButtonSecondSec!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=ColumnWidth;
    			ButtonParam.topMargin=0;
    			ButtonSecondSec.setLayoutParams(ButtonParam);	
    			ButtonSecondSec.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    	
    	ButtonThirdSec=(RelativeLayout)this.findViewById(R.id.ButtonThirdSec);
    	if(ButtonThirdSec!=null)
    	{
    		ColumnHeight=ColumnWidth;
    		
    		ButtonParam=new RelativeLayout.LayoutParams(ColumnWidth,ColumnHeight);
    		if(ButtonParam!=null)
    		{
    			ButtonParam.leftMargin=ColumnWidth*2;
    			ButtonParam.topMargin=0;
    			ButtonThirdSec.setLayoutParams(ButtonParam);	  
    			ButtonThirdSec.setPadding(ColumnWidth/5, 0, 0, 0);
    		}    		
    	}
    }
    //����Ҷ�Ӱ�ť
    private void MakeLeafButtons(int ScreenWidth,int ScreenHeight)
    {
    	    	
    	MakeLeafOne(ScreenWidth,ScreenHeight);
    	MakeLeafTwo(ScreenWidth,ScreenHeight);
    	MakeLeafThree(ScreenWidth,ScreenHeight);
    	MakeLeafLine(ScreenWidth,ScreenHeight);
    }
    
    private void MakeLeafOne(int ScreenWidth,int ScreenHeight)
    {
    	
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	LeafOne;  	
    	
    	
    	WidthDHeight=242/197;
    	
    	LeafOne=(RelativeLayout)this.findViewById(R.id.leafone);
    	if(LeafOne!=null)
    	{
    		RelativeLayout.LayoutParams LeafParam;
    		int NewWidth=(int) (ScreenWidth*0.35);
    		int NewHeight=0;
    		
    		if(WidthDHeight>0.0)
    		{
    			NewHeight=(int)(NewWidth/WidthDHeight);	    			
    		}
    		
    		LeafOne_Width=NewWidth;
    		
    		LeafParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=(int)(0.0618*ScreenWidth/2);
    		MarginTop=(int)(ScreenHeight*0.0618)+TopHeight;
    		
    		if(LeafParam!=null)
    		{
    			LeafParam.leftMargin=MarginLeft;
    			LeafParam.topMargin=MarginTop;
    			LeafOne.setLayoutParams(LeafParam);	    			
    		}    		
    	} 	
    }
    private void MakeLeafTwo(int ScreenWidth,int ScreenHeight)
    {
    	
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	LeafTwo;  	
    	
    	
    	WidthDHeight=171/143;
    	
    	LeafTwo=(RelativeLayout)this.findViewById(R.id.leaftwo);
    	if(LeafTwo!=null)
    	{
    		RelativeLayout.LayoutParams LeafParam;
    		int NewWidth=(int) (ScreenWidth*0.3);
    		int NewHeight=0;
    		
    		if(WidthDHeight>0.0)
    		{
    			NewHeight=(int)(NewWidth*WidthDHeight);	    			
    		}
    		
    		//LeafOne_Width=NewWidth;
    		
    		LeafParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=LeafOne_Width+15;
    		MarginTop=(int)(ScreenHeight*0.0618)+TopHeight+10;
    		
    		LeafTwoWidth=MarginLeft+NewWidth;
    		
    		
    		if(LeafParam!=null)
    		{
    			LeafParam.leftMargin=MarginLeft;
    			LeafParam.topMargin=MarginTop;
    			LeafTwo.setLayoutParams(LeafParam);	    			
    		}    		
    	} 	
    }
    private void MakeLeafThree(int ScreenWidth,int ScreenHeight)
    {
    	
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	LeafThree;  	
    	
    	
    	WidthDHeight=149/120;
    	
    	LeafThree=(RelativeLayout)this.findViewById(R.id.leafthree);
    	if(LeafThree!=null)
    	{
    		RelativeLayout.LayoutParams LeafParam;
    		int NewWidth=(int) (ScreenWidth*0.25);
    		int NewHeight=0;
    		
    		if(WidthDHeight>0.0)
    		{
    			NewHeight=(int)(NewWidth*WidthDHeight);	    			
    		}
    		
    		//LeafOne_Width=NewWidth;
    		
    		LeafParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=LeafTwoWidth;
    		MarginTop=(int)(ScreenHeight*0.0618)+TopHeight;
    		
    		if(LeafParam!=null)
    		{
    			LeafParam.leftMargin=MarginLeft;
    			LeafParam.topMargin=MarginTop;
    			LeafThree.setLayoutParams(LeafParam);	    			
    		}    		
    	} 	
    }
    private void MakeLeafLine(int ScreenWidth,int ScreenHeight)
    {
    	int       		MarginLeft=0,MarginTop=0;
    	double    		WidthDHeight=0;
    	RelativeLayout 	LeafLine;  	
    	
    	
    	WidthDHeight=720/140;
    	
    	LeafLine=(RelativeLayout)this.findViewById(R.id.leafline);
    	if(LeafLine!=null)
    	{
    		RelativeLayout.LayoutParams LeafParam;
    		int NewWidth=(int) (ScreenWidth*1);
    		int NewHeight=0;
    		
    		if(WidthDHeight>0.0)
    		{
    			NewHeight=(int)(NewWidth/WidthDHeight);	    			
    		}
    		
    		//LeafOne_Width=NewWidth;
    		
    		LeafParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
    		
    		MarginLeft=0;
    		MarginTop=(int)(ScreenHeight*0.0618/3)+TopHeight;
    		
    		if(LeafParam!=null)
    		{
    			LeafParam.leftMargin=MarginLeft;
    			LeafParam.topMargin=MarginTop;
    			LeafLine.setLayoutParams(LeafParam);	    			
    		}    		
    	} 
    	
    	
    }
    //����ͼƬ����
    private void MakeTopPic(int ScreenWidth,int ScreenHeight)
    {
    	RelativeLayout TopPic;
   	 
    	int		  TopPic_Width=0,TopPic_Height=0,TopPicLeft=0;
    	int       MarginLeft=0,MarginTop=0;
    	double    WidthDHeight=0;
    	
    	WidthDHeight=358/86;
    	
    	TopPic=(RelativeLayout)this.findViewById(R.id.TopContainer);
    	if(TopPic!=null)
    	{
    		RelativeLayout.LayoutParams TopParam;
    		try
    		{
	    		int NewWidth=(int) (ScreenWidth*0.618*1.2);
	    		int NewHeight=(int)(ScreenHeight*0.0618);
	    		
	    		if(WidthDHeight>0.0)
	    		{
	    			NewHeight=(int)(NewWidth/WidthDHeight);	    			
	    		}
	    		    		 
	    		TopParam=new RelativeLayout.LayoutParams(NewWidth,NewHeight);
	    		
	    		MarginLeft=(ScreenWidth-NewWidth)/2;
	    		MarginTop=(int)(ScreenHeight*0.0618);
	    		
	    		TopHeight=MarginTop+NewHeight;	//���涥������ĸ߶�
	    		if(TopParam!=null)
	    		{
	    			TopParam.leftMargin=MarginLeft;
	    			TopParam.topMargin=MarginTop;
	    			TopPic.setLayoutParams(TopParam);	    			
	    		}
    		}
    		catch(Exception exp)
    		{
    			String Error=exp.getMessage();
    		}    		
    	}   	
    }
    
    private void OpenMorePage()
    {
    	/*
    	Intent ShowWorkZone=new Intent(this,ShareActivity.class);
    	 
    	this.startActivity(ShowWorkZone);
    	*/
    	
    }

    public void onClick(View v) 
	{
		int BtnID = v.getId();
		int BtnCt = 0;
		String StrTemp;
		 
		
		switch(BtnID)
		{
			 
			 
			case R.id.imgBtnBA:
				OpenBookList();
				break;
			case R.id.imageView4:		//��������
				OpenTabPage(3);
				break;	
			
			case R.id.imageButton3Sec:		//����ײ�
				ShowShare();
				break;	
			default:
				ShowCommonHint();
				break;
		}
		
	}
    private void initImagePath() {
		try {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
					&& Environment.getExternalStorageDirectory().exists()) {
				TEST_IMAGE = Environment.getExternalStorageDirectory().getAbsolutePath() + FILE_NAME;
			}
			else {
				TEST_IMAGE = getApplication().getFilesDir().getAbsolutePath() + FILE_NAME;
			}
			File file = new File(TEST_IMAGE);
			if (!file.exists()) 
			{
				file.createNewFile();
				Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
				FileOutputStream fos = new FileOutputStream(file);
				pic.compress(CompressFormat.JPEG, 100, fos);
				fos.flush();
				fos.close();
			}
		} catch(Throwable t) {
			t.printStackTrace();
			TEST_IMAGE = null;
		}
	}
    private void showShare(boolean silent, String platform) 
	{
		
	}
    private void ShowShare()
	{
		showShare(false, null);
	}
    //��ʾͨ����ʾ��Ϣ
    private void ShowCommonHint()
    {
    	WndAssistant.showHintDialog(this, "�����ٱ�", "���ܼ�������", false);
    }
    private boolean TimeVerify()
    {
    	
    	
    	 
    		return true;
    	 
    }
    
    //�򿪻�Ƽ�����
    private void OpenBookList()
    {
    	if(TimeVerify())
    	{
    	
	    	Intent OpenBook=new Intent(this,SalesActivity.class);
	    	this.startActivity(OpenBook);
    	}
    	else
    	{
    		ShowCommonHint();
    		System.exit(0);
    	}
    		
    }
    private void OpenTabPage(int PageIndex)
    {
    	/*Intent ShowWorkZone=new Intent(this,WorkZoneActivity.class);
    	Bundle bundle = new Bundle();
		bundle.putString("PageIndex", Integer.toString(PageIndex));
    	ShowWorkZone.putExtras(bundle);
    	this.startActivity(ShowWorkZone);*/
    }
    
    //��ʼ����ť
    private void InitButtons()
    {
    	
    	/*BookOnLine=(ImageButton)this.findViewById(R.id.imageButton1);
    	
    	if(BookOnLine!=null)
    	{    		
    		BookOnLine.setOnClickListener(this);   		
    	}*/
    	
    	MyDoctor=(ImageButton)this.findViewById(R.id.imageButton2);
    	
    	if(MyDoctor!=null)
    	{    		
    		MyDoctor.setOnClickListener(this);   		
    	}
    	
    	SaleOnLine=(ImageButton)this.findViewById(R.id.imageButton1Sec);
    	
    	if(SaleOnLine!=null)
    	{    		
    		SaleOnLine.setOnClickListener(this);   		
    	}
    	HealthEvaluate=(ImageButton)this.findViewById(R.id.imageButton2Sec);
    	
    	if(HealthEvaluate!=null)
    	{    		
    		HealthEvaluate.setOnClickListener(this);   		
    	}
    	
    	SystemSet=(ImageButton)this.findViewById(R.id.imageButton3Sec);
    	
    	if(SystemSet!=null)
    	{    		
    		SystemSet.setOnClickListener(this);   		
    	}
    	
    	/*
    	LeafFirst=(ImageView)this.findViewById(R.id.imageView24);
    	if(LeafFirst!=null)
    	{
    		LeafFirst.setOnClickListener(this);
    		
    	}
    	
    	LeafSecond=(ImageView)this.findViewById(R.id.imageView4);
    	if(LeafSecond!=null)
    	{
    		LeafSecond.setOnClickListener(this);
    		
    	}
    	
    	LeafThird=(ImageView)this.findViewById(R.id.imageView14);
    	if(LeafThird!=null)
    	{
    		LeafThird.setOnClickListener(this);
    		
    	}*/
    	
    	
    	BookActivity=(ImageButton)this.findViewById(R.id.imgBtnBA);
    	if(BookActivity!=null)
    	{    		
    		BookActivity.setOnClickListener(this);   		
    	}
    	
    }
    
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main_page, menu);
        return true;
    }*/
}
