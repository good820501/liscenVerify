package com.common.dbhelp;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Context;  
import android.util.DisplayMetrics;  


public class DensityUtil 
{
	private static final String TAG = DensityUtil.class.getSimpleName();  
    
    // ��ǰ��Ļ��densityDpi  
    private static float dmDensityDpi = 0.0f;  
    private static DisplayMetrics dm;  
    private static float scale = 0.0f;  
  
    public int ScreenWidth=0,ScreenHeight=0;
    
    
    
    /** 
     *  
     * ��ݹ��캯���õ�ǰ�ֻ����Ļϵ�� 
     *  
     * */  
    public DensityUtil(Context context) 
    {  
        // ��ȡ��ǰ��Ļ  
        dm = new DisplayMetrics();  
        dm = context.getApplicationContext().getResources().getDisplayMetrics();  
        // ����DensityDpi  
        setDmDensityDpi(dm.densityDpi);  
        // �ܶ�����  
        scale = getDmDensityDpi() / 160; 
        
        Activity Main=(Activity)context;
        InitScreenWH(Main);
        //Logger.i(TAG, toString());  
    }  
    private void InitScreenWH(Activity context)
    {
    	DisplayMetrics dm = new DisplayMetrics();
    	context.getWindowManager().getDefaultDisplay().getMetrics(dm);
    	int widthPixels= dm.widthPixels;
    	int heightPixels= dm.heightPixels;
    	float density = dm.density;
    	//ScreenWidth = (int) (widthPixels * density) ;
    	//ScreenHeight = (int) (heightPixels * density) ;   	
    	
    	ScreenWidth = (int) (widthPixels ) ;
    	ScreenHeight = (int) (heightPixels) ;  
    }
  
    /** 
     * ��ǰ��Ļ��density���� 
     *  
     * @param DmDensity 
     * @retrun DmDensity Getter 
     * */  
    public static float getDmDensityDpi() 
    {  
        return dmDensityDpi;  
    }  
  
    /** 
     * ��ǰ��Ļ��density���� 
     *  
     * @param DmDensity 
     * @retrun DmDensity Setter 
     * */  
    public static void setDmDensityDpi(float dmDensityDpi) 
    {  
        DensityUtil.dmDensityDpi = dmDensityDpi;  
    }  
  
    /** 
     * �ܶ�ת������ 
     * */  
    public static int dip2px(float dipValue) 
    {  
  
        return (int) (dipValue * scale + 0.5f);  
  
    }  
    
    
  
    /** 
     * ����ת���ܶ� 
     * */  
    public int px2dip(float pxValue) 
    {  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    @Override  
    public String toString() 
    {  
        return " dmDensityDpi:" + dmDensityDpi;  
    }  
}
