package com.homenet.basecompoment;

import java.util.ArrayList;

import com.example.homenet.R;



import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

/**
 * ��ɻ���ͼƬ���򲼾�
 * @Description: ��ɻ���ͼƬ���򲼾�

 * @File: SlideImageLayout.java


 */
public class SlideImageLayout 
{
	// ��ͼƬ��ArrayList
	private ArrayList<ImageView> imageList = null;
	private Activity activity = null;
	// Բ��ͼƬ����
	private ImageView[] imageViews = null; 
	private ImageView imageView = null;
	//private NewsXmlParser parser = null;
	// ��ʾ��ǰ����ͼƬ������
	private int pageIndex = 0;
	
	public SlideImageLayout(Activity activity) 
	{
		// TODO Auto-generated constructor stub
		this.activity = activity;
		imageList = new ArrayList<ImageView>();
		//parser = new NewsXmlParser();
	}
	
	/**
	 * ��ɻ���ͼƬ���򲼾�
	 * @param index
	 * @return
	 */
	public View getSlideImageLayout(String ImgName)
	{
		// ��TextView��LinearLayout
		LinearLayout imageLinerLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams imageLinerLayoutParames = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, 
				LinearLayout.LayoutParams.WRAP_CONTENT,
				1);
		
		ImageView iv = new ImageView(activity);
		//iv.setBackgroundResource(index);
		iv.setImageBitmap(NetFileAssistant.getHttpBitmap(HServiceHelp.Picture_Home+ImgName));
		iv.setOnClickListener(new ImageOnClickListener());
		imageLinerLayout.addView(iv,imageLinerLayoutParames);
		imageList.add(iv);
		
		return imageLinerLayout;
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
	public ImageView getCircleImageLayout(int index)
	{
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
    private class ImageOnClickListener implements OnClickListener
    {
    	public void onClick(View v) 
    	{
    		// TODO Auto-generated method stub
    		//Toast.makeText(activity, parser.getSlideTitles()[pageIndex], Toast.LENGTH_SHORT).show();
    		//Toast.makeText(activity, parser.getSlideUrls()[pageIndex], Toast.LENGTH_SHORT).show();
    	}
    }
}
