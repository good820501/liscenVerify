package com.chart.control;

import java.util.ArrayList;

import com.example.homenet.R;




import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  
 * @Description:  

 * @File: TopicNews.java

 * @Package com.image.indicator.activity

 *  

 * @Version V1.0
 */
public class TopicNews extends Activity{
	// ����ͼƬ�ļ���
	private ArrayList<View> imagePageViews = null;
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
	// ��ݽ�����
	//private NewsXmlParser parser = null; 
	private ArrayList<String>  PicList;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Translucent_NoTitleBar);
		
		PicList = (ArrayList<String>)getIntent().getStringArrayListExtra("PIC_LIST");
		
		
		
		// ��ʼ��
		initeViews();
	}
	
	/**
	 * ��ʼ��
	 */
	private void initeViews()
	{
		// ����ͼƬ����
		
		
		
		
		
		imagePageViews = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();  
		main = (ViewGroup)inflater.inflate(R.layout.page_topic_news, null);
		viewPager = (ViewPager) main.findViewById(R.id.image_slide_page);  
		
		// Բ��ͼƬ����
		
		

		
		int length = PicList.size();
		imageCircleViews = new ImageView[length];
		imageCircleView = (ViewGroup) main.findViewById(R.id.layout_circle_images);
		slideLayout = new SlideImageLayout(TopicNews.this);
		slideLayout.setCircleImageLayout(length);
		
		
		
		
		for(int i = 0;i < length;i++)
		{
			imagePageViews.add(slideLayout.getSlideImageLayout(PicList.get(i)));
			imageCircleViews[i] = slideLayout.getCircleImageLayout(i);
			imageCircleView.addView(slideLayout.getLinearLayout(imageCircleViews[i], 10, 10));
		}
		
		// ����Ĭ�ϵĻ�������
		//tvSlideTitle = (TextView) main.findViewById(R.id.tvSlideTitle);
		
		//tvSlideTitle.setText(parser.getSlideTitles()[0]);
		
		setContentView(main);
		
		// ����ViewPager
        viewPager.setAdapter(new SlideImageAdapter());  
        viewPager.setOnPageChangeListener(new ImagePageChangeListener());
	}
	
	// ����ͼƬ���������
    private class SlideImageAdapter extends PagerAdapter 
    {  
        @Override  
        public int getCount() { 
            return imagePageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(imagePageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
        	((ViewPager) arg0).addView(imagePageViews.get(arg1));
            
            return imagePageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    }
    
    // ����ҳ�����¼�������
    private class ImagePageChangeListener implements OnPageChangeListener 
    {
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int index) 
        {  
        	pageIndex = index;
        	slideLayout.setPageIndex(index);
        	//tvSlideTitle.setText(parser.getSlideTitles()[index]);
        	
            for (int i = 0; i < imageCircleViews.length; i++) 
            {  
            	imageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);
                
                if (index != i) {  
                	imageCircleViews[i].setBackgroundResource(R.drawable.dot_none);  
                }  
            }
        }  
    }
}
