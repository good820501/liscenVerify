package com.homenet.basecompoment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class NetFileAssistant 
{

	/**  
     * ��ȡ����ͼƬ��Դ   
     * @param url  
   * @return  */ 

public static Bitmap getHttpBitmap(String url)
{  

  URL myFileURL;  

   Bitmap bitmap=null;  

   if(url.length()==0)
	   return null;
   
      try{  

          myFileURL = new URL(url);  

      //�������  
     HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  

       //���ó�ʱʱ��Ϊ6000���룬conn.setConnectionTiem(0);��ʾû��ʱ������  

      conn.setConnectTimeout(6000);  

      //�������û�������  

      conn.setDoInput(true);  

      //��ʹ�û���  

      conn.setUseCaches(false);  

      //�����п��ޣ�û��Ӱ��  

      //conn.connect();  

      //�õ������  

     InputStream is = conn.getInputStream();  

     //�����õ�ͼƬ  

      bitmap = BitmapFactory.decodeStream(is);  

      //�ر������  

     is.close();  

 }
 catch(Exception e)
 {  
      e.printStackTrace();  

 }  
      return bitmap;  

}  
public static long GetUserID(Activity ParentActivity)
{
		SharedPreferences shared = ParentActivity.getSharedPreferences("TopData", 0);  
		
        String id = shared.getString("id", "");//�����ǽ� main.xml �е���ݶ����� 
		if(id.length()>2)
			return Long.parseLong(id);
		else
			return 0;
}
}
