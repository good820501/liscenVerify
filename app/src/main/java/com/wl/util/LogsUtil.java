/**
 * LogsUtil.java
 * 鐗堟潈鎵�湁(C) 2013 
 * 鍒涘缓:cuiran 2013-4-17 涓婂崍11:42:15
 */
package  com.wl.util;


import android.util.Log;

/**
 * TODO 鏃ュ織浣跨敤绫� * @author cuiran
 * @version 1.0
 */
public class LogsUtil {
	private static boolean flag=true;
	public static void d(String tag,String msg){
		if(flag){
			Log.d(tag, msg);
		}
		
	}

	public static void d(String tag,String msg,Throwable tr){
		if(flag){
			Log.d(tag, msg,tr);
		}
		
	}
	
	public static void i(String tag,String msg){
		if(flag){
			Log.i(tag, msg);
		}
		
	}

	public static void i(String tag,String msg,Throwable tr){
		if(flag){
			Log.i(tag, msg,tr);
		}
		
	}
	
	public static void e(String tag,String msg){
		if(flag){
			Log.e(tag, msg);
		}
		
	}
	public static void e(String tag,Exception e){
		if(flag){
			if(null!=e){
				Log.e(tag, e.getMessage());
			}
		
		}
		
	}

	public static void e(String tag,String msg,Throwable tr){
		if(flag){
			Log.e(tag, msg,tr);
		}
		
	}
}
