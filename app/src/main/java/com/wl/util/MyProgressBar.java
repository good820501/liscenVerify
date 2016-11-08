package com.wl.util;

import com.example.homenet.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


public class MyProgressBar {
	Dialog dialog;
	Context context;
	//����ProgressBar����     
    private ProgressBar pro1; 
    int intCounter=0;
	/**
	 * ����
	 */
	 public MyProgressBar(Context context) 
	 {
				// TODO Auto-generated constructor stub
		 this.context=context;
		 dialog=new Dialog(context,R.style.dialog);
		 dialog.setOnCancelListener(onCancelListener);
	 }
	 /**
	  * ��ʼ����ȶԻ���
	  */
	 public void initDialog()
	 {
		 
		 LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 View view=inflater.inflate(R.layout.myprogressbar, null);
		 dialog.setContentView(view);
		 pro1=(ProgressBar)dialog.findViewById(R.id.progressBar1);
		//���ý�����Ƿ��Զ���ת,�������䲻ȷ��ģʽ,false��ʾ���Զ���ת 
		 pro1.setIndeterminate(false);
		//����ProgressBar�����ֵ  
	     pro1.setMax(100);  
	  
	    //����ProgressBar�ĵ�ǰֵ  
	    pro1.setProgress(0); 
	    
	    new Thread(new Runnable() 
	    {
			 
			public void run() 
			{
				// TODO Auto-generated method stub
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < 100; i++)     
                {
					handler.sendEmptyMessage(0);
					try 
					{
						Thread.sleep(30);
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}).start();
		 dialog.show();
	 }
	 
	 Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) 
			{				
				 // �ı�ProgressBar�ĵ�ǰֵ     
                pro1.setProgress(intCounter++);
                if (intCounter==100) {
                	intCounter=0;
                	colseDialog();
				}
                         
                
			}
			 
		 };
	public void colseDialog()
	{		
		dialog.dismiss();
	}
	public boolean isShowing()
	{
		if(dialog.isShowing())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	OnCancelListener onCancelListener=new OnCancelListener()
	{		
		 
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub			
			dialog.dismiss();
			System.out.println("cancel!!!!");
		}
	};
}
