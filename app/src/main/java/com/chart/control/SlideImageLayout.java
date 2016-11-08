package com.chart.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.example.homenet.R;

 

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * ��ɻ���ͼƬ���򲼾�
 * @Description: ��ɻ���ͼƬ���򲼾�

 * @File: SlideImageLayout.java

 * @Package com.image.indicator.layout

 * @Author  

 * 

 * @Version V1.0
 */
public class SlideImageLayout 
{
	// ��ͼƬ��ArrayList
	private ArrayList<ImageView> imageList = null;
	private Activity activity = null;
	// Բ��ͼƬ����
	private ImageView[] imageViews = null; 
	private ImageView imageView = null;

	// ��ʾ��ǰ����ͼƬ������
	private int pageIndex = 0;
	
	public SlideImageLayout(Activity activity) 
	{
		// TODO Auto-generated constructor stub
		this.activity = activity;
		imageList = new ArrayList<ImageView>();

	}
	
	/**
	 * ��ɻ���ͼƬ���򲼾�
	 * @param index
	 * @return
	 */
	public View getSlideImageLayout(int index)
	{
		// ��TextView��LinearLayout
		LinearLayout imageLinerLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT,
				1);
		
		ImageView iv = new ImageView(activity);
		iv.setBackgroundResource(index);
		iv.setOnClickListener(new ImageOnClickListener());
		
		
		iv.setOnLongClickListener(new OnLongClickListener() 
		{
            
            @Override
            public boolean onLongClick(View v) 
            {
                    // TODO Auto-generated method stub
            	    
                    return false;
            }
    });
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		imageList.add(iv);
		
		return imageLinerLayout;
	}
	public static Bitmap getLoacalBitmap(String url) 
    {
    	Bitmap ReturnImg=null;
         try 
         {
        	 BitmapFactory.Options opt = new BitmapFactory.Options();  
        	 opt.inPreferredConfig = Bitmap.Config.RGB_565;   
        	 opt.inPurgeable = true;  
        	 opt.inInputShareable = true;  
        	          //��ȡ��ԴͼƬ  
        	  
        	 
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
	public View getSlideImageLayout(String FileName)
	{
		// ��TextView��LinearLayout
		LinearLayout imageLinerLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 
				LinearLayout.LayoutParams.MATCH_PARENT,
				1);
		
		ImageView iv = new ImageView(activity);
		try
	  	{
	  
			 Bitmap bitmap = getLoacalBitmap(FileName);
					 //������ȡͼƬ
			
			 if(bitmap!=null)
			 {
				 
				 bitmap=createRoundConerImage(bitmap,bitmap.getWidth(),bitmap.getHeight(),30);
				 if(bitmap!=null)
				 iv.setImageBitmap(bitmap);	//����Bitmap
			 }
			 else
			 {
				  
				 
				 
			 }
	  	}
	  	catch(Exception Exp)
	  	{
	  		
	  		//this.showDialog("����ͼƬ����"+Exp.getMessage());
	  		
	  	}
		
		
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		imageList.add(iv);
		
		return imageLinerLayout;
	}
	//����Բ��ͼƬ
	private Bitmap createRoundConerImage(Bitmap source,int mWidth,int mHeight,int mRadius)
	{
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		int Rwidth=1024;
		int RHeight=768;
		
		Bitmap target=null ;
		
		if(mWidth>Rwidth)
			target=Bitmap.createBitmap(Rwidth, RHeight, Config.ARGB_8888);
		else
			target=Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, target.getWidth(), target.getHeight());
		
		 
			
		canvas.drawRoundRect(rect, mRadius, mRadius, paint);	
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		Bitmap bitmap = ThumbnailUtils.extractThumbnail(source, Rwidth, RHeight);  
		
		if(mWidth>Rwidth)
		
			canvas.drawBitmap(bitmap, 0, 0, paint);
		else
			canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}
	/**
	 * ��ȡLinearLayout
	 * @param view
	 * @param width
	 * @param height
	 * @return
	 */
	public View getLinearLayout(View view,int width,int height)
	{
		LinearLayout linerLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams linerLayoutParames = new LinearLayout.LayoutParams(
				width, 
				height,
				1);
		// �������Ҳ�Զ������ã�����Ȥ���Լ����á�
		linerLayout.setPadding(10, 0, 10, 0);
		linerLayout.addView(view, linerLayoutParames);
		
		return linerLayout;
	}
	
	/**
	 * ����Բ�����
	 * @param size
	 */
	public void setCircleImageLayout(int size){
		imageViews = new ImageView[size];
	}
	
	/**
	 * ���Բ��ͼƬ���򲼾ֶ���
	 * @param index
	 * @return
	 */
	public ImageView getCircleImageLayout(int index){
		imageView = new ImageView(activity);  
		imageView.setLayoutParams(new LayoutParams(10,10));
        imageView.setScaleType(ScaleType.FIT_XY);
        
        imageViews[index] = imageView;
         
        if (index == 0) 
        {  
            //Ĭ��ѡ�е�һ��ͼƬ
            imageViews[index].setBackgroundResource(R.drawable.dot_selected);  
        }
        else 
        {  
            imageViews[index].setBackgroundResource(R.drawable.dot_none);  
        }  
         
        return imageViews[index];
	}
	
	/**
	 * ���õ�ǰ����ͼƬ������
	 * @param index
	 */
	public void setPageIndex(int index)
	{
		pageIndex = index;
	}
	
	// ����ҳ�����¼�������
    private class ImageOnClickListener implements OnClickListener{
    	@Override
    	public void onClick(View v) 
    	{
    		// TODO Auto-generated method stub
    		//Toast.makeText(activity, parser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();
    		//Toast.makeText(activity, parser.getSlideUrls()[pageIndex], Toast.LENGTH_SHORT).show();
    	}
    }
    
    
}
