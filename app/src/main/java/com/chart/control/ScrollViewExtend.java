package com.chart.control;
//com.chart.control.ScrollViewExtend
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * �ܹ�����ViewPager��ScrollView
 * @Description: �����ViewPager��ScrollView�еĻ�����������

 * @File: ViewPagerCompatScrollView.java

 * @Package com.image.indicator.control

 * @Author  

 * @Date 2012-6-18 ����01:34:50

 * @Version V1.0
 */
public class ScrollViewExtend extends ScrollView {
	// �������뼰���
    private float xDistance, yDistance, xLast, yLast;
    private GestureDetector mGestureDetector; 
    private boolean canScroll;  
    View.OnTouchListener mGestureListener;  
    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
         
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) 
    {
    	
        switch (ev.getAction()) 
        {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                
                if(xDistance > yDistance){
                	return false;
                }  
        }

        return super.onInterceptTouchEvent(ev);
    	
    	 
    }
    
    
}