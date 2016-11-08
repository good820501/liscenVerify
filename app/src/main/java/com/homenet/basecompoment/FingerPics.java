package com.homenet.basecompoment;

import java.util.ArrayList;

import com.example.homenet.R;


import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;



public class FingerPics extends ScrollView 
{
	
	View convertView ;
	Activity ParentActivity;
	
	private ArrayList<View> imagePageViews = null;
	private ArrayList<String> ImageList;
	
	private ViewGroup main = null;
	private ViewPager viewPager = null;
	// ��ǰViewPager����
	private int pageIndex = 0; 
	
	// ��Բ��ͼƬ��View
	private ViewGroup imageCircleView = null;
	private ImageView[] imageCircleViews = null; 
	
	// ��������
	private TextView tvSlideTitle = null;
	
	// ����������
	private SlideImageLayout slideLayout = null;
	
	
	public FingerPics(Context context)	
	{
		
		this(context, null);
		ParentActivity=(Activity)context;
		
         InitControl();
	}
	public void SetParentActivity(Activity Parent)
	{
		
		ParentActivity=Parent;
		InitControl();
	}
	public void InitControl()
	{
		
		ImageList=new ArrayList<String>();
		
		
	
		
	}
	public FingerPics(Context context, AttributeSet attrs) 
	{
        this(context, attrs, 0);
    }
	public FingerPics(Context context, AttributeSet attrs, int defStyle) 
	{
        super(context, attrs, defStyle);	        
	}
	
	//����ͼƬ���
	public void AddImageName(String ImageName)
	{
		if(ImageName!="")
		{
			if(ImageList!=null)
			{
				ImageList.add(ImageName);
			}
		}
	}
	
	//����б�
	public void ClearAllImages()
	{
		if(ImageList!=null)
		{
			//ImageList.removeAll(collection)
			
		}
		
		
	}
	//��ʼ����
	public void StartPlay()
	{
		initeViews(ParentActivity);
		
	}
	
	/**
	 * ��ʼ��
	 */
	private void initeViews(Context context)
	{
		// ����ͼƬ����
		imagePageViews = new ArrayList<View>();
		
		LayoutInflater inflater = LayoutInflater.from(context);  
		main = (ViewGroup)inflater.inflate(R.layout.top_pictures, null);
		viewPager = (ViewPager) main.findViewById(R.id.image_slide_page);  
		
		
		//Բ��ͼƬ����
		
		int length = ImageList.size();
		imageCircleViews = new ImageView[length];
		imageCircleView = (ViewGroup) main.findViewById(R.id.layout_circle_images);
		slideLayout = new SlideImageLayout((Activity)context);
		slideLayout.setCircleImageLayout(length);
		
		
		
		
		for(int i = 0;i < length;i++)
		{
			imagePageViews.add(slideLayout.getSlideImageLayout((String)ImageList.get(i)));
			imageCircleViews[i] = slideLayout.getCircleImageLayout(i);
			imageCircleView.addView(slideLayout.getLinearLayout(imageCircleViews[i], 10, 10));
		}
		
		this.addView(main);
		
		//setContentView(main);
		
		// ����ViewPager
        viewPager.setAdapter(new SlideImageAdapter());  
        viewPager.setOnPageChangeListener(new ImagePageChangeListener());
	}
	
	// ����ͼƬ���������
    private class SlideImageAdapter extends PagerAdapter 
    {  
        @Override  
        public int getCount() 
        { 
            return imagePageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) 
        {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) 
        {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) 
        {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(imagePageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) 
        {  
            // TODO Auto-generated method stub  
        	((ViewPager) arg0).addView(imagePageViews.get(arg1));
            
            return imagePageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1)
        {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() 
        {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) 
        {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) 
        {  
            // TODO Auto-generated method stub  
  
        }  
    }
    
    // ����ҳ�����¼�������
    private class ImagePageChangeListener implements OnPageChangeListener 
    {

 
        public void onPageScrollStateChanged(int arg0) 
        {  
            // TODO Auto-generated method stub  
  
        }  
  
          
        public void onPageScrolled(int arg0, float arg1, int arg2) 
        {  
            // TODO Auto-generated method stub  
  
        }  
  
         
        public void onPageSelected(int index) 
        {  
        	pageIndex = index;
        	slideLayout.setPageIndex(index);
        	//tvSlideTitle.setText(parser.getSlideTitles()[index]);
        	
            for (int i = 0; i < imageCircleViews.length; i++) 
            {  
            	imageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);
                
                if (index != i)
                {  
                	imageCircleViews[i].setBackgroundResource(R.drawable.dot_none);  
                }  
            }
        }  
    }
}
