package com.example.homenet;

import com.common.dbhelp.WndAssistant;
import com.common.dbhelp.dbHelper;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SaleDetailActivity extends Activity implements android.view.View.OnClickListener
{
	TextView DetailCaption;
	ImageButton Return;
	TextView    DetailContent;
	ImageView   HeadImg;
	ImageView AttendBtn;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        WndAssistant.SetWindowNoTitle(this);
        setContentView(R.layout.activity_sale_detail);
        InitControls();
        try 
        {
			InitData();
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			//�������
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_sale_detail, menu);
        return true;
    }*/
  //�������
    private void InitData() throws Exception
    {
    	String ActiveID = "";
    	String Act_Title="",Act_Detail="",Act_BigImg="",Hos_Descripe="",Hos_Class="";
    	
    	
		int    PageIndex=0;
		dbHelper db=null;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)

			ActiveID = bundle.getString("ActiveID");
		ActiveID = ActiveID.trim();
		Cursor myCursor=null;
		if(ActiveID!="")
		{
			try
			{
				db=new dbHelper(this);
				 myCursor=db.selectActive("ID="+ActiveID);
				
				int RecordNums=myCursor.getCount();
				myCursor.moveToFirst();
				if(RecordNums>0)
				{
					
					byte[] val = myCursor.getBlob(myCursor.getColumnIndex("Act_Title")); 
					Act_Title=new String(val,"GBK");
					Act_Title=Act_Title.trim();
					
					
					val = myCursor.getBlob(myCursor.getColumnIndex("Act_Detail"));
					Act_Detail=new String(val,"GBK");
					Act_Detail=Act_Detail.trim();
					
					
					val = myCursor.getBlob(myCursor.getColumnIndex("Act_BigImg"));
					Act_BigImg=new String(val,"GBK");
					Act_BigImg=Act_BigImg.trim();
					
					
					if(DetailContent!=null)
					{
						DetailContent.setText(Act_Detail);
						
					}
					if(Act_BigImg!="")
					{
						if(HeadImg!=null)
				        {
				        	Bitmap DestBmp=WndAssistant.GetBmpByName(Act_BigImg, SaleDetailActivity.this);
							if(DestBmp!=null)
							{
								BitmapDrawable BGPic = new BitmapDrawable(DestBmp);
								HeadImg.setBackgroundDrawable(BGPic);
							}
				        	
				        }
						
					}
				
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
			if(db!=null)
				db.close();
		}
		
    }
    private void InitControls()
    {
    	
    	 
    	
    	
    	 
    	AttendBtn=(ImageView)findViewById(R.id.imageView3);
    	if(AttendBtn!=null)
    	{   		
    		AttendBtn.setOnClickListener(this);
    	}
    	//HosDescripe=(TextView)findViewById(R.id.textView2);
    	
    	DetailCaption=(TextView)findViewById(R.id.textView1);
    	
    	if(DetailCaption!=null)
    	{
    		DetailCaption.setTextColor(WndAssistant.COLOR2);
    		DetailCaption.setTextSize(22); 
    		WndAssistant.LayoutCaptionText(DetailCaption,"�����", this, 100);
    	}
    	DetailContent=(TextView)findViewById(R.id.textView2);
    	 
        if(DetailContent!=null)
        {
        	
        	DetailContent.setText("");
        }
        HeadImg=(ImageView)findViewById(R.id.imageView2);
        
    }
    public void onClick(View v) 
	{
		int BtnID = v.getId();
		int BtnCt = 0;
		String StrTemp;
		
		switch(BtnID)
		{
			
			 
			case R.id.imageView3:
				BookActive();
				break;
		}
	}
    
    //�ԤԼ
    private void BookActive()
    {
    	String Hint="";
    	
    	if(WndAssistant.GetLoginState())
    	{
    		Hint="ԤԼ�ɹ�����ע����ն���֪ͨ!";
    		
    	}
    	else
    	{
    		Hint="��δ��¼�����¼�����";
    	}
    	WndAssistant.showHintDialog(this, "�����", Hint, false);
    	
    }
    
}
