/**
 * SupportedSizesReflect.java
 * 鐗堟潈鎵�湁(C) 2013 
 * 鍒涘缓:cuiran 2013-7-22 涓嬪崍4:54:22
 */
package com.wl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.hardware.Camera;
import android.hardware.Camera.Size;

/**
 * TODO 绯荤粺鏀寔鐨勫垎杈ㄧ巼
 * @author cuiran
 * @version 1.0.0
 */
public class SupportedSizesReflect {

	private static Method Parameters_getSupportedPreviewSizes = null;
	private static Method Parameters_getSupportedPictureSizes = null;

	static {
		initCompatibility();
	};

	private static void initCompatibility() {
		try {
			Parameters_getSupportedPreviewSizes = Camera.Parameters.class.getMethod(
					"getSupportedPreviewSizes", new Class[] {});

			Parameters_getSupportedPictureSizes = Camera.Parameters.class.getMethod(
					"getSupportedPictureSizes", new Class[] {});

		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
			Parameters_getSupportedPreviewSizes = Parameters_getSupportedPictureSizes = null;
		}
	}

	/**
	 * Android 2.1涔嬪悗鏈夋晥
	 * @param p
	 * @return Android1.x杩斿洖null
	 */
	public static List<Size> getSupportedPreviewSizes(Camera.Parameters p) {
		return getSupportedSizes(p, Parameters_getSupportedPreviewSizes);
	}

	public static List<Size> getSupportedPictureSizes(Camera.Parameters p){
		return getSupportedSizes(p, Parameters_getSupportedPictureSizes);
	}	

	@SuppressWarnings("unchecked")
	private static List<Size> getSupportedSizes(Camera.Parameters p, Method method){
		try {
			if (method != null) {
				return (List<Size>) method.invoke(p);
			} else {
				return null;
			}
		} catch (InvocationTargetException ite) {
			Throwable cause = ite.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			} else if (cause instanceof Error) {
				throw (Error) cause;
			} else {
				throw new RuntimeException(ite);
			}
		} catch (IllegalAccessException ie) {
			return null;
		}
	}

}
