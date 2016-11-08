package com.album.util;// com.album.util.MyApplication

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
 

import com.calendar.control.LogWriter;
import com.example.homenet.MainActivity;


public class MyApplication extends Application implements Thread.UncaughtExceptionHandler{
	
	private HttpClient httpClient;
	public boolean isLogin = false;
	public LogWriter mLogWriter;
	@Override
	public void onCreate() 
	{
		super.onCreate();
		String LogFileName=Environment.getExternalStorageDirectory()+ File.separator + "PowerRep.txt";
		File logf = new File(LogFileName);
        try
        {
			mLogWriter = LogWriter.open(logf.getAbsolutePath());
		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			Log.d("---test---", e.getMessage());
		}
        if(mLogWriter!=null)
        {
        	
        	
        	LocalLog("����Ӧ������ϵͳ�����ɹ���",null);
        	 
        	
        }
        
	}
	//д��ϵͳ��־
	public void LocalLog(String msg,Class cls)
	{
    	Log.d("---test---", msg);
    	
    	try 
    	{
    		if(mLogWriter!=null)
    		{
    			if(cls!=null)
    				mLogWriter.print(cls, msg);
    			else
    				mLogWriter.print(msg);
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("---test---", e.getMessage());
		}
    }
	private void CloseLog()
	{
		 if(mLogWriter!=null)
			try {
				mLogWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	@Override 
    public void uncaughtException(Thread thread, Throwable ex) 
	{ 
        System.out.println("uncaughtException"); 
        LocalLog("ϵͳ�쳣�˳������ڽ�������",null);
        CloseLog();
        System.exit(0); 
        Intent intent = new Intent(this, MainActivity.class); 
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_NEW_TASK); 
        startActivity(intent); 
    } 
	@Override
	public void onLowMemory()
	{
		super.onLowMemory();
		this.shutdownHttpClient();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		CloseLog();
		 
	}

	//  
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));

		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(
				params, schReg);

		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	// 对外提供HttpClient实例
	public HttpClient getHttpClient() {
		return httpClient;
	}
}
